package com.my.restboard.application.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository repository;

	@Transactional
	public Long save(BoardSaveRequestDTO request) {

		BoardEntity entity = request.toEntity();

		validate(entity);

		return repository.save(entity).getBoard_num();
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
