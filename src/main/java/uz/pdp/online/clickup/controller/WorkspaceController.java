package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.annotations.CurrentUser;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.authDto.VerifyRequest;
import uz.pdp.online.clickup.model.workspaceDto.*;
import uz.pdp.online.clickup.service.WorkspaceService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspace")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<ApiResponse<WorkspaceCreateResponseDto>> create(
            @Valid @RequestBody WorkspaceCreateRequestDto workspaceCreateRequestDto,
            @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(workspaceService.create(workspaceCreateRequestDto, user), "Workspace created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkspaceEditResponseDto>> edit(@PathVariable Long id,
                                                                      @Valid @RequestBody WorkspaceEditRequestDto workspaceEditRequestDto,
                                                                      @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(workspaceService.edit(id, workspaceEditRequestDto, user), "Workspace updated"));
    }

    @PutMapping("/{id}/change-owner")
    public ResponseEntity<ApiResponse<Void>> changeOwner(@PathVariable Long id,
                                                         @RequestParam UUID newOwnerId,
                                                         @CurrentUser User user) {
        workspaceService.changeOwner(id, newOwnerId, user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Owner changed"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id,
                                                    @CurrentUser User user) {
        workspaceService.delete(id, user);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Workspace deleted"));
    }

    @PostMapping("/{id}/member")
    public ResponseEntity<ApiResponse<Void>> addOrEditOrRemove(@PathVariable Long id,
                                                               @Valid @RequestBody MemberDto memberDto) {
        workspaceService.addOrEditOrRemove(id, memberDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Success"));
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<ApiResponse<Void>> join(@PathVariable Long id,
                                                  @CurrentUser User user,
                                                  @RequestBody VerifyRequest verifyRequest) {
        workspaceService.joinToWorkspace(id, user, verifyRequest);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Successfully joined to workspace"));
    }

    @GetMapping("/get-members-and-guests/{id}")
    public ResponseEntity<ApiResponse<List<MemberDto>>> getMembersAndGuests(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(workspaceService.getMemberAndGuest(id), "Workspace members and guests"));
    }

    @GetMapping("/my-workspaces")
    public ResponseEntity<ApiResponse<List<WorkspaceGetAllResponseDto>>> getMyWorkspaces(@CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(workspaceService.getMyWorkspaces(user), "User workspaces fetched successfully"));
    }

    @PutMapping("/add-or-remove-permission")
    public ResponseEntity<ApiResponse<Void>> addOrRemovePermissionToRole(@RequestBody WorkspaceRoleDto workspaceRoleDto) {
        workspaceService.addOrRemovePermissionToRole(workspaceRoleDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Success"));
    }
}
