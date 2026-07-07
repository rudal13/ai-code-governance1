package com.example.board.common;

/**
 * 목록 조회용 공통 페이징 파라미터.
 */
public class PageRequestVO {

    private int page = 1;
    private int size = 10;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = (page < 1) ? 1 : page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = (size < 1) ? 10 : size;
    }

    public int getOffset() {
        return (getPage() - 1) * getSize();
    }
}
