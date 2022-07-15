package com.my.restboard.application.Board;

import lombok.*;

@Getter
@NoArgsConstructor
public class BoardSaveRequestDTO {

	private String title;
	private String userId;
	private String content;

	@Builder
	public BoardSaveRequestDTO(String title, String content, String userId) {
		this.title = title;
		this.content = content;
		this.userId = userId;
	}

	public BoardEntity toEntity() {
		return BoardEntity.builder()
				.title(title)
				.content(content)
				.userId(userId)
				.build();
	}
}

