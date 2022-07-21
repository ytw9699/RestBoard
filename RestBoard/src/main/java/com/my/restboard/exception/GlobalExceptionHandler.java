package com.my.restboard.exception;
	import com.my.restboard.common.CommonResponse;
	import com.my.restboard.common.Error;
    import lombok.extern.slf4j.Slf4j;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.converter.HttpMessageNotReadableException;
    import org.springframework.web.bind.annotation.ExceptionHandler;
	import org.springframework.web.bind.annotation.ResponseStatus;
	import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {

        ex.printStackTrace();

        CommonResponse response = createCommonResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());

        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse handleIllegalArgumentException(IllegalArgumentException ex) {

        ex.printStackTrace();

        CommonResponse response = createCommonResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());

        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse handleRuntimeException(RuntimeException ex) {

        ex.printStackTrace();

        CommonResponse response = createCommonResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        return response;
    }

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
	public CommonResponse handleException(Exception ex) {

        ex.printStackTrace();

        CommonResponse response = createCommonResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        return response;
	}

    private CommonResponse createCommonResponse(String message, int status){

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

