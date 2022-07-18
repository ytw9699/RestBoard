package com.my.restboard.application.User;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.restboard.common.CommonResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.Test;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @DisplayName("회원가입 후 검증")
    @Test
    public void createUser() throws Exception{

        String uuid = UUID.randomUUID().toString();
        String userId = "userId_"+uuid;
        String nickName = "nickName_"+uuid;
        String password = "password";

        UserSavedRequestDTO request = UserSavedRequestDTO.builder()
                .userId(userId)
                .nickName(nickName)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/auth/signup";

        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk());

        UserEntity user = userRepository.findByUserId(userId);

        assertThat(user.getUserId()).isEqualTo(userId);
        assertThat(user.getNickName()).isEqualTo(nickName);
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        assertThat(encoder.matches(password, user.getPassword()));

    }

    @DisplayName("인증후 토큰 유효성 검증")
    @Test
    public void authenticate() throws Exception{

        String userId = "admin";
        String password = "admin";

        UserSigninRequestDTO request = UserSigninRequestDTO.builder()
                .userId(userId)
                .password(password)
                .build();

        String url = "http://localhost:" + port + "/auth/signin";

        String response = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        ObjectMapper objectMapper = new ObjectMapper();
        CommonResponse commonResponse = objectMapper.readValue(response, CommonResponse.class);
        String token = (String)commonResponse.getData();
        String SECRET_KEY = "123gf1ffdhtycUeR4uUAt7WJa5raD7EN3Os4yyYuHxMEbSFdd4XXyffgB0F7Bq4dH";

        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token.substring(7))
                .getBody();

        assertThat(claims.getSubject()).isEqualTo(userId);
    }
}
