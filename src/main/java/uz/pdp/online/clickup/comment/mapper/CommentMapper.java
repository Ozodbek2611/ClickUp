package uz.pdp.online.clickup.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.comment.entity.Comment;
import uz.pdp.online.clickup.comment.dto.CommentResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "taskId", source = "task.id")
    CommentResponseDto toResponseDto(Comment comment);

    List<CommentResponseDto> toResponseDtoList(List<Comment> comments);
}
