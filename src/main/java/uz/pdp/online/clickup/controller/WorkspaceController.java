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
import uz.pdp.online.clickup.model.authDto.VerifyRequest;
import uz.pdp.online.clickup.model.workspaceDto.*;
import uz.pdp.online.clickup.service.WorkspaceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspace")
@Tag(name = "Workspace", description = "Workspace management APIs")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    @Operation(summary = "Create workspace", description = "Creates a new workspace for the authenticated user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Workspace created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    public ResponseEntity<ApiResponseDto<WorkspaceCreateResponseDto>> create(
            @Valid @RequestBody WorkspaceCreateRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(workspaceService.create(dto, user), "Workspace created"));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Edit workspace", description = "Updates workspace name or other details")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Workspace updated successfully"),
            @ApiResponse(responseCode = "404", description = "Workspace not found")
    })
    public ResponseEntity<ApiResponseDto<WorkspaceEditResponseDto>> edit(
            @PathVariable Long id,
            @Valid @RequestBody WorkspaceEditRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(workspaceService.edit(id, dto, user), "Workspace updated"));
    }

    @PutMapping("/{id}/owner")
    @Operation(summary = "Change workspace owner", description = "Transfers ownership of workspace to another user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Owner changed successfully"),
            @ApiResponse(responseCode = "403", description = "Not authorized to change owner"),
            @ApiResponse(responseCode = "404", description = "Workspace or user not found")
    })
    public ResponseEntity<ApiResponseDto<Void>> changeOwner(
            @PathVariable Long id,
            @RequestParam UUID newOwnerId,
            @CurrentUser User user) {
        workspaceService.changeOwner(id, newOwnerId, user);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Owner changed"));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete workspace", description = "Permanently deletes a workspace")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Workspace deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Not authorized"),
            @ApiResponse(responseCode = "404", description = "Workspace not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id, @CurrentUser User user) {
        workspaceService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/members")
    @Operation(summary = "Add/Edit/Remove member", description = "Manages workspace membership (add, update role, or remove)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operation successful"),
            @ApiResponse(responseCode = "404", description = "Workspace or user not found")
    })
    public ResponseEntity<ApiResponseDto<Void>> addOrEditOrRemoveMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDto dto) {
        workspaceService.addOrEditOrRemove(id, dto);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Success"));
    }

    @PutMapping("/{id}/join")
    @Operation(summary = "Join workspace", description = "Join a workspace using an invitation code")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Successfully joined workspace"),
            @ApiResponse(responseCode = "401", description = "Invalid invitation code"),
            @ApiResponse(responseCode = "404", description = "Workspace not found")
    })
    public ResponseEntity<ApiResponseDto<Void>> join(
            @PathVariable Long id,
            @CurrentUser User user,
            @RequestBody VerifyRequest verifyRequest) {
        workspaceService.joinToWorkspace(id, user, verifyRequest);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Successfully joined workspace"));
    }

    @GetMapping("/{id}/members")
    @Operation(summary = "Get workspace members", description = "Returns list of all members in a workspace")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Members fetched successfully"),
            @ApiResponse(responseCode = "404", description = "Workspace not found")
    })
    public ResponseEntity<ApiResponseDto<List<MemberResponseDto>>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseDto.ok(workspaceService.getMembers(id), "Members fetched"));
    }

    @GetMapping("/my")
    @Operation(summary = "Get my workspaces", description = "Returns all workspaces belonging to the authenticated user")
    @ApiResponse(responseCode = "200", description = "Workspaces fetched successfully")
    public ResponseEntity<ApiResponseDto<List<WorkspaceGetAllResponseDto>>> getMyWorkspaces(@CurrentUser User user) {
        return ResponseEntity.ok(ApiResponseDto.ok(workspaceService.getMyWorkspaces(user), "Workspaces fetched"));
    }

    @PutMapping("/roles/{roleId}/permissions")
    @Operation(summary = "Update role permissions", description = "Adds or removes permissions for a workspace role")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Permission updated successfully"),
            @ApiResponse(responseCode = "404", description = "Role not found")
    })
    public ResponseEntity<ApiResponseDto<Void>> updateRolePermission(
            @PathVariable Long roleId,
            @RequestBody WorkspaceRoleDto dto) {
        workspaceService.addOrRemovePermissionToRole(dto);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Permission updated"));
    }
}
