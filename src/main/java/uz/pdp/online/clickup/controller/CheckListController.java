package uz.pdp.online.clickup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.checkListDto.CheckListRequestDto;
import uz.pdp.online.clickup.model.checkListDto.CheckListResponseDto;
import uz.pdp.online.clickup.service.CheckListService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
public class CheckListController {

    private final CheckListService checkListService;

    @PostMapping
    public ResponseEntity<ApiResponse<CheckListResponseDto>> create(@RequestBody CheckListRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(checkListService.create(dto), "CheckList created successfully"));
    }

    @PatchMapping("/{checkListId}")
    public ResponseEntity<ApiResponse<CheckListResponseDto>> edit(@PathVariable UUID checkListId,
                                                                  @RequestParam String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(checkListService.edit(checkListId, name),
                        "CheckList updated successfully"));
    }

    @DeleteMapping("/{checkListId}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID checkListId) {
        checkListService.delete(checkListId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "CheckList deleted successfully"));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponse<List<CheckListResponseDto>>> getByTask(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(checkListService.getByTaskId(taskId),
                        "CheckLists fetched successfully"));
    }
}
