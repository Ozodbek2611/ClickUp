package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Project", description = "Project management APIs")
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping
    @Operation(summary = "Create project", description = "Creates a new project inside a space")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<ApiResponseDto<ProjectResponseDto>> create(@Valid @RequestBody ProjectRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(projectService.create(dto), "Project created successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit project", description = "Updates project name or other details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ApiResponseDto<ProjectResponseDto>> edit(@PathVariable UUID id,
                                                                   @Valid @RequestBody ProjectUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(projectService.edit(id, dto), "Project updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project", description = "Permanently deletes a project")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Project not found")
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Project deleted successfully"));
    }

    @GetMapping("/space/{spaceId}")
    @Operation(summary = "Get projects by space", description = "Returns all projects belonging to a specific space")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Projects fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Space not found")
    })
    public ResponseEntity<ApiResponseDto<List<ProjectResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(projectService.getBySpaceId(spaceId), "Projects fetched successfully"));
    }
}
