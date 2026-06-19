package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.annotations.CurrentUser;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.entity.domain.User;
import uz.pdp.online.clickup.model.spaceDto.*;
import uz.pdp.online.clickup.service.SpaceService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/space")
@Tag(name = "Space", description = "Space APIs")
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<SpaceCreateResponseDto>> createSpace(
            @RequestBody @Valid SpaceCreateRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(spaceService.createSpace(dto, user), "Space created"));
    }

    @PutMapping("/{spaceId}")
    public ResponseEntity<ApiResponseDto<SpaceEditResponseDto>> editSpace(
            @PathVariable UUID spaceId,
            @RequestBody @Valid SpaceEditRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity.ok(ApiResponseDto.ok(spaceService.editSpace(spaceId, dto, user), "Space updated"));
    }

    @PostMapping("/{spaceId}/members")
    public ResponseEntity<ApiResponseDto<SpaceMemberResponseDto>> manageMember(
            @PathVariable UUID spaceId,
            @RequestBody @Valid SpaceMemberRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity.ok(
                ApiResponseDto.ok(spaceService.addOrRemoveMember(spaceId, dto, user), "Member updated"));
    }

    @DeleteMapping("/{spaceId}")
    public ResponseEntity<Void> deleteSpace(@PathVariable UUID spaceId, @CurrentUser User user) {
        spaceService.deleteSpace(spaceId, user);
        return ResponseEntity.noContent().build();
    }
}
