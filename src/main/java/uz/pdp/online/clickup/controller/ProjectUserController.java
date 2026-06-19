package uz.pdp.online.clickup.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.online.clickup.entity.enums.TaskPermission;
import uz.pdp.online.clickup.common.ApiResponseDto;
import uz.pdp.online.clickup.model.projectUserDto.ProjectUserRequestDto;
import uz.pdp.online.clickup.model.projectUserDto.ProjectUserResponseDto;
import uz.pdp.online.clickup.service.ProjectUserService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/project-user")
@Tag(name = "Project User", description = "Project User APIs")
public class ProjectUserController {

    private final ProjectUserService projectUserService;

    @PostMapping
    public ResponseEntity<ApiResponseDto<ProjectUserResponseDto>> assign(@RequestBody @Valid ProjectUserRequestDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(projectUserService.assign(dto), "User assigned to project"));
    }

    @PutMapping("/{projectId}/{userId}")
    public ResponseEntity<ApiResponseDto<Void>> updatePermission(@PathVariable UUID projectId,
                                                              @PathVariable UUID userId,
                                                              @RequestParam TaskPermission taskPermission) {
        projectUserService.updatePermission(projectId, userId, taskPermission);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "Permission updated"));
    }

    @DeleteMapping("/{projectId}/{userId}")
    public ResponseEntity<ApiResponseDto<Void>> unassign(@PathVariable UUID projectId, @PathVariable UUID userId) {
        projectUserService.unassign(projectId, userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(null, "User unassigned from project"));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ApiResponseDto<List<ProjectUserResponseDto>>> getByProjectId(@PathVariable UUID projectId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponseDto.ok(projectUserService.getByProjectId(projectId), "Project users"));
    }
}