package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import ru.skypro.homework.dto.comments.CommentDto;
import ru.skypro.homework.dto.comments.CreateOrUpdateCommentDto;
import ru.skypro.homework.model.Comment;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper {
    @Mappings({
            @Mapping(target = "pk", source = "id"),
            @Mapping(target = "author", source = "author.id"),
            @Mapping(target = "authorImage", source = "author.image"),
            @Mapping(target = "authorFirstName", source = "author.firstName")
    })
    CommentDto commentToCommentDTO(Comment comment);
    List<CommentDto> commentsToCommentsDTO(List<Comment> comments);
    CreateOrUpdateCommentDto commentToCreateOrUpdateCommentDto(Comment comment);

    @Mappings({
            @Mapping(target = "id", source = "pk"),
            @Mapping(target = "author.id", source = "author")
    })
    Comment commentDTOToComment(CommentDto commentDto);
}
