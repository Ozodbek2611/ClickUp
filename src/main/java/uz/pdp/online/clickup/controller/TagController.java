package uz.pdp.online.clickup.controller;

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
    public ResponseEntity<ApiResponseDto<TagResponseDto>> create(@Valid @RequestBody TagRequestDto dto) {
        TagResponseDto response = tagService.create(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Tag created successfully"));
    }

    @PostMapping("/add-to-task")
    public ResponseEntity<ApiResponseDto<TaskTagResponseDto>> addToTask(@Valid @RequestBody TaskTagRequestDto dto) {
        TaskTagResponseDto response = tagService.addToTask(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(response, "Tag added to task successfully"));
    }

    @DeleteMapping("/task/{taskId}/{tagId}")
    public ResponseEntity<ApiResponseDto<Void>> removeFromTask(@PathVariable UUID taskId,
                                                            @PathVariable UUID tagId) {
        tagService.removeFromTask(taskId, tagId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Tag removed from task successfully"));
    }

    @GetMapping("/workspace/{workspaceId}")
    public ResponseEntity<ApiResponseDto<List<TagResponseDto>>> getByWorkspace(@PathVariable Long workspaceId) {
        List<TagResponseDto> response = tagService.getByWorkspaceId(workspaceId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(response, "Tags fetched successfully"));
    }
}
