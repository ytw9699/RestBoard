package com.my.restboard.application.Board;

import com.my.restboard.common.CommonResponse;
import com.my.restboard.common.Error;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @Operation(summary = "게시글 등록", description = "제목, 내용, 아이디를 이용하여 글을 등록합니다.")
    @PostMapping("board")
    public CommonResponse<?> createBoard(
                        @AuthenticationPrincipal String userId, @RequestBody BoardRequestDTO request){

        try{

            if(!userId.equals(request.getUserId())){
                throw new RuntimeException("작성자 아이디를 확인해주세요.");
            }

            Long boardnum = service.create(request);

            CommonResponse response = CommonResponse.builder()
                    .success(true)
                    .data(boardnum)
                    .build();

            return response;

        }catch (Exception e) {

            return exceptionHandle(e, e.getMessage(), 500);
        }
    }

    @Operation(summary = "게시글 조회", description = "글번호를 이용하여 조회합니다.")
    @GetMapping("board")
    public CommonResponse<?> readBoard(@RequestParam("board_num") Long board_num){

        try{

            BoardResponseDTO dto = service.read(board_num);

            CommonResponse response = CommonResponse.builder()
                    .success(true)
                    .data(dto)
                    .build();

            return response;

        }catch (Exception e) {

            return exceptionHandle(e, e.getMessage(), 500);
        }
    }

    @Operation(summary = "게시글 수정", description = "글번호, 제목, 내용, 글의 아이디를 이용하여 수정합니다.")
    @PutMapping("board/{board_num}")
    public CommonResponse updateBoard( @AuthenticationPrincipal String userId,
                                       @PathVariable Long board_num,
                                       @RequestBody BoardRequestDTO request) {

        try{

            if(!userId.equals(request.getUserId())){
                throw new RuntimeException("본인만 수정 할 수 있습니다.");
            }

            Long return_num = service.update(board_num, request);

            CommonResponse response = CommonResponse.builder()
                    .success(true)
                    .data(return_num)
                    .build();

            return response;
            
        }catch (Exception e) {

            return exceptionHandle(e, e.getMessage(), 500);
        }
    }

    @Operation(summary = "게시글 삭제", description = "글번호, 글의 아이디를 이용하여 삭제합니다.")
    @DeleteMapping("board/{board_num}")
    public CommonResponse deleteBoard(@AuthenticationPrincipal String userId, @PathVariable Long board_num,
                                      @RequestBody String requestedId) {

        try{

            if(!userId.equals(requestedId)){
                throw new RuntimeException("본인만 삭제 할 수 있습니다.");
            }

            service.delete(board_num);

            CommonResponse response = CommonResponse.builder()
                    .success(true)
                    .build();

            return response;
            
        }catch (Exception e) {

            return exceptionHandle(e, e.getMessage(), 500);

        }
    }

    private CommonResponse exceptionHandle(Exception e, String message, int status){

        e.printStackTrace();

        Error error = Error.builder()
                .message(message)
                .status(status)
                .build();

        CommonResponse response = CommonResponse.builder()
                .success(false)
                .error(error)
                .build();

        return response;
    }
}
