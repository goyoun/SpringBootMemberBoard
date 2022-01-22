package com.icia.memberboard.entity;

import com.icia.memberboard.dto.CommentDetailDTO;
import com.icia.memberboard.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="comment_table")
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;
    @Column
    private String commentWriter;
    @Column
    private String commentContents;


    // 원글의 게시글 번호를 참조하기 위한 설정 댓글:게시글 N:1
    // 보드 엔티티 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_id") // 부모테이블(참조하고자하는 테이블)의 pk 컬럼이름
    private BoardEntity boardEntity; // 참조하고자 하는 테이블을 관리하는 엔티티

    // 회원 엔티티 연관관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private MemberEntity memberEntity;

    public static CommentEntity toSaveEntity(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity, MemberEntity memberEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(memberEntity.getMemberEmail());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());

        commentEntity.setBoardEntity(boardEntity);
        commentEntity.setMemberEntity(memberEntity);
        return commentEntity;
    }

}
