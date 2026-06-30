package uz.pdp.online.clickup.project.controller;

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
import uz.pdp.online.clickup.common.enums.TaskPermission;
import uz.pdp.online.clickup.project.dto.ProjectUserRequestDto;
import uz.pdp.online.clickup.project.dto.ProjectUserResponseDto;
import uz.pdp.online.clickup.project.service.ProjectUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project-user")
@Tag(name = "Project User", description = "Project User APIs")
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    @Operation(summary = "Assign user to project", description = "Assigns a user to a project with permissions")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User assigned to project",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"User assigned to project\",\n  \"data\": {\n    \"projectId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"userId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n    \"taskPermission\": \"EDITOR\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"projectId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Project or user not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Project not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "User is already assigned to the project",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User is already assigned to Project! 3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<ProjectUserResponseDto>> assign(@RequestBody @Valid ProjectUserRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(projectUserService.assign(dto), "User assigned to project"));
    }

    @PutMapping("/{projectId}/{userId}")
    @Operation(summary = "Update user permission", description = "Updates the permission level of a project member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission updated",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Permission updated\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "User is not assigned to the project",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User is not assigned to Project! 3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> updatePermission(@PathVariable UUID projectId,
                                                                 @PathVariable UUID userId,
                                                                 @RequestParam TaskPermission taskPermission) {
        projectUserService.updatePermission(projectId, userId, taskPermission);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Permission updated"));
    }

    @DeleteMapping("/{projectId}/{userId}")
    @Operation(summary = "Remove user from project", description = "Removes a user from a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unassigned from project",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"User unassigned from project\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "User is not assigned to the project",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User is not assigned to Project3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> unassign(@PathVariable UUID projectId, @PathVariable UUID userId) {
        projectUserService.unassign(projectId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "User unassigned from project"));
    }

    @Operation(summary = "Get project members", description = "Returns all users assigned to a project")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project users",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Project users\",\n  \"data\": [\n    {\n      \"projectId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"userId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n      \"taskPermission\": \"EDITOR\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponseDto<List<ProjectUserResponseDto>>> getByProjectId(@PathVariable UUID projectId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(projectUserService.getByProjectId(projectId), "Project users"));
    }
}