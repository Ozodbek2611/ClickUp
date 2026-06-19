package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.checkListItemDto.CheckListItemRequestDto;
import uz.pdp.online.clickup.model.checkListItemDto.CheckListItemResponseDto;
import uz.pdp.online.clickup.service.CheckListItemService;

import java.util.UUID;

@RestController
@RequestMapping("/api/checklist-item")
@RequiredArgsConstructor
@Tag(name = "Checklist Item", description = "Checklist Item APIs")
public class CheckListItemController {

    private final CheckListItemService checkListItemService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<CheckListItemResponseDto>> addItem(@RequestBody CheckListItemRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(checkListItemService.addItem(dto), "Item added successfully"));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteItem(@PathVariable UUID itemId) {
        checkListItemService.deleteItem(itemId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Item deleted successfully"));
    }

    @PatchMapping("/item/{itemId}/assign/{userId}")
    public ResponseEntity<ApiResponseDto<CheckListItemResponseDto>> assignItem(@PathVariable UUID itemId,
                                                                            @PathVariable UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(checkListItemService.assignItem(itemId, userId),
                        "Item assigned successfully"));
    }
}
