package com.example.board.common;

import java.util.UUID;

/**
 * 식별자(PK) 생성 공통 유틸리티.
 */
public final class IdGenerator {

    private IdGenerator() {
    }

    public static String generate(String prefix) {
        return prefix + "-" + UUID.randomUUID().toString().replace("-", "").substring(0, 20);
    }
}
