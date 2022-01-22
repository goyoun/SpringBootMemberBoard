package com.icia.memberboard.dto;

import com.icia.memberboard.entity.CommentEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CommentDetailDTO {
    private Long commentId;
    private Long boardId;
    private String commentWriter;
    private String commentContents;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public static CommentDetailDTO toCommentDetailDTO(CommentEntity commentEntity) {
        // 객체생성
        CommentDetailDTO commentDetailDTO = new CommentDetailDTO();
        // 옮겨담기
        commentDetailDTO.setCommentId(commentEntity.getId());
        commentDetailDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDetailDTO.setCommentContents(commentEntity.getCommentWriter());
        commentDetailDTO.setCreateTime(commentEntity.getCreateTime());
        commentDetailDTO.setUpdateTime(commentEntity.getUpdateTime());
        commentDetailDTO.setBoardId(commentEntity.getBoardEntity().getId());
        return commentDetailDTO;
    }

    public static List<CommentDetailDTO> toCommentDetailDTOList(List<CommentEntity> commentEntityList) {
        List<CommentDetailDTO> commentDetailDTOList = new ArrayList<>();
        for (CommentEntity c: commentEntityList) {
            commentDetailDTOList.add(toCommentDetailDTO(c));
        }
        return commentDetailDTOList;
    }
}
