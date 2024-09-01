/*package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.model.Comment;
import ru.skypro.homework.model.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommentMapperTest {

    private final CommentMapper commentMapper = Mappers.getMapper(CommentMapper.class);

    @Test
    public void commentToCommentDto_shouldMapCorrectly() {
        // Given
        Comment comment = new Comment();
        comment.setId(1);
        comment.setAuthor(new User(2, "John", "Doe", null));
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        comment.setText("This is a comment.");

        // When
        CommentDto commentDto = commentMapper.commentToCommentDTO(comment);

        // Then
        assertEquals(1, commentDto.getPk());
        assertEquals(2, commentDto.getAuthor());
        assertEquals("John", commentDto.getAuthorFirstName());
        assertEquals("This is a comment.", commentDto.getText());
        assertEquals(comment.getCreatedAt().getTime(), commentDto.getCreatedAt());
    }

    @Test
    public void commentsToCommentsDTO_shouldMapCorrectly() {
        // Given
        List<Comment> comments = Arrays.asList(
                new Comment(1, new User(2, "John", "Doe", null), "This is a comment.", Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC))),
                new Comment(2, new User(3, "Jane", "Doe", null), "This is another comment.", Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)))
        );

        // When
        List<CommentDto> commentDtos = commentMapper.commentsToCommentsDTO(comments);

        // Then
        assertEquals(2, commentDtos.size());
        assertEquals(1, commentDtos.get(0).getPk());
        assertEquals(2, commentDtos.get(0).getAuthor());
        assertEquals("John", commentDtos.get(0).getAuthorFirstName());
        assertEquals("This is a comment.", commentDtos.get(0).getText());
        assertEquals(2, commentDtos.get(1).getPk());
        assertEquals(3, commentDtos.get(1).getAuthor());
        assertEquals("Jane", commentDtos.get(1).getAuthorFirstName());
        assertEquals("This is another comment.", commentDtos.get(1).getText());
    }

    @Test
    public void commentToCreateOrUpdateCommentDto_shouldMapCorrectly() {
        // Given
        Comment comment = new Comment();
        comment.setId(1);
        comment.setAuthor(new User(2, "John", "Doe", null));
        comment.setCreatedAt(Timestamp.valueOf(LocalDateTime.now(ZoneOffset.UTC)));
        comment.setText("This is a comment.");

        // When
        CreateOrUpdateCommentDto createOrUpdateCommentDto = commentMapper.commentToCreateOrUpdateCommentDto(comment);

        // Then
        assertEquals(1L, createOrUpdateCommentDto.getId());
        assertEquals(2L, createOrUpdateCommentDto.getAuthorId());
        assertEquals("This is a comment.", createOrUpdateCommentDto.getText());
    }
}*/

