package com.example.board.service.impl;

import com.example.board.common.IdGenerator;
import com.example.board.dao.FileDao;
import com.example.board.exception.NotFoundException;
import com.example.board.service.FileService;
import com.example.board.vo.FileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 첨부파일 Service 구현체. (MVC-003)
 */
@Service
public class FileServiceImpl implements FileService {

    private static final Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Autowired
    private FileDao fileDao;

    @Override
    @Transactional(readOnly = true)
    public List<FileVO> getFileList(String postId) {
        return fileDao.selectFileList(postId);
    }

    @Override
    @Transactional
    public FileVO registerFile(FileVO fileVO, String regUser, String regIp) {
        fileVO.setFileId(IdGenerator.generate("FIL"));
        fileVO.setRegDttm(LocalDateTime.now());
        fileVO.setRegUser(regUser);
        fileVO.setRegIp(regIp);
        fileVO.setUpdDttm(LocalDateTime.now());
        fileVO.setUpdUser(regUser);
        fileVO.setUpdIp(regIp);

        fileDao.insertFile(fileVO);
        logger.info("첨부파일이 등록되었습니다. fileId={}", fileVO.getFileId());
        return fileVO;
    }

    @Override
    @Transactional
    public void removeFile(String fileId, String updUser, String updIp) {
        FileVO existing = fileDao.selectFile(fileId);
        if (existing == null) {
            throw new NotFoundException("첨부파일을 찾을 수 없습니다. fileId=" + fileId);
        }
        existing.setUpdDttm(LocalDateTime.now());
        existing.setUpdUser(updUser);
        existing.setUpdIp(updIp);

        fileDao.deleteFile(existing);
        logger.info("첨부파일이 삭제(논리) 처리되었습니다. fileId={}", fileId);
    }
}
