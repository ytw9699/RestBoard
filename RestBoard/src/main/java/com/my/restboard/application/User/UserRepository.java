package com.my.restboard.application.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

	Boolean existsByUserId(String userId);
	UserEntity findByUserId(String userId);
	Boolean existsByNickName(String userId);
}
