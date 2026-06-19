package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.entity.domain.Attachment;
import uz.pdp.online.clickup.model.attachmentDto.AttachmentResponseDto;
import uz.pdp.online.clickup.service.AttachmentService;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
@Tag(name = "Attachment", description = "Attachment APIs")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponseDto<AttachmentResponseDto>> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(attachmentService.upload(file), "File uploaded successfully"));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable UUID id) {
        Attachment metadata = attachmentService.getFileMetadata(id);
        Path path = attachmentService.getFilePath(metadata);

        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(metadata.getContentType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                                + metadata.getOriginalName() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID id) {
        attachmentService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Attachment deleted successfully"));
    }
}
