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

    // native query
    // JPQL  ( Java persistence query language )
    // 주의해야 할 점은 반드시 테이블에 대한 약칭을 써야함. BoardEntity b 처럼 엔티티를 b로 사용함
    // Entity 기준으로 사용하여야 한다.
    // query dsl
    @Modifying
    //                      엔티티이름       엔티티테이블에 컬럼                 엔티티 PK값 : 변수이름
    @Query(value = "update BoardEntity b set b.boardHits = b.boardHits+1 where b.id = :boardId")
    // 위의 변수이름은 여기서 준다.             변수이름
    int boardHits( @Param ("boardId") Long boardId);

}
