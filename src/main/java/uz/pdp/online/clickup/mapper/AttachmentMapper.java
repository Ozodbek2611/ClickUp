package uz.pdp.online.clickup.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import uz.pdp.online.clickup.entity.domain.Attachment;
import uz.pdp.online.clickup.model.attachmentDto.AttachmentResponseDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {

    @Mapping(target = "downloadUrl", expression = "java(\"/api/attachment/download/\" + attachment.getId())")
    AttachmentResponseDto toResponseDto(Attachment attachment);

    List<AttachmentResponseDto> toResponseDtoList(List<Attachment> attachments);
}
