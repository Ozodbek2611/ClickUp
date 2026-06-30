package uz.pdp.online.clickup.task.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.task.entity.TaskAttachment;
import uz.pdp.online.clickup.task.dto.TaskAttachmentResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskAttachmentMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "attachmentId", source = "attachment.id")
    TaskAttachmentResponseDto toResponseDto(TaskAttachment taskAttachment);

    List<TaskAttachmentResponseDto> toResponseDtoList(List<TaskAttachment> taskAttachments);
}
