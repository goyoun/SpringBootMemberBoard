package com.icia.memberboard.entity;

import com.icia.memberboard.dto.MemberDetailDTO;
import com.icia.memberboard.dto.MemberSaveDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;
    @Column
    private String memberEmail;
    @Column
    private String memberPassword;
    @Column
    private String memberName;
    @Column
    private String memberPhone;
    @Column
    private String mFilename;


  //   부모를 지우면 참조하는 모든게 지워진다.
    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardEntity> boardEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList = new ArrayList<>();

    public static MemberEntity toSaveEntity(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberSaveDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberSaveDTO.getMemberPassword());
        memberEntity.setMemberName(memberSaveDTO.getMemberName());
        memberEntity.setMemberPhone(memberSaveDTO.getMemberPhone());
        memberEntity.setMFilename(memberSaveDTO.getMFilename());
        return memberEntity;
    }

    public static MemberEntity toUpdateMember(MemberDetailDTO memberDetailDTO) {
        //객체 생성
        MemberEntity memberEntity = new MemberEntity();
        // memberEntity 객체에 memberDetailDTO 객체안에 담긴 값을 담아줌
        memberEntity.setId(memberDetailDTO.getMemberId());
        memberEntity.setMemberEmail(memberDetailDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDetailDTO.getMemberPassword());
        memberEntity.setMemberName(memberDetailDTO.getMemberName());
        memberEntity.setMemberPhone(memberDetailDTO.getMemberPhone());
        memberEntity.setMFilename(memberDetailDTO.getMemberFilename());
        // 변환이 완료된 memberEntity를 넘겨줌
        return memberEntity;
    }
}
