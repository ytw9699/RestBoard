package com.my.restboard.application.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.restboard.application.Board.BoardEntity;
import com.my.restboard.application.Board.BoardRepository;
import com.my.restboard.application.Board.BoardRequestDTO;
import com.my.restboard.common.CommonResponse;
import com.my.restboard.security.TokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BoardControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private BoardRepository repository;

    private MockMvc mvc;
    private String token;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        UserEntity userEntity =  UserEntity.builder().
                                            userId("admin").//관리자
                                            build();

        token = tokenProvider.createToken(userEntity);//토큰 생성
    }

    @DisplayName("글 작성 후 조회 검증")
    @Test
    public void createBoard() throws Exception {

        String title = "글제목";
        String content = "내용";
        String userId = "admin";

        BoardRequestDTO requestDto = BoardRequestDTO.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();

        String url = "http://localhost:" + port + "/board";

        String response = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization","Bearer "+token)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        CommonResponse commonResponse = objectMapper.readValue(response, CommonResponse.class);

        Long board_num = Long.valueOf((int)commonResponse.getData());

        BoardEntity entity  = repository.findById(board_num).get();

        assertThat(entity.getTitle()).isEqualTo(title);
        assertThat(entity.getContent()).isEqualTo(content);
        assertThat(entity.getUserId()).isEqualTo(userId);

    }

    @DisplayName("글 작성 후, 재차 수정 후 조회 검증")
    @Test
    public void updateBoard() throws Exception {

        BoardEntity createdEntity = repository.save(BoardEntity.builder()
                .title("title")
                .content("content")
                .userId("admin")
                .build());

        Long board_num = createdEntity.getBoard_num();

        String title = "수정제목";
        String content = "수정내용";
        String userId = "admin";

        BoardRequestDTO requestDto = BoardRequestDTO.builder()
                .title(title)
                .content(content)
                .userId(userId)
                .build();

        String url = "http://localhost:" + port + "/board/"+board_num;

        String response =  mvc.perform(put(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization","Bearer "+token)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        CommonResponse commonResponse = objectMapper.readValue(response, CommonResponse.class);

        Long return_num = Long.valueOf((int)commonResponse.getData());

        BoardEntity entity = repository.findById(return_num).get();

        assertThat(entity.getTitle()).isEqualTo(title);
        assertThat(entity.getContent()).isEqualTo(content);
        assertThat(entity.getUserId()).isEqualTo(userId);
    }

    @DisplayName("글 작성 후 삭제 검증")
    @Test
    public void deleteBoard() throws Exception {

        BoardEntity createdEntity = repository.save(BoardEntity.builder()
                .title("title")
                .content("content")
                .userId("admin")
                .build());

        Long board_num = createdEntity.getBoard_num();

        BoardRequestDTO requestDto = BoardRequestDTO.builder()
                .userId("admin")
                .build();

        String url = "http://localhost:" + port + "/board/"+board_num;

        String response =  mvc.perform(delete(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .header("Authorization","Bearer "+token)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();

        CommonResponse commonResponse = objectMapper.readValue(response, CommonResponse.class);

        assertThat(commonResponse.isSuccess());
    }
}
