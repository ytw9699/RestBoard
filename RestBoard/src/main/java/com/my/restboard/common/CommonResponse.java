package com.my.restboard.common;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommonResponse<T> {

	private boolean success;//성공시 true, false
	private T data;
	private Error error;
}