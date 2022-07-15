package com.my.restboard.application.Board;

import lombok.*;
import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "Board")
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long board_num;

	@Column(length = 200, nullable = false)
	private String userId;

	@Column(length = 500, nullable = false)
	private String title;

	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Builder
	public BoardEntity(String title, String content, String userId) {
		this.title = title;
		this.content = content;
		this.userId = userId;
	}
}

