package uz.pdp.online.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.checkListItemDto.CheckListItemRequestDto;
import uz.pdp.online.clickup.model.checkListItemDto.CheckListItemResponseDto;
import uz.pdp.online.clickup.service.CheckListItemService;

import java.util.UUID;

@RestController
@RequestMapping("/api/checklist-item")
@RequiredArgsConstructor
public class CheckListItemController {

    private final CheckListItemService checkListItemService;

    @PostMapping
    public ResponseEntity<ApiResponse<CheckListItemResponseDto>> addItem(@RequestBody CheckListItemRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(checkListItemService.addItem(dto), "Item added successfully"));
    }

    @DeleteMapping("/item/{itemId}")
    public ResponseEntity<ApiResponse<Void>> deleteItem(@PathVariable UUID itemId) {
        checkListItemService.deleteItem(itemId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Item deleted successfully"));
    }

    @PatchMapping("/item/{itemId}/assign/{userId}")
    public ResponseEntity<ApiResponse<CheckListItemResponseDto>> assignItem(@PathVariable UUID itemId,
                                                                            @PathVariable UUID userId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(checkListItemService.assignItem(itemId, userId),
                        "Item assigned successfully"));
    }
}
