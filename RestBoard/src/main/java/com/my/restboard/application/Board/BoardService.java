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
	public Long create(BoardRequestDTO request) {

		BoardEntity entity = request.toEntity();

		validate(entity);

		return repository.save(entity).getBoard_num();
	}

	@Transactional(readOnly = true)
	public BoardResponseDTO read(Long board_num) {

		Optional<BoardEntity> result = repository.findById(board_num);

		if (!result.isPresent()) {
			new IllegalArgumentException("해당 하는 글이 없습니다");
		}

		BoardEntity entity = result.get();

		return new BoardResponseDTO(entity);
	}

	@Transactional
	public Long update(Long board_num, BoardRequestDTO requestDto) {

		Optional<BoardEntity> result = repository.findById(board_num);

		if (!result.isPresent()) {
			new IllegalArgumentException("해당 하는 글이 없습니다");
		}

		BoardEntity entity = result.get();

		entity.update(requestDto.getTitle(), requestDto.getContent());

		return board_num;
	}

	@Transactional
	public void delete (Long board_num) {

		Optional<BoardEntity> result = repository.findById(board_num);

		if (!result.isPresent()) {
			new IllegalArgumentException("해당 하는 글이 없습니다");
		}

		BoardEntity entity = result.get();

		repository.delete(entity);
	}

	private void validate(final BoardEntity entity){

		if(entity == null) {

			throw new RuntimeException("Entity cannot be null.");
		}

		if(entity.getUserId() == null) {

			throw new RuntimeException("아이디가 없습니다.");
		}

		if(entity.getTitle() == null) {

			throw new RuntimeException("제목이 없습니다.");
		}

		if(entity.getContent() == null) {

			throw new RuntimeException("글 내용이 없습니다.");
		}

	}

}
