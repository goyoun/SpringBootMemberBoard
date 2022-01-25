package com.icia.memberboard.service;

import com.icia.memberboard.common.PagingConst;
import com.icia.memberboard.dto.*;
import com.icia.memberboard.entity.BoardEntity;
import com.icia.memberboard.entity.MemberEntity;
import com.icia.memberboard.repository.BoardRepository;
import com.icia.memberboard.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository br;
    private final MemberRepository mr;

    // 글쓰기
    @Override
    public Long save(BoardSaveDTO boardSaveDTO) throws IOException {
        MultipartFile bFile = boardSaveDTO.getBFile();
        System.out.println(bFile);
        String bFilename = bFile.getOriginalFilename();
        bFilename = System.currentTimeMillis() + "-" + bFilename;
        String savePath = "C:\\Development\\source\\springboot\\MemberBoard\\src\\main\\resources\\static\\image\\" + bFilename;
        if (!bFile.isEmpty()) {
            bFile.transferTo(new File(savePath));
        }
        boardSaveDTO.setBFilename(bFilename);
        System.out.println("boardSaveDTO.getBFilename() = " + boardSaveDTO.getBFilename());

        MemberEntity memberEntity = mr.findByMemberEmail(boardSaveDTO.getMemberEmail());
        // JpaRepository는 무조건 Entity만 받음.
        // BoardSaveDTO -> BoardEntity 로 변환
        // boardSaveDTO를 전달하고 받는다
        // 가져온 boardSaveDTO 매개변수를 boardEntity 클래스의 toSaveEntity매서드의 매개변수로 사용
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardSaveDTO,memberEntity); //memberEntity 괄호안에
        Long result = br.save(boardEntity).getId();
//        두줄을 밑에 리턴한줄로 가능
//        Long boardId = mr.save(boardEntity).getId();
//        return boardId;
        System.out.println("BoardServiceImpl.save");
        // jpa에 save메서드에 boardEntity 매개변수를 넣어줌 그리고 Id를 가져와서 리턴시킴
        return result;
    }

    // 글목록
    @Override
    public List<BoardDetailDTO> findAll() {
// jpa에서 findAll 결과값을 가져온다.
        List<BoardEntity> boardEntityList = br.findAll();
// boardList 선언하기
        List<BoardDetailDTO> boardList = new ArrayList<>();
        // 포이프문에서 boardEntity 변수를 boardList 변수로 변경
        for(BoardEntity b: boardEntityList) {
            boardList.add(BoardDetailDTO.toBoardDetailDTO(b));
        }
        return boardList;
    }

    //페이징
    @Override
    public Page<BoardPagingDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber();
        // 요청한 페이지가 1이면 페이지값을 0으로 하고 1이 아니면 요청 페이지에서 1을 뺀다.
//        page = page - 1;
        page = (page == 1)? 0: (page - 1);
        //                        몇페이지? / 몇개씩 볼껀지       / 무슨 기준으로 정렬할지 (내림차순)/ 기준 컬럼 (Entity 필드이름) /
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Entity는 서비스 밖으로 나가면 안됨
        // Page<BoardEntity> => Page(BoardPagingDTO 로 변환시켜야하지만 페이징은 안된다.
        Page<BoardPagingDTO> boardList = boardEntities.map(
                // 엔티티 객체를 담기위한 반복용 변수 board
                board -> new BoardPagingDTO(board.getId(),
                        board.getBoardWriter(),
                        board.getBoardTitle(),
                        board.getBoardHits())
        );
        return boardList;
    }

    @Override
    @Transactional // jpa 가갖고있는 캐시를 DB에 반영함 (커밋하는느낌) 조회수처리를할때도
    public BoardDetailDTO findById(Long boardId) {
        int boardHits = br.boardHits(boardId);
        /*
            1. MemberRepository로 부터 해당 회원의 정보를 MemberEntity로 가져옴.
            2. MemberEntity를 MemberDetailDTO로 바꿔서 컨트롤러로 리턴.
         */
        // 1.
        // Optional = null 방지
        Optional<BoardEntity> optionalBoardEntity = br.findById(boardId);
        /*
            Optional 객체 메서드
            - isPresnet(): 데이터가 있으면 true, 없으면 false 반환
            - isEmpty(); 데이터가 없으면 true, 있으면 false 반환
            - get(): Optional 객체에 들어있는 실제 데이터를 가져올때
         */
        BoardDetailDTO boardDetailDTO=null;
        if(optionalBoardEntity.isPresent()) {
            BoardEntity boardEntity = optionalBoardEntity.get();
            boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        }
//        // .get() 은 옵셔널안에 Entity를 꺼냄
//        BoardEntity boardEntity = optionalBoardEntity.get();
//        // 2.
//        BoardDetailDTO boardDetailDTO = BoardDetailDTO.toBoardDetailDTO(boardEntity);
        return boardDetailDTO;
        // 위의 4줄을 밑에 한줄로도 가능하다.
        //return MemberDetailDTO.toMemberDetailDTO(mr.findById(memberId).get());
    }

    // 삭제
    @Override
    public void deleteById(Long boardId) {
        br.deleteById(boardId);
    }

    //업데이트
    @Override
    public void update(BoardUpdateDTO boardUpdateDTO) {
        MemberEntity memberEntity = mr.findById(boardUpdateDTO.getMemberId()).get();
        BoardEntity boardEntity = BoardEntity.updateBoard(boardUpdateDTO, memberEntity);
        br.save(boardEntity);

    }

    // 서칭기능
    @Override
    public List<BoardDetailDTO> search(BoardSearchDTO boardSearchDTO) {
        // 검색타입이 2가지니까 분리한다
        if (boardSearchDTO.getSelect().equals("boardWriter")){
            // Containing 은 %검색어% 앞뒤로 알맞는 단어가 있으면 검색이 된다.
            List<BoardEntity> boardEntityList = br.findByBoardWriterContaining(boardSearchDTO.getSearch());
            // DTO 타입으로 변환
            List<BoardDetailDTO> boardDetailDTOList = BoardDetailDTO.toChangeDTOList(boardEntityList);
            System.out.println("boardDetailDTOList = " + boardDetailDTOList);
            return boardDetailDTOList;
        } else {
            List<BoardEntity> boardEntityList = br.findByBoardTitleContaining(boardSearchDTO.getSearch());
            // DTO 타입으로 변환
            List<BoardDetailDTO> boardDetailDTOList = BoardDetailDTO.toChangeDTOList(boardEntityList);
            return boardDetailDTOList;
        }

    }


}
