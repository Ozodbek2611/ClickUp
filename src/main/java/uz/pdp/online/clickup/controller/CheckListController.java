package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.checkListDto.CheckListRequestDto;
import uz.pdp.online.clickup.model.checkListDto.CheckListResponseDto;
import uz.pdp.online.clickup.service.CheckListService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/checklist")
@RequiredArgsConstructor
@Tag(name = "Checklist", description = "Checklist APIs")
public class CheckListController {

    private final CheckListService checkListService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<CheckListResponseDto>> create(@RequestBody CheckListRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(checkListService.create(dto), "CheckList created successfully"));
    }

    @PatchMapping("/{checkListId}")
    public ResponseEntity<ApiResponseDto<CheckListResponseDto>> edit(@PathVariable UUID checkListId,
                                                                  @RequestParam String name) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(checkListService.edit(checkListId, name),
                        "CheckList updated successfully"));
    }

    @DeleteMapping("/{checkListId}")
    public ResponseEntity<ApiResponseDto<Void>> delete(@PathVariable UUID checkListId) {
        checkListService.delete(checkListId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "CheckList deleted successfully"));
    }

    @GetMapping("/task/{taskId}")
    public ResponseEntity<ApiResponseDto<List<CheckListResponseDto>>> getByTask(@PathVariable UUID taskId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(checkListService.getByTaskId(taskId),
                        "CheckLists fetched successfully"));
    }
}
