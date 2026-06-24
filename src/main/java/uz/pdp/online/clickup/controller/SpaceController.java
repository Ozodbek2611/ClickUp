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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Space created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Space created\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Marketing\",\n    \"color\": \"#2ECC71\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"name: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Workspace or avatar not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Workspace not found\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Space updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Space updated\",\n  \"data\": {\n    \"id\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"name\": \"Marketing Team\",\n    \"color\": \"#E74C3C\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"name: must not be blank\"]\n}")
                    )),
            @ApiResponse(responseCode = "403", description = "Not authorized to edit this space",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"You are not allowed to edit this space\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Space or avatar not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Space not found\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<ApiResponseDto<SpaceEditResponseDto>> editSpace(
            @PathVariable UUID spaceId,
            @RequestBody @Valid SpaceEditRequestDto dto,
            @CurrentUser User user) {
        return ResponseEntity.ok(ApiResponseDto.ok(spaceService.editSpace(spaceId, dto, user), "Space updated"));
    }

    @PostMapping("/{spaceId}/members")
    @Operation(summary = "Add or remove member", description = "Adds or removes a member from a space")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Member updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": true,\n  \"message\": \"Member updated\",\n  \"data\": {\n    \"userId\": \"3fa85f64-5717-4562-b3fc-2c963f66afa6\",\n    \"action\": \"ADD\"\n  },\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "400", description = "Validation Failed",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Validation Failed\",\n  \"data\": null,\n  \"errors\": [\"memberId: must not be null\"]\n}")
                    )),
            @ApiResponse(responseCode = "403", description = "Not authorized to manage members of this space, or invalid action type",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"You are not allowed to manage members of this space\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Space not found, member not found, or member not found in this space (on REMOVE)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Space not found\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "409", description = "Member already exists in this space (on ADD)",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Member already exists in this space\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Space deleted successfully"),
            @ApiResponse(responseCode = "403", description = "Not authorized",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"You are not allowed to delete this space\",\n  \"data\": null,\n  \"errors\": null\n}")
                    )),
            @ApiResponse(responseCode = "404", description = "Space not found",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value = "{\n  \"success\": false,\n  \"message\": \"Space not found\",\n  \"data\": null,\n  \"errors\": null\n}")
                    ))
    })
    public ResponseEntity<Void> deleteSpace(@PathVariable UUID spaceId, @CurrentUser User user) {
        spaceService.deleteSpace(spaceId, user);
        return ResponseEntity.noContent().build();
    }
}
