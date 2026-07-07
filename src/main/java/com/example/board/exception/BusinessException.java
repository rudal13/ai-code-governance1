package com.example.board.exception;

/**
 * 업무 처리 중 발생하는 예외에 대한 공통 예외 클래스.
 * 모든 사용자 정의 예외는 본 클래스를 상속하여 일관된 방식으로 처리한다. (MVC-604)
 */
public class BusinessException extends RuntimeException {

    private final String code;

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
