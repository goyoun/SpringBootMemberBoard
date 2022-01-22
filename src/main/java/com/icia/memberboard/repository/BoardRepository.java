package com.icia.memberboard.repository;

import com.icia.memberboard.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    List<BoardEntity> findByBoardWriterContaining(String search);

    List<BoardEntity> findByBoardTitleContaining(String search);

    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits+1 where b.id = :boardId")
    int boardHits( @Param ("boardId") Long boardId);

}
