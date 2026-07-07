package com.example.board.exception;

/**
 * 요청한 리소스를 찾을 수 없는 경우 발생하는 예외.
 */
public class NotFoundException extends BusinessException {

    public NotFoundException(String message) {
        super("NOT_FOUND", message);
    }
}
