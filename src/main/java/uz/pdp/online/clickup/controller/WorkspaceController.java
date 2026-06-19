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
import uz.pdp.online.clickup.model.authDto.VerifyRequest;
import uz.pdp.online.clickup.model.workspaceDto.*;
import uz.pdp.online.clickup.service.WorkspaceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspace")
@Tag(name = "Workspace", description = "Workspace APIs")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<WorkspaceCreateResponseDto>> create(
            @Valid @RequestBody WorkspaceCreateRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(workspaceService.create(dto, user), "Workspace created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<WorkspaceEditResponseDto>> edit(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceEditRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(workspaceService.edit(id, dto, user), "Workspace updated"));
    }

    @PutMapping("/{id}/owner")
    public ResponseEntity<ApiResponseDto<Void>> changeOwner(
            @PathVariable Long id,
            @RequestParam UUID newOwnerId,
            @CurrentUser User user) {
        workspaceService.changeOwner(id, newOwnerId, user);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Owner changed"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @CurrentUser User user) {
        workspaceService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<ApiResponseDto<Void>> addOrEditOrRemoveMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDto dto) {
        workspaceService.addOrEditOrRemove(id, dto);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Success"));
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<ApiResponseDto<Void>> join(
            @PathVariable Long id,
            @CurrentUser User user,
            @RequestBody VerifyRequest verifyRequest) {
        workspaceService.joinToWorkspace(id, user, verifyRequest);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Successfully joined workspace"));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<ApiResponseDto<List<MemberResponseDto>>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseDto.ok(workspaceService.getMembers(id), "Members fetched"));
    }

    @GetMapping("/my")
    public ResponseEntity<ApiResponseDto<List<WorkspaceGetAllResponseDto>>> getMyWorkspaces(@CurrentUser User user) {
        return ResponseEntity.ok(ApiResponseDto.ok(workspaceService.getMyWorkspaces(user), "Workspaces fetched"));
    }

    @PutMapping("/roles/{roleId}/permissions")
    public ResponseEntity<ApiResponseDto<Void>> updateRolePermission(
            @PathVariable Long roleId,
            @RequestBody WorkspaceRoleDto dto) {
        workspaceService.addOrRemovePermissionToRole(dto);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Permission updated"));
    }
}
