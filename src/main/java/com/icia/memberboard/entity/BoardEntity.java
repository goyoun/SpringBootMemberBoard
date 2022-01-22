package com.icia.memberboard.entity;

import com.icia.memberboard.dto.BoardSaveDTO;
import com.icia.memberboard.dto.BoardUpdateDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_id")
     private Long id;
    @Column
     private String boardTitle;
    @Column
     private String boardWriter;
    @Column
     private String boardContents;
    @Column
     private int boardHits;
    @Column
     private String bFilename;

    // 원글의 게시글 번호를 참조하기 위한 설정 댓글:게시글 N:1
    // 회원엔티티와 연관관계 N:1
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id") // 부모테이블(참조하고자하는 테이블)의 pk 컬럼이름
    private MemberEntity memberEntity; // 참조하고자 하는 테이블을 관리하는 엔티티

    // 댓글과의 연관관계 Entity를 기준으로 누가 N이고 누가 1인지
    // mappedBy = 연관관계를 맺었을때 누가 주최인지 (자식)
    // fetch = FetchType.LAZY = BackEnd > DB 로 JPA를 계속 호출하는데 DB가 부담이생기는데 중간에서 캐싱역할을 해줌
    // LAZY는 필요데이터를 알아서 가져온다. EAGER는 한번에 다가져와서 부담스러움
    // cascade = CascadeType.All, orphanRemoval = true 자식이있어도 삭제함
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) //commentEntity에서 참조하고자하는 테이블을 관리하는 엔티티를 가져옴
    // 댓글을 가져올때 리스트를 선언해서 자식테이블 Entity를 가져옴
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    // BoardSaveDTO > BoardEntity 객체로 변환하기 위한 메서드
    public static BoardEntity toSaveEntity(BoardSaveDTO boardSaveDTO, MemberEntity memberEntity) {
        // 선언
        BoardEntity boardEntity = new BoardEntity();
        // boardWriter을 memberEmail로 변경
        boardEntity.setBoardWriter(memberEntity.getMemberEmail());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
        boardEntity.setBFilename(boardSaveDTO.getBFilename());
        boardEntity.setBoardHits(0);
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }

    public static BoardEntity updateBoard(BoardUpdateDTO boardUpdateDTO, MemberEntity memberEntity) {
        // 선언
        BoardEntity boardEntity = new BoardEntity();
        // 옮기기
        boardEntity.setId(boardUpdateDTO.getBoardId());
        boardEntity.setBoardWriter(boardUpdateDTO.getBoardWriter());
        boardEntity.setBoardTitle(boardUpdateDTO.getBoardTitle());
        boardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
        boardEntity.setBFilename(boardUpdateDTO.getBoardFilename());
        boardEntity.setMemberEntity(memberEntity);
        return boardEntity;
    }
}
