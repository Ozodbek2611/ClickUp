package uz.pdp.online.clickup.icon.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.attachment.entity.Attachment;
import uz.pdp.online.clickup.icon.entity.Icon;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.icon.mapper.IconMapper;
import uz.pdp.online.clickup.icon.dto.IconRequestDto;
import uz.pdp.online.clickup.icon.dto.IconResponseDto;
import uz.pdp.online.clickup.attachment.repository.AttachmentRepository;
import uz.pdp.online.clickup.icon.repository.IconRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IconService {

    private final IconRepository iconRepository;
    private final AttachmentRepository attachmentRepository;
    private final IconMapper iconMapper;

    @Transactional
    public IconResponseDto create(IconRequestDto dto) {
        Attachment attachment = null;
        if (dto.getAttachmentId() != null) {
            attachment = attachmentRepository.findById(dto.getAttachmentId())
                    .orElseThrow(() -> new NotFoundException("Attachment not found with ID: " + dto.getAttachmentId()));
        }

        Icon icon = Icon.builder()
                .attachment(attachment)
                .color(dto.getColor())
                .initialLetter(dto.getInitialLetter())
                .build();

        Icon savedIcon = iconRepository.save(icon);
        log.info("Icon successfully created with ID: {}", savedIcon.getId());
        return iconMapper.toResponseDto(savedIcon);
    }

    @Transactional
    public IconResponseDto edit(UUID id, IconRequestDto dto) {
        Icon icon = iconRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Icon not found with ID: " + id));

        Attachment attachment = null;
        if (dto.getAttachmentId() != null) {
            attachment = attachmentRepository.findById(dto.getAttachmentId())
                    .orElseThrow(() -> new NotFoundException("Attachment not found with ID: " + dto.getAttachmentId()));
        }

        icon.setAttachment(attachment);
        icon.setColor(dto.getColor());
        icon.setInitialLetter(dto.getInitialLetter());

        log.info("Icon successfully updated with ID: {}", id);
        return iconMapper.toResponseDto(icon);
    }

    @Transactional
    public void delete(UUID id) {
        Icon icon = iconRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Icon not found with ID: " + id));
        iconRepository.delete(icon);
        log.info("Icon successfully deleted with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<IconResponseDto> getAll() {
        List<Icon> icons = iconRepository.findAll();
        return iconMapper.toResponseDtoList(icons);
    }
}
