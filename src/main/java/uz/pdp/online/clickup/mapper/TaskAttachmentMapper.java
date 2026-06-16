package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.TaskAttachment;
import uz.pdp.online.clickup.model.taskAttachmentDto.TaskAttachmentResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskAttachmentMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "attachmentId", source = "attachment.id")
    TaskAttachmentResponseDto toResponseDto(TaskAttachment taskAttachment);

    List<TaskAttachmentResponseDto> toResponseDtoList(List<TaskAttachment> taskAttachments);
}
