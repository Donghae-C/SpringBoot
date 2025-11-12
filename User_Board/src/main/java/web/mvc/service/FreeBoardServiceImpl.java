package web.mvc.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.description.method.MethodDescription;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.mvc.domain.FreeBoard;
import web.mvc.dto.FreeBoardDTO;
import web.mvc.exception.BasicException;
import web.mvc.exception.ErrorCode;
import web.mvc.repository.FreeBoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FreeBoardServiceImpl implements FreeBoardService {
    private final FreeBoardRepository freeBoardRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<FreeBoardDTO> selectAll() {
        //List<FreeBoard> list = freeBoardRepository.findAll();
        List<FreeBoard> entityList = freeBoardRepository.join02();
        List<FreeBoardDTO> list = entityList.stream().map(this::entitytoDTO).collect(Collectors.toList());

        return list;
    }

    @Override
    public Page<FreeBoardDTO> selectAll(Pageable pageable) {
        Page<FreeBoard> entityPageList = freeBoardRepository.join03(pageable);
        Page<FreeBoardDTO> pageList = entityPageList.map(entity -> modelMapper.map(entity, FreeBoardDTO.class));
        return pageList;
    }

    @Override
    public void insert(FreeBoard board) {
        freeBoardRepository.save(board);
    }

    @Override
    public FreeBoard selectBy(Long bno, boolean state) {
        FreeBoard freeBoard = freeBoardRepository.join04(bno).orElseThrow(()->new BasicException(ErrorCode.FAILED_DETAIL));
        if(state){
            freeBoard.setReadnum(freeBoard.getReadnum()+1);
        }
        return freeBoard;
    }

    @Override
    public FreeBoard update(FreeBoard board) {
        FreeBoard freeBoard = freeBoardRepository.findById(board.getBno()).orElseThrow(() -> new BasicException(ErrorCode.FAILED_UPDATE));
        if(!freeBoard.getPassword().equals(board.getPassword())) {
            throw new BasicException(ErrorCode.WRONG_PASS);
        }
        freeBoard.setSubject(board.getSubject());
        freeBoard.setContent(board.getContent());
        return freeBoardRepository.save(freeBoard);
    }

    @Override
    public void delete(Long bno, String password) {
        FreeBoard freeBoard = freeBoardRepository.findById(bno).orElseThrow(() -> new BasicException(ErrorCode.FAILED_DELETE));
        if(!freeBoard.getPassword().equals(password)){
            throw new BasicException(ErrorCode.FAILED_DELETE);
        }
        freeBoardRepository.delete(freeBoard);
    }

    private FreeBoard DTOtoEntity(FreeBoardDTO freeBoardDTO) {
        return modelMapper.map(freeBoardDTO, FreeBoard.class);
    }

    private FreeBoardDTO entitytoDTO(FreeBoard freeBoard) {
        return modelMapper.map(freeBoard, FreeBoardDTO.class);
    }
}
