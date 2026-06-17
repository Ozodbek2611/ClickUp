package uz.pdp.online.clickup.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.entity.enums.TaskPermission;
import uz.pdp.online.clickup.model.ApiResponse;
import uz.pdp.online.clickup.model.projectUserDto.ProjectUserRequestDto;
import uz.pdp.online.clickup.model.projectUserDto.ProjectUserResponseDto;
import uz.pdp.online.clickup.service.ProjectUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project-user")
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectUserResponseDto>> assign(@RequestBody @Valid ProjectUserRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(projectUserService.assign(dto), "User assigned to project"));
    }

    @PutMapping("/{projectId}/{userId}")
    public ResponseEntity<ApiResponse<Void>> updatePermission(@PathVariable UUID projectId,
                                                              @PathVariable UUID userId,
                                                              @RequestParam TaskPermission taskPermission) {
        projectUserService.updatePermission(projectId, userId, taskPermission);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "Permission updated"));
    }

    @DeleteMapping("/{projectId}/{userId}")
    public ResponseEntity<ApiResponse<Void>> unassign(@PathVariable UUID projectId, @PathVariable UUID userId) {
        projectUserService.unassign(projectId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(null, "User unassigned from project"));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponse<List<ProjectUserResponseDto>>> getByProjectId(@PathVariable UUID projectId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.ok(projectUserService.getByProjectId(projectId), "Project users"));
    }
}