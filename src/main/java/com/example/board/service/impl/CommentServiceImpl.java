package com.example.board.service.impl;

import com.example.board.common.IdGenerator;
import com.example.board.dao.CommentDao;
import com.example.board.exception.NotFoundException;
import com.example.board.service.CommentService;
import com.example.board.vo.CommentVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 댓글 Service 구현체. (MVC-003)
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Override
    @Transactional(readOnly = true)
    public List<CommentVO> getCommentList(String postId) {
        return commentDao.selectCommentList(postId);
    }

    @Override
    @Transactional
    public CommentVO createComment(CommentVO commentVO, String regUser, String regIp) {
        commentVO.setCmtId(IdGenerator.generate("CMT"));
        commentVO.setMbrId(regUser);
        commentVO.setRegDttm(LocalDateTime.now());
        commentVO.setRegUser(regUser);
        commentVO.setRegIp(regIp);
        commentVO.setUpdDttm(LocalDateTime.now());
        commentVO.setUpdUser(regUser);
        commentVO.setUpdIp(regIp);

        commentDao.insertComment(commentVO);
        logger.info("댓글이 등록되었습니다. cmtId={}", commentVO.getCmtId());
        return commentVO;
    }

    @Override
    @Transactional
    public CommentVO updateComment(String cmtId, CommentVO commentVO, String updUser, String updIp) {
        CommentVO existing = commentDao.selectComment(cmtId);
        if (existing == null) {
            throw new NotFoundException("댓글을 찾을 수 없습니다. cmtId=" + cmtId);
        }
        existing.setContent(commentVO.getContent());
        existing.setUpdDttm(LocalDateTime.now());
        existing.setUpdUser(updUser);
        existing.setUpdIp(updIp);

        commentDao.updateComment(existing);
        logger.info("댓글이 수정되었습니다. cmtId={}", cmtId);
        return existing;
    }

    @Override
    @Transactional
    public void deleteComment(String cmtId, String updUser, String updIp) {
        CommentVO existing = commentDao.selectComment(cmtId);
        if (existing == null) {
            throw new NotFoundException("댓글을 찾을 수 없습니다. cmtId=" + cmtId);
        }
        existing.setUpdDttm(LocalDateTime.now());
        existing.setUpdUser(updUser);
        existing.setUpdIp(updIp);

        commentDao.deleteComment(existing);
        logger.info("댓글이 삭제(논리) 처리되었습니다. cmtId={}", cmtId);
    }
}
