package com.example.board.service.impl;

import com.example.board.common.IdGenerator;
import com.example.board.common.PageRequestVO;
import com.example.board.dao.PostDao;
import com.example.board.exception.NotFoundException;
import com.example.board.service.PostService;
import com.example.board.vo.PostVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시글 Service 구현체. (MVC-003)
 */
@Service
public class PostServiceImpl implements PostService {

    private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);

    private static final String YES = "Y";
    private static final String NO = "N";

    @Autowired
    private PostDao postDao;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getPostList(String boardId, PageRequestVO pageRequestVO) {
        List<PostVO> list = postDao.selectPostList(boardId, pageRequestVO);
        int totalCount = postDao.selectPostCount(boardId);

        Map<String, Object> result = new HashMap<>();
        result.put("list", list);
        result.put("totalCount", totalCount);
        result.put("page", pageRequestVO.getPage());
        result.put("size", pageRequestVO.getSize());
        return result;
    }

    @Override
    @Transactional
    public PostVO getPost(String postId) {
        PostVO postVO = postDao.selectPost(postId);
        if (postVO == null) {
            throw new NotFoundException("게시글을 찾을 수 없습니다. postId=" + postId);
        }
        postDao.increaseViewCnt(postId);
        postVO.setViewCnt(postVO.getViewCnt() == null ? 1 : postVO.getViewCnt() + 1);
        return postVO;
    }

    @Override
    @Transactional
    public PostVO createPost(PostVO postVO, String regUser, String regIp) {
        postVO.setPostId(IdGenerator.generate("PST"));
        postVO.setMbrId(regUser);
        postVO.setViewCnt(0);
        postVO.setRcmdCnt(0);
        if (postVO.getNoticeYn() == null) {
            postVO.setNoticeYn(NO);
        }
        if (postVO.getPinYn() == null) {
            postVO.setPinYn(NO);
        }
        if (postVO.getPubYn() == null) {
            postVO.setPubYn(YES);
        }
        postVO.setRegDttm(LocalDateTime.now());
        postVO.setRegUser(regUser);
        postVO.setRegIp(regIp);
        postVO.setUpdDttm(LocalDateTime.now());
        postVO.setUpdUser(regUser);
        postVO.setUpdIp(regIp);

        postDao.insertPost(postVO);
        logger.info("게시글이 등록되었습니다. postId={}", postVO.getPostId());
        return postVO;
    }

    @Override
    @Transactional
    public PostVO updatePost(String postId, PostVO postVO, String updUser, String updIp) {
        PostVO existing = postDao.selectPost(postId);
        if (existing == null) {
            throw new NotFoundException("게시글을 찾을 수 없습니다. postId=" + postId);
        }
        existing.setTitle(postVO.getTitle());
        existing.setContent(postVO.getContent());
        existing.setNoticeYn(postVO.getNoticeYn());
        existing.setPinYn(postVO.getPinYn());
        existing.setPubYn(postVO.getPubYn());
        existing.setUpdDttm(LocalDateTime.now());
        existing.setUpdUser(updUser);
        existing.setUpdIp(updIp);

        postDao.updatePost(existing);
        logger.info("게시글이 수정되었습니다. postId={}", postId);
        return existing;
    }

    @Override
    @Transactional
    public void deletePost(String postId, String updUser, String updIp) {
        PostVO existing = postDao.selectPost(postId);
        if (existing == null) {
            throw new NotFoundException("게시글을 찾을 수 없습니다. postId=" + postId);
        }
        existing.setUpdDttm(LocalDateTime.now());
        existing.setUpdUser(updUser);
        existing.setUpdIp(updIp);

        postDao.deletePost(existing);
        logger.info("게시글이 삭제(논리) 처리되었습니다. postId={}", postId);
    }
}
