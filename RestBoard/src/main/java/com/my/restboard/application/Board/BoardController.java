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
                        @AuthenticationPrincipal String userId, @RequestBody BoardRequestDTO request){

        try {

            if(!userId.equals(request.getUserId())){
                throw new RuntimeException("작성 할 수 없습니다.");
            }

            Long boardnum = service.create(request);

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

    @GetMapping("board")
    public CommonResponse<?> readBoard(@RequestParam("board_num") Long board_num) {

        try {

            BoardResponseDTO dto = service.read(board_num);

            CommonResponse response = CommonResponse.builder()
                    .success(true)
                    .data(dto)
                    .build();

            return response;

        }catch (Exception e) {

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

    @PutMapping("board/{board_num}")
    public CommonResponse updateBoard( @AuthenticationPrincipal String userId,
                                       @PathVariable Long board_num,
                                       @RequestBody BoardRequestDTO request) {

        try {

            if(!userId.equals(request.getUserId())){
                throw new RuntimeException("본인만 수정 할 수 있습니다.");
            }

            Long return_num = service.update(board_num, request);

            CommonResponse response = CommonResponse.builder()
                    .success(true)
                    .data(return_num)
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
<<<<<<< HEAD
        
    }
=======
    }

    @DeleteMapping("board/{board_num}")
    public CommonResponse deleteBoard(@AuthenticationPrincipal String userId, @PathVariable Long board_num,
                                                @RequestBody BoardRequestDTO request) {

        try {

            if(!userId.equals(request.getUserId())){
                throw new RuntimeException("본인만 삭제 할 수 있습니다.");
            }
            
            service.delete(board_num);

            CommonResponse response = CommonResponse.builder()
                    .success(true)
                    .build();

            return response;
            
        }  catch (Exception e) {

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

>>>>>>> feature/14
}
