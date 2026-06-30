package uz.pdp.online.clickup.checkList.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.checkList.dto.CheckListRequestDto;
import uz.pdp.online.clickup.checkList.dto.CheckListResponseDto;
import uz.pdp.online.clickup.checkList.service.CheckListService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
@Tag(name = "Checklist", description = "Checklist APIs")
public class CheckListController {

    private final CheckListService checkListService;

    @Operation(summary = "Create checklist", description = "Creates a new checklist for a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CheckList created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"CheckList created successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Launch checklist\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Task not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Task not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<CheckListResponseDto>> create(@RequestBody CheckListRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(checkListService.create(dto), "CheckList created successfully"));
    }

    @PatchMapping("/{checkListId}")
    @Operation(summary = "Edit checklist", description = "Updates the name of a checklist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CheckList updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"CheckList updated successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Updated checklist name\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "CheckList not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"CheckList not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<CheckListResponseDto>> edit(@PathVariable UUID checkListId,
                                                                     @RequestParam String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(checkListService.edit(checkListId, name),
                        "CheckList updated successfully"));
    }

    @DeleteMapping("/{checkListId}")
    @Operation(summary = "Delete checklist", description = "Permanently deletes a checklist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CheckList deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"CheckList deleted successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "CheckList not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"CheckList not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID checkListId) {
        checkListService.delete(checkListId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "CheckList deleted successfully"));
    }

    @Operation(summary = "Get checklists by task", description = "Returns all checklists for a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "CheckLists fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"CheckLists fetched successfully\",\n  \"data\": [\n    {\n      \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"name\": \"Launch checklist\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<CheckListResponseDto>>> getByTask(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(checkListService.getByTaskId(taskId),
                        "CheckLists fetched successfully"));
    }
}
