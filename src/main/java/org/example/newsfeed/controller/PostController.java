package org.example.newsfeed.controller;

import lombok.AllArgsConstructor;
import org.example.newsfeed.CommonResponse;
import org.example.newsfeed.dto.PostRequestDTO;
import org.example.newsfeed.dto.PostResponseDTO;
import org.example.newsfeed.dto.SearchRequestDTO;
import org.example.newsfeed.entity.Post;
import org.example.newsfeed.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
public class PostController {

    public final PostService postService;

    @PostMapping
    public ResponseEntity postPost(@RequestBody PostRequestDTO dto) {
        Post post = postService.createPost(dto);
        PostResponseDTO response = new PostResponseDTO(post);
        return ResponseEntity.ok(response);
    }

    @GetMapping("{postId}")
    public ResponseEntity getPost(@PathVariable Long postId) {
        Post post = postService.getPost(postId);
        PostResponseDTO response = new PostResponseDTO(post);
        return ResponseEntity.ok(response);
    }


    @PutMapping("{postId}")
    public ResponseEntity updatePost(@PathVariable Long postId, @RequestBody PostRequestDTO dto) {
        Post post = postService.updatePost(postId, dto);
        //PostResponseDTO response = new PostResponseDTO(post);
        return ResponseEntity.ok().body(CommonResponse.builder()
            .msg("게시글 수정에 성공했습니다")
            .statusCode(HttpStatus.OK.value())
            .build());
    }


    @DeleteMapping("{postId}")
    public ResponseEntity<CommonResponse> deletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
        return ResponseEntity.ok().body(CommonResponse.builder()
            .msg("게시글 삭제에 성공했습니다")
            .statusCode(HttpStatus.OK.value())
            .build());
    }

    @GetMapping("/page")
    public Page<PostResponseDTO> getPostPage(
        @RequestParam("page") int page, //페이지 번호 1부터
        @RequestParam("search") boolean canSearch,  //기간별 검색 기능 사용할지
        @RequestBody(required = false) SearchRequestDTO dto) { //기간
        return postService.getPosts(page - 1, canSearch, dto);
    }

}