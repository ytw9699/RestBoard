package com.my.restboard.application.Board;

import com.my.restboard.application.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    Optional<BoardEntity> findByBoardNumAndUserId(Long boardNum, String userId);
}
