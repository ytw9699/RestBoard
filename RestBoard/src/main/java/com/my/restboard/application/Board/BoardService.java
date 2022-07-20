package com.my.restboard.application.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository repository;

	@Transactional
	public Long create(BoardSavedRequestDTO request) {

		BoardEntity entity = request.toEntity();

		validate(entity);

		return repository.save(entity).getBoardNum();
	}

	@Transactional(readOnly = true)
	public BoardResponseDTO read(Long boardNum, String userId) {

		Optional<BoardEntity> result = repository.findById(boardNum);

		if (!result.isPresent()) {
			throw new IllegalArgumentException("해당 하는 글이 없습니다");
		}

		BoardEntity entity = result.get();

		if(entity.getBoardLocked() == 1){
			if(!userId.equals(entity.getUserId())){
				throw new RuntimeException("글이 비공개 되어있습니다.");
			}
		}

		return new BoardResponseDTO(entity);
	}

	@Transactional
	public Long update(Long boardNum, String userId, BoardUpdatedRequestDTO requestDto) {

		validate(requestDto.toEntity());

		Optional<BoardEntity> result = repository.findByBoardNumAndUserId(boardNum, userId);

		if (!result.isPresent()) {
			throw new IllegalArgumentException("해당 하는 글이 없습니다");
		}

		BoardEntity entity = result.get();

		entity.update(requestDto.getTitle(), requestDto.getContent(), requestDto.getBoardLocked());

		return boardNum;
	}

	@Transactional
	public void delete (Long boardNum, String userId) {

		Optional<BoardEntity> result = repository.findByBoardNumAndUserId(boardNum, userId);

		if (!result.isPresent()) {
			throw new IllegalArgumentException("해당 하는 글이 없습니다");
		}

		BoardEntity entity = result.get();

		repository.delete(entity);
	}

	private void validate(final BoardEntity entity){

		if(entity == null) {

			throw new RuntimeException("Entity cannot be null.");
		}

		if(entity.getUserId() == null) {

			throw new RuntimeException("요청 된 아이디값이 없습니다.");
		}

		if(entity.getTitle() == null) {

			throw new RuntimeException("요청 된 제목값이 없습니다.");
		}

		if(entity.getContent() == null) {

			throw new RuntimeException("요청 된 글 내용값이 없습니다.");
		}
	}
}
