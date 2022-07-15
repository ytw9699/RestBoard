package com.my.restboard.application.Board;

import lombok.Getter;

@Getter
public class BoardResponseDTO {

    private Long board_num;
    private String title;
    private String content;
    private String userId;

    public BoardResponseDTO(BoardEntity entity) {
        this.board_num = entity.getBoard_num();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userId = entity.getUserId();
    }
}
