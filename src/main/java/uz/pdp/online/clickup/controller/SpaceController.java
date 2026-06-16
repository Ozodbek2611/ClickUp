package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.annotations.CurrentUser;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.spaceDto.*;
import uz.pdp.online.clickup.service.SpaceService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space")
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping
    public ResponseEntity<ApiResponse<SpaceCreateResponseDto>> createSpace(@RequestBody @Valid SpaceCreateRequestDto spaceCreateRequestDto,
                                                                           @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(spaceService.createSpace(spaceCreateRequestDto, user), "Space created"));
    }

    @PutMapping("/edit/{spaceId}")
    public ResponseEntity<ApiResponse<SpaceEditResponseDto>> editSpace(@PathVariable UUID spaceId,
                                                                       @RequestBody @Valid SpaceEditRequestDto spaceEditRequestDto,
                                                                       @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(spaceService.editSpace(spaceId, spaceEditRequestDto, user), "Space edited"));
    }

    @PostMapping("/{spaceId}/member")
    public ResponseEntity<ApiResponse<SpaceMemberResponseDto>> addOrRemoveMember(@PathVariable UUID spaceId,
                                                                                 @RequestBody @Valid SpaceMemberRequestDto spaceMemberRequestDto,
                                                                                 @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(spaceService.addOrRemoveMember(spaceId, spaceMemberRequestDto, user),
                        "Space members edited successfully"));
    }

    @DeleteMapping("/delete/{spaceId}")
    public ResponseEntity<ApiResponse<Void>> deleteSpace(@PathVariable UUID spaceId,
                                                         @CurrentUser User user) {
        spaceService.deleteSpace(spaceId, user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Space deleted"));
    }
}
