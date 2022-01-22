package com.icia.memberboard.dto;

import com.icia.memberboard.entity.BoardEntity;
import com.icia.memberboard.entity.CommentEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDetailDTO {
    private Long boardId;
    private Long memberId;
    private String boardWriter;
    private String boardTitle;
    private String boardContents;
    private String bFilename;
    private LocalDateTime boardDate;
    private List<CommentDetailDTO> commentList;
    private int boardHits;

    public static BoardDetailDTO toBoardDetailDTO(BoardEntity boardEntity) {
        //객체 생성
        BoardDetailDTO boardDetailDTO = new BoardDetailDTO();
        //옮겨담기
        boardDetailDTO.setBoardId(boardEntity.getId());
        boardDetailDTO.setMemberId(boardEntity.getMemberEntity().getId());
        boardDetailDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDetailDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDetailDTO.setBoardContents(boardEntity.getBoardContents());
        boardDetailDTO.setBFilename(boardEntity.getBFilename());
        boardDetailDTO.setBoardDate(boardEntity.getCreateTime());
        boardDetailDTO.setBoardHits(boardEntity.getBoardHits());
        boardDetailDTO.setCommentList(CommentDetailDTO.toCommentDetailDTOList(boardEntity.getCommentEntityList()));
        // 리턴
        return boardDetailDTO;
    }

    public static List<BoardDetailDTO> toChangeDTOList(List<BoardEntity> boardEntityList) {
        List<BoardDetailDTO> boardDetailDTOList = new ArrayList<>();
        for (BoardEntity m: boardEntityList) {
            boardDetailDTOList.add(toBoardDetailDTO(m));
        }
        return boardDetailDTOList;
    }

}
