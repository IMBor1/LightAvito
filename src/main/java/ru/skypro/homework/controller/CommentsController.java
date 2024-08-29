package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.service.CommentService;
import ru.skypro.homework.service.impl.CommentServiceImpl;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("/ads")
public class CommentsController {

    private final CommentService commentService;


    @GetMapping("/{id}/comments")
    @Operation(
            summary = "Получение комментариев обЪявления",
            tags = "Комментарии"
    )
    public ResponseEntity<CommentsDto> getComment(@PathVariable Integer id) {
        CommentsDto commentsDto = commentService.getComments(id);
        return ResponseEntity.ok(commentsDto);
    }

    @PostMapping("/{id}/comments")
    @Operation(
            summary = "Добавление комментария к обЪявлению",
            tags = "Комментарии"
    )
    public ResponseEntity<CommentDto> setComment(@PathVariable Integer id,
                                                 @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto,
                                                  Authentication authentication) {
        CommentDto commentDto = commentService.setComment(id, createOrUpdateCommentDto, authentication.getName());
        return ResponseEntity.ok(commentDto);
    }

    @DeleteMapping("/{adId}/comments/{commentId}")
    @Operation(
            summary = "Удаление комментария",
            tags = "Комментарии"
    )
    public void deleteComment(@PathVariable Integer adId,
                                           @PathVariable Integer commentId) {
        commentService.deleteComment(adId,commentId);

    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @Operation(
            summary = "Обновление комментария",
            tags = "Комментарии"
    )
    @PreAuthorize("hasRole( 'ADMIN' ) or @commentServiceImpl.find(commentId).author.username.equals(authentication.name)")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Integer adId,
                                        @PathVariable Integer commentId,
                                        @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        CommentDto commentDto = commentService.updateComment(adId, commentId, createOrUpdateCommentDto);
        return ResponseEntity.ok(commentDto);
    }
}
