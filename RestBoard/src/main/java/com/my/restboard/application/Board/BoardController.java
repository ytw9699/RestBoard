package com.my.restboard.application.Board;

import com.my.restboard.common.CommonResponse;
import com.my.restboard.common.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/board/test")
    public String test(){

        return "test";
    }

    @PostMapping("board")
    public CommonResponse<?> createBoard(
                        @AuthenticationPrincipal String userId, @RequestBody BoardSaveRequestDTO request){

        try {

            if(!userId.equals(request.getUserId())){
                throw new RuntimeException("작성 할 수 없습니다.");
            }

            Long boardnum = service.save(request);

            CommonResponse response = CommonResponse.builder()
                    .success(true)
                    .data(boardnum)
                    .build();

            return response;

        } catch (Exception e) {

            Error error = Error.builder()
                    .message(e.getMessage())
                    .status(500)
                    .build();

            CommonResponse response = CommonResponse.builder()
                    .success(false)
                    .error(error)
                    .build();

            return response;
        }
    }
}
