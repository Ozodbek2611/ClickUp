package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.online.clickup.entity.Attachment;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.AttachmentMapper;
import uz.pdp.online.clickup.model.attachmentDto.AttachmentResponseDto;
import uz.pdp.online.clickup.repository.AttachmentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    private final String uploadDir = "uploads";

    @Transactional
    public AttachmentResponseDto upload(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Cannot upload an empty file");
        }

        try {
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            String originalFilename = file.getOriginalFilename();

            String extension = originalFilename != null && originalFilename.contains(".")
                    ? originalFilename.substring(originalFilename.lastIndexOf("."))
                    : "";

            String savedName = UUID.randomUUID() + extension;

            Path filePath = Paths.get(uploadDir, savedName);
            Files.copy(file.getInputStream(), filePath);

            Attachment attachment = Attachment.builder()
                    .name(savedName)
                    .originalName(originalFilename)
                    .size(file.getSize())
                    .contentType(file.getContentType())
                    .build();

            Attachment savedAttachment = attachmentRepository.save(attachment);
            log.info("File '{}' successfully uploaded and auto-detected with ID: {}", originalFilename, savedAttachment.getId());
            return attachmentMapper.toResponseDto(savedAttachment);

        } catch (IOException e) {
            log.error("Failed to store physical file", e);
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

    public Attachment getFileMetadata(UUID id) {
        return attachmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attachment metadata not found with ID: " + id));
    }

    public Path getFilePath(Attachment attachment) {
        return Paths.get(uploadDir, attachment.getName());
    }

    @Transactional
    public void delete(UUID id) {
        Attachment attachment = attachmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Attachment not found with ID: " + id));

        try {
            Path filePath = Paths.get(uploadDir, attachment.getName());
            Files.deleteIfExists(filePath);

            attachmentRepository.delete(attachment);
            log.info("Attachment and physical file successfully deleted with ID: {}", id);
        } catch (IOException e) {
            log.error("Failed to delete physical file with ID: {}", id, e);
            throw new RuntimeException("Could not delete the file. Error: " + e.getMessage());
        }
    }
}
