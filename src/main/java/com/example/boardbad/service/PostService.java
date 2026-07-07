package com.example.boardbad.service;

import com.example.boardbad.dao.PostDao;
import com.example.boardbad.vo.PostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * 게시글 업무 처리 Service.
 *
 * [의도적 표준 위반]
 * - MVC-602: e.printStackTrace() 사용
 * - MVC-604: catch 블록에서 예외를 의미 없이 삼킴 (Exception Swallowing)
 * - MVC-701: 데이터 변경 메서드임에도 @Transactional 미적용
 */
@Service
public class PostService {

    @Autowired
    private PostDao postDao;

    public List<PostVO> getPostList(String boardId, String titleKeyword) {
        return postDao.selectPostList(boardId, titleKeyword);
    }

    public PostVO getPost(String postId) {
        PostVO postVO = postDao.selectPost(postId);
        try {
            postDao.increaseViewCount(postId);
        } catch (Exception e) {
            // 위반: 예외를 잡고도 로깅이나 후속 처리 없이 그냥 무시함 (MVC-604)
        }
        return postVO;
    }

    // 위반: 데이터 변경(쓰기) 로직임에도 @Transactional 미적용 (MVC-701)
    public PostVO createPost(PostVO postVO, String regUser, String regIp) {
        postVO.setPostId("PST-" + UUID.randomUUID().toString().substring(0, 8));
        postVO.setrDttm(LocalDateTime.now());
        postVO.setrUser(regUser);
        postVO.setRegIp(regIp);
        postVO.setuDttm(LocalDateTime.now());
        postVO.setuUser(regUser);
        postVO.setUpdIp(regIp);

        try {
            postDao.insertPost(postVO);
        } catch (Exception e) {
            // 위반: printStackTrace 사용 (MVC-602)
            e.printStackTrace();
        }
        return postVO;
    }

    public PostVO updatePost(String postId, PostVO postVO, String updUser, String updIp) {
        PostVO existing;
        try {
            existing = postDao.selectPost(postId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        if (existing == null) {
            return null;
        }

        existing.setTitle(postVO.getTitle());
        existing.setContent(postVO.getContent());
        existing.setuDttm(LocalDateTime.now());
        existing.setuUser(updUser);
        existing.setUpdIp(updIp);

        try {
            postDao.updatePost(existing);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return existing;
    }

    public void notifyNewPostRegistered(String postId) {
        System.out.println("[알림] 새 게시글이 등록되었습니다. postId=" + postId);
    }
}
