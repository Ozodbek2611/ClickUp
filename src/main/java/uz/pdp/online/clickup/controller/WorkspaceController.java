package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Workspace created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Workspace created\",\n  \"data\": {\n    \"id\": 1,\n    \"name\": \"My Workspace\",\n    \"color\": \"#2ECC71\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"name: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Avatar attachment not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Avatar not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "Workspace with this name already exists for the user",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Workspace already exists with name: My Workspace\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Workspace updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Workspace updated\",\n  \"data\": {\n    \"id\": 1,\n    \"name\": \"Updated Workspace\",\n    \"color\": \"#E74C3C\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"name: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "403", description = "Not authorized to edit this workspace",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"You are not allowed to edit this workspace\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Workspace or avatar not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Workspace not found with ID: 1\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner changed successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Owner changed\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "403", description = "Not authorized to change owner",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Only the workspace owner can change ownership\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Workspace or user not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Workspace not found with ID: 1\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Workspace deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Not authorized",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"You are not allowed to delete this workspace\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Workspace not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Workspace not found with ID: 1\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<Void> delete(@PathVariable Long id, @CurrentUser User user) {
        workspaceService.delete(id, user);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/members")
    @Operation(summary = "Add/Edit/Remove member", description = "Manages workspace membership (add, update role, or remove)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operation successful",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Success\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"userId: User ID is required\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Workspace, user, or role not found (ADD/EDIT), or user is not a member (EDIT)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Workspace not found with ID: 1\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "User already exists in this workspace (on ADD)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"User already exists in this workspace\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> addOrEditOrRemoveMember(
            @PathVariable Long id,
            @Valid @RequestBody MemberRequestDto dto) {
        workspaceService.addOrEditOrRemove(id, dto);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Success"));
    }

    @PutMapping("/{id}/join")
    @Operation(summary = "Join workspace", description = "Join a workspace using an invitation code")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully joined workspace",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Successfully joined workspace\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "403", description = "Email does not match the invitation, or invalid verification code",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Invalid email verification code\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Invitation not found for this user, or email verification code not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Invitation not found for this user\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Members fetched successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Members fetched\",\n  \"data\": [\n    {\n      \"userId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n      \"fullName\": \"John Doe\",\n      \"email\": \"john@example.com\",\n      \"roleName\": \"ROLE_MEMBER\"\n    }\n  ],\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<List<MemberResponseDto>>> getMembers(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponseDto.ok(workspaceService.getMembers(id), "Members fetched"));
    }

    @GetMapping("/my")
    @Operation(summary = "Get my workspaces", description = "Returns all workspaces belonging to the authenticated user")
    @ApiResponse(responseCode = "200", description = "Workspaces fetched successfully",
            content = @Content(
                    mediaType = "application/json",
                    examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Workspaces fetched\",\n  \"data\": [\n    {\n      \"id\": 1,\n      \"name\": \"My Workspace\",\n      \"color\": \"#2ECC71\"\n    }\n  ],\n  \"errors\": null\n}")
            ))
    public ResponseEntity<ApiResponseDto<List<WorkspaceGetAllResponseDto>>> getMyWorkspaces(@CurrentUser User user) {
        return ResponseEntity.ok(ApiResponseDto.ok(workspaceService.getMyWorkspaces(user), "Workspaces fetched"));
    }

    @PutMapping("/roles/permissions")
    @Operation(summary = "Update role permissions", description = "Adds or removes permissions for a workspace role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Permission updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Permission updated\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Workspace role not found, or permission not found for this role (on REMOVE)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Workspace role not found with ID: 3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "Permission is already assigned to this role (on ADD)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Permission is already assigned to this role\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<Void>> updateRolePermission(@RequestBody WorkspaceRoleDto dto) {
        workspaceService.addOrRemovePermissionToRole(dto);
        return ResponseEntity.ok(ApiResponseDto.ok(null, "Permission updated"));
    }
}
