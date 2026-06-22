package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
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
    public ResponseEntity<ApiResponseDto<TagResponseDto>> create(@Valid @RequestBody TagRequestDto dto) {
        TagResponseDto response = tagService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Tag created successfully"));
    }

        @Operation(summary = "Add tag to task", description = "Associates an existing tag with a task")

    @PostMapping("/add-to-task")
    public ResponseEntity<ApiResponseDto<TaskTagResponseDto>> addToTask(@Valid @RequestBody TaskTagRequestDto dto) {
        TaskTagResponseDto response = tagService.addToTask(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Tag added to task successfully"));
    }

        @Operation(summary = "Remove tag from task", description = "Removes a tag association from a task")

    @DeleteMapping("/task/{taskId}/{tagId}")
    public ResponseEntity<ApiResponseDto<Void>> removeFromTask(@PathVariable UUID taskId,
                                                            @PathVariable UUID tagId) {
        tagService.removeFromTask(taskId, tagId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Tag removed from task successfully"));
    }

        @Operation(summary = "Get tags by workspace", description = "Returns all tags in a given workspace")

    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<ApiResponseDto<List<TagResponseDto>>> getByWorkspace(@PathVariable Long workspaceId) {
        List<TagResponseDto> response = tagService.getByWorkspaceId(workspaceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Tags fetched successfully"));
    }
}
