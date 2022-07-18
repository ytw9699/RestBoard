package com.my.restboard.application.Board;

import lombok.Getter;

@Getter
public class BoardResponseDTO {

    private Long boardNum;
    private String title;
    private String content;
    private String userId;

    public BoardResponseDTO(BoardEntity entity) {
        this.boardNum = entity.getBoardNum();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.userId = entity.getUserId();
    }
}
