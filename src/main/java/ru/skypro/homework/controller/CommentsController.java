package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.comments.CommentsDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.dto.users.UpdateUserDto;

@Slf4j
@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RequestMapping("ads")
public class CommentsController {
    @GetMapping("/{id}/comments")
    public ResponseEntity getComment(@PathVariable Integer id,
                                     @RequestBody CommentsDto commentsDto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/comments")
    public ResponseEntity<?> setComment(@PathVariable Integer id, CreateOrUpdateCommentDto createOrUpdateCommentDto) {
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{adId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Integer adId,
                                        @PathVariable Integer commentId) {

            return ResponseEntity.ok().build();

    }
    @PatchMapping("/{adId}/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Integer adId,
                                        @PathVariable Integer commentId,
                                        @RequestBody CreateOrUpdateCommentDto createOrUpdateCommentDto) {

            return ResponseEntity.ok().build();

    }
}
