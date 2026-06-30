package uz.pdp.online.clickup.checkListItem.controller;

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
import uz.pdp.online.clickup.checkListItem.dto.CheckListItemRequestDto;
import uz.pdp.online.clickup.checkListItem.dto.CheckListItemResponseDto;
import uz.pdp.online.clickup.checkListItem.service.CheckListItemService;

import java.util.UUID;

@RestController
@RequestMapping("/api/checklist-item")
@RequiredArgsConstructor
@Tag(name = "Checklist Item", description = "Checklist Item APIs")
public class CheckListItemController {

    private final CheckListItemService checkListItemService;

    @Operation(summary = "Add checklist item", description = "Adds a new item to a checklist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Item added successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Item added successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"resolved\": false\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "CheckList or assigned user not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"CheckList not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PostMapping
    public ResponseEntity<ApiResponseDto<CheckListItemResponseDto>> addItem(@RequestBody CheckListItemRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(checkListItemService.addItem(dto), "Item added successfully"));
    }

    @Operation(summary = "Delete checklist item", description = "Permanently deletes a checklist item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Item deleted successfully\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "CheckListItem not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"CheckListItem not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteItem(@PathVariable UUID itemId) {
        checkListItemService.deleteItem(itemId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Item deleted successfully"));
    }

    @Operation(summary = "Assign checklist item", description = "Assigns a user to a checklist item")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item assigned successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Item assigned successfully\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"resolved\": false,\n    \"assignedUserId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa7\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "CheckListItem or user not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"CheckListItem not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    @PatchMapping("/item/{itemId}/assign/{userId}")
    public ResponseEntity<ApiResponseDto<CheckListItemResponseDto>> assignItem(@PathVariable UUID itemId,
                                                                               @PathVariable UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(checkListItemService.assignItem(itemId, userId),
                        "Item assigned successfully"));
    }
}
