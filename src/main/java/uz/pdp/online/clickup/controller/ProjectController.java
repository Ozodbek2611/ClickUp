package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.projectDto.ProjectRequestDto;
import uz.pdp.online.clickup.model.projectDto.ProjectResponseDto;
import uz.pdp.online.clickup.model.projectDto.ProjectUpdateDto;
import uz.pdp.online.clickup.service.ProjectService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/project")
@RequiredArgsConstructor
@Tag(name = "Project", description = "Project APIs")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<ProjectResponseDto>> create(@Valid @RequestBody ProjectRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(projectService.create(dto), "Project created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<ProjectResponseDto>> edit(@PathVariable UUID id,
                                                                @Valid @RequestBody ProjectUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(projectService.edit(id, dto), "Project updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Project deleted successfully"));
    }

    @GetMapping("/space/{spaceId}")
    public ResponseEntity<ApiResponseDto<List<ProjectResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(projectService.getBySpaceId(spaceId), "Projects fetched successfully"));
    }
}
