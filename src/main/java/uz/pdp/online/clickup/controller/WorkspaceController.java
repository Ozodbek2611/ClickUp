//package uz.pdp.online.clickup.controller;
//
//import jakarta.validation.Valid;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import uz.pdp.online.clickup.annotations.CurrentUser;
//import uz.pdp.online.clickup.entity.User;
//import uz.pdp.online.clickup.model.ApiResponse;
//import uz.pdp.online.clickup.model.MemberDto;
//import uz.pdp.online.clickup.model.WorkspaceRequestDto;
//import uz.pdp.online.clickup.model.WorkspaceResponseDto;
//import uz.pdp.online.clickup.service.WorkspaceService;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/workspace")
//public class WorkspaceController {
//    private final WorkspaceService workspaceService;
//
//    @PostMapping("/create")
//    public ResponseEntity<ApiResponse<WorkspaceResponseDto>> createWorkspace(@Valid
//                                                                             @RequestBody WorkspaceRequestDto workspaceDto,
//                                                                             @CurrentUser User user) {
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(ApiResponse.ok(workspaceService.create(workspaceDto, user), "Workspace created"));
//    }
//
//    @PostMapping("edit-or-remove/{id}")
//    public ResponseEntity<ApiResponse<Void>> editOrRemoveWorkspace(@RequestBody MemberDto memberDto,
//                                                                   @PathVariable Long id) {
//        workspaceService.addOrEditOrRemove(id, memberDto);
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(ApiResponse.ok(null, "Successfully updated workspace"));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<ApiResponse<Void>> deleteWorkspace(@PathVariable Long id,
//                                                             @CurrentUser User user) {
//        workspaceService.delete(id, user);
//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT)
//                .body(ApiResponse.ok(null, "Workspace deleted"));
//    }
//}
package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.annotations.CurrentUser;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.MemberDto;
import uz.pdp.online.clickup.model.WorkspaceRequestDto;
import uz.pdp.online.clickup.model.WorkspaceResponseDto;
import uz.pdp.online.clickup.service.WorkspaceService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/workspace")
public class WorkspaceController {

    private final WorkspaceService workspaceService;

    @PostMapping
    public ResponseEntity<ApiResponse<WorkspaceResponseDto>> create(@Valid @RequestBody WorkspaceRequestDto dto,
                                                                    @CurrentUser User user) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(workspaceService.create(dto, user), "Workspace created"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<WorkspaceResponseDto>> edit(@PathVariable Long id,
                                                                  @Valid @RequestBody WorkspaceRequestDto dto,
                                                                  @CurrentUser User user) {
        return ResponseEntity.ok(ApiResponse.ok(workspaceService.edit(id, dto, user), "Workspace updated"));
    }

    @PutMapping("/{id}/change-owner")
    public ResponseEntity<ApiResponse<Void>> changeOwner(@PathVariable Long id,
                                                         @RequestParam UUID newOwnerId,
                                                         @CurrentUser User user) {
        workspaceService.changeOwner(id, newOwnerId, user);
        return ResponseEntity.ok(ApiResponse.ok(null, "Owner changed"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id,
                                                    @CurrentUser User user) {
        workspaceService.delete(id, user);
        return ResponseEntity.ok(ApiResponse.ok(null, "Workspace deleted"));
    }

    @PostMapping("/{id}/member")
    public ResponseEntity<ApiResponse<Void>> addOrEditOrRemove(@PathVariable Long id,
                                                               @Valid @RequestBody MemberDto memberDto) {
        workspaceService.addOrEditOrRemove(id, memberDto);
        return ResponseEntity.ok(ApiResponse.ok(null, "Success"));
    }

    @PutMapping("/{id}/join")
    public ResponseEntity<ApiResponse<Void>> join(@PathVariable Long id,
                                                  @CurrentUser User user) {
        workspaceService.joinToWorkspace(id, user);
        return ResponseEntity.ok(ApiResponse.ok(null, "Successfully joined to workspace"));
    }
}