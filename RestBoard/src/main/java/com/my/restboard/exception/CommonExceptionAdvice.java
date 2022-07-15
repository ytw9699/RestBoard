//공통 예외 처리 설계중

/*package com.my.restboard.exception;
	import com.my.restboard.common.CommonResponse;
	import com.my.restboard.common.Error;
	import org.springframework.http.HttpStatus;
	import org.springframework.web.bind.annotation.ExceptionHandler;
	import org.springframework.web.bind.annotation.ResponseStatus;
	import org.springframework.web.bind.annotation.RestControllerAdvice;
	import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestControllerAdvice
public class CommonExceptionAdvice {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
	public CommonResponse handleException(Exception ex) {

		System.out.println("handleException .......");

		ex.printStackTrace();

		Error error = Error.builder()
				.message(ex.getMessage())
				.status(500)
				.build();

		CommonResponse response = CommonResponse.builder()
				.success(false)
				.error(error)
				.build();

		return response;
	}
}*/

