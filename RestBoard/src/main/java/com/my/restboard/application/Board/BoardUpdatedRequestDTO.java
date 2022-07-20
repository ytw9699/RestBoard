package com.my.restboard.application.Board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BoardUpdatedRequestDTO {

	@Schema(description = "제목", nullable = false, example = "수정된 제목")
	private String title;

	@Schema(description = "아이디", nullable = false, example = "admin")
	private String userId;

	@Schema(description = "내용", nullable = false, example = "수정된 내용")
	private String content;

	@Schema(description = "잠금여부( 1 = true, 0 = false)", nullable = false, example = "0")
	private int boardLocked;

	@Builder
	public BoardUpdatedRequestDTO(String title, String content, String userId, int boardLocked) {
		this.title = title;
		this.content = content;
		this.userId = userId;
		this.boardLocked = boardLocked;
	}

	public BoardEntity toEntity() {
		return BoardEntity.builder()
				.title(title)
				.content(content)
				.userId(userId)
				.boardLocked(boardLocked)
				.build();
	}
}

