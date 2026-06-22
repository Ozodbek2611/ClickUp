package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "Space", description = "Space management APIs")
public class SpaceController {

    private final SpaceService spaceService;

    @PostMapping
    @Operation(summary = "Create space", description = "Creates a new space inside a workspace")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Space created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<ApiResponseDto<SpaceCreateResponseDto>> createSpace(
            @RequestBody @Valid SpaceCreateRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(spaceService.createSpace(dto, user), "Space created"));
    }

    @PutMapping("/{spaceId}")
    @Operation(summary = "Edit space", description = "Updates space name, icon or other settings")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Space updated successfully"),
            @ApiResponse(responseCode = "404", description = "Space not found")
    })
    public ResponseEntity<ApiResponseDto<SpaceEditResponseDto>> editSpace(
            @PathVariable UUID spaceId,
            @RequestBody @Valid SpaceEditRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity.ok(ApiResponseDto.ok(spaceService.editSpace(spaceId, dto, user), "Space updated"));
    }

    @PostMapping("/{spaceId}/members")
    @Operation(summary = "Add or remove member", description = "Adds or removes a member from a space")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Member updated successfully"),
            @ApiResponse(responseCode = "404", description = "Space or user not found")
    })
    public ResponseEntity<ApiResponseDto<SpaceMemberResponseDto>> manageMember(
            @PathVariable UUID spaceId,
            @RequestBody @Valid SpaceMemberRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity.ok(
                ApiResponseDto.ok(spaceService.addOrRemoveMember(spaceId, dto, user), "Member updated"));
    }

    @DeleteMapping("/{spaceId}")
    @Operation(summary = "Delete space", description = "Permanently deletes a space and all its contents")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Space deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Space not found")
    })
    public ResponseEntity<Void> deleteSpace(@PathVariable UUID spaceId, @CurrentUser User user) {
        spaceService.deleteSpace(spaceId, user);
        return ResponseEntity.noContent().build();
    }
}
