package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Project created successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Backend API\",\n    \"color\": \"#2ECC71\",\n    \"spaceId\": null,\n    \"createdAt\": \"2026-06-23T10:15:30.000+00:00\",\n    \"createdBy\": null\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"name: Project name cannot be blank\", \"spaceId: Space ID cannot be null\"]\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<ProjectResponseDto>> create(@Valid @RequestBody ProjectRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(projectService.create(dto), "Project created successfully"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit project", description = "Updates project name or other details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Project updated successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Frontend App\",\n    \"color\": \"#E74C3C\",\n    \"spaceId\": null,\n    \"createdAt\": \"2026-06-23T10:15:30.000+00:00\",\n    \"createdBy\": null\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"name: Project name cannot be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Project not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<ProjectResponseDto>> edit(@PathVariable UUID id,
                                                                   @Valid @RequestBody ProjectUpdateDto dto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(projectService.edit(id, dto), "Project updated successfully"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete project", description = "Permanently deletes a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Project deleted successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Project not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Project not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID id) {
        projectService.delete(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Project deleted successfully"));
    }

    @GetMapping("/space/{spaceId}")
    @Operation(summary = "Get projects by space", description = "Returns all projects belonging to a specific space")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Projects fetched successfully\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"name\": \"Backend API\",\n      \"color\": \"#2ECC71\",\n      \"spaceId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n      \"createdAt\": \"2026-06-23T10:15:30.000+00:00\",\n      \"createdBy\": null\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<List<ProjectResponseDto>>> getBySpaceId(@PathVariable UUID spaceId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(projectService.getBySpaceId(spaceId), "Projects fetched successfully"));
    }
}
