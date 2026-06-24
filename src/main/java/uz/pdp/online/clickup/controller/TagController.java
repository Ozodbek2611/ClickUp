package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.tagDto.TagRequestDto;
import uz.pdp.online.clickup.model.tagDto.TagResponseDto;
import uz.pdp.online.clickup.model.taskTagDto.TaskTagRequestDto;
import uz.pdp.online.clickup.model.taskTagDto.TaskTagResponseDto;
import uz.pdp.online.clickup.service.TagService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Tag", description = "Tag APIs")
public class TagController {

    private final TagService tagService;

    @PostMapping
    @Operation(summary = "Create tag", description = "Creates a new tag in a workspace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Tag created successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Urgent\",\n    \"color\": \"#E74C3C\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"name: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Workspace not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Workspace not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<TagResponseDto>> create(@Valid @RequestBody TagRequestDto dto) {
        TagResponseDto response = tagService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Tag created successfully"));
    }

    @Operation(summary = "Add tag to task", description = "Associates an existing tag with a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag added to task successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Tag added to task successfully\",\n  \"data\": {\n    \"taskId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"tagId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"taskId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task or tag not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "Tag is already assigned to the task",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Tag is already assigned to Task3fa85f64-5717-4562-b3fc-2c963f66afa7\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping("/add-to-task")
    public ResponseEntity<ApiResponseDto<TaskTagResponseDto>> addToTask(@Valid @RequestBody TaskTagRequestDto dto) {
        TaskTagResponseDto response = tagService.addToTask(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Tag added to task successfully"));
    }

    @Operation(summary = "Remove tag from task", description = "Removes a tag association from a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag removed from task successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Tag removed from task successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Tag is not assigned to this task",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @DeleteMapping("/task/{taskId}/{tagId}")
    public ResponseEntity<ApiResponseDto<Void>> removeFromTask(@PathVariable UUID taskId,
                                                               @PathVariable UUID tagId) {
        tagService.removeFromTask(taskId, tagId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Tag removed from task successfully"));
    }

    @Operation(summary = "Get tags by workspace", description = "Returns all tags in a given workspace")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tags fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Tags fetched successfully\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"name\": \"Urgent\",\n      \"color\": \"#E74C3C\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<ApiResponseDto<List<TagResponseDto>>> getByWorkspace(@PathVariable Long workspaceId) {
        List<TagResponseDto> response = tagService.getByWorkspaceId(workspaceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Tags fetched successfully"));
    }
}
