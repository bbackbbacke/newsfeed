package org.example.newsfeed.controller;


import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.example.newsfeed.CommonResponse;
import org.example.newsfeed.dto.CommentRequestDTO;
import org.example.newsfeed.dto.CommentResponseDTO;
import org.example.newsfeed.entity.Comment;
import org.example.newsfeed.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    public final CommentService commentService;


    @PostMapping
    public ResponseEntity postComment(@PathVariable Long postId, @RequestBody CommentRequestDTO dto){
        Comment comment = commentService.creatComment(postId, dto);
        CommentResponseDTO response = new CommentResponseDTO(comment);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity getComments(@PathVariable Long postId){ //댓글 조회
        List<Comment> comments = commentService.getComments(postId);

        List<CommentResponseDTO> response = comments.stream()
            .map(CommentResponseDTO::new)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{commentId}") //댓글 내용 수정
    public ResponseEntity putComment(@PathVariable Long commentId,  @RequestBody CommentRequestDTO dto){
        Comment comment = commentService.updateComment(commentId, dto);
        //CommentResponseDTO response = new CommentResponseDTO(comment);
        return ResponseEntity.ok().body(CommonResponse.builder()
            .msg("댓글 수정에 성공했습니다")
            .statusCode(HttpStatus.OK.value())
            .build());
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().body(CommonResponse.builder()
            .msg("댓글 삭제에 성공했습니다")
            .statusCode(HttpStatus.OK.value())
            .build());

    }


}