package com.example.board.controller;

import com.example.board.common.ApiResponse;
import com.example.board.service.FileService;
import com.example.board.vo.FileVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 첨부파일 Controller.
 * Controller는 요청 수신, 파라미터 검증, Service 호출, 결과 반환만 수행한다. (MVC-002)
 *
 * 실제 파일 바이너리 업로드/스토리지 저장 로직은 별도의 파일 스토리지 모듈(공통 컴포넌트)에서
 * 처리하고, 본 Controller는 메타데이터(파일명, 경로, 크기, 확장자) CRUD만 담당한다.
 */
@RestController
@RequestMapping("/api/posts/{postId}/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping
    public ApiResponse<List<FileVO>> getFileList(@PathVariable String postId) {
        return ApiResponse.success(fileService.getFileList(postId));
    }

    @PostMapping
    public ApiResponse<FileVO> registerFile(@PathVariable String postId,
                                             @Valid @RequestBody FileVO fileVO,
                                             HttpServletRequest request) {
        fileVO.setPostId(postId);
        String regUser = resolveUser(request);
        String regIp = resolveIp(request);
        return ApiResponse.success("첨부파일이 등록되었습니다.", fileService.registerFile(fileVO, regUser, regIp));
    }

    @DeleteMapping("/{fileId}")
    public ApiResponse<Void> removeFile(@PathVariable String postId,
                                         @PathVariable String fileId,
                                         HttpServletRequest request) {
        String updUser = resolveUser(request);
        String updIp = resolveIp(request);
        fileService.removeFile(fileId, updUser, updIp);
        return ApiResponse.success("첨부파일이 삭제되었습니다.", null);
    }

    private String resolveUser(HttpServletRequest request) {
        String user = request.getHeader("X-USER-ID");
        return (user == null || user.isBlank()) ? "SYSTEM" : user;
    }

    private String resolveIp(HttpServletRequest request) {
        return request.getRemoteAddr();
    }
}
