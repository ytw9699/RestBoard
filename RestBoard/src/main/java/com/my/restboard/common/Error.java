package com.my.restboard.common;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Error{

	private String message;
	private int status;
}