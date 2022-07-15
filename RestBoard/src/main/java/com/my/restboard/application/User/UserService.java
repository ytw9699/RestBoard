package com.my.restboard.application.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserEntity create(final UserEntity userEntity) {

		if(userEntity == null || userEntity.getUserId() == null || userEntity.getNickName() == null) {
			throw new RuntimeException("Invalid arguments");
		}

		if(userRepository.existsByUserId(userEntity.getUserId())) {
			throw new RuntimeException("이미 존재하는 아이디입니다.");
		}

		if(userRepository.existsByNickName(userEntity.getUserId())) {
			throw new RuntimeException("이미 존재하는 닉네임입니다.");
		}

		return userRepository.save(userEntity);
	}

	public UserEntity getUserAuthenticated(final String userId, final String password, final PasswordEncoder encoder) throws Exception{

		final UserEntity userEntity = userRepository.findByUserId(userId);

		if(userEntity == null){
			throw new UsernameNotFoundException(userId);
		}

		if(!encoder.matches(password, userEntity.getPassword())) {
			throw new BadCredentialsException(userId);
		}

		return userEntity;
	}
}
