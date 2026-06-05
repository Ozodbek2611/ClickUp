//package uz.pdp.online.clickup.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import uz.pdp.online.clickup.entity.*;
//import uz.pdp.online.clickup.entity.enums.WorkspacePermissionName;
//import uz.pdp.online.clickup.entity.enums.WorkspaceRoleName;
//import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
//import uz.pdp.online.clickup.exceptions.ForbiddenException;
//import uz.pdp.online.clickup.exceptions.NotFoundException;
//import uz.pdp.online.clickup.model.MemberDto;
//import uz.pdp.online.clickup.model.WorkspaceRequestDto;
//import uz.pdp.online.clickup.model.WorkspaceResponseDto;
//import uz.pdp.online.clickup.repository.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class WorkspaceService {
//    private final WorkspaceRepository workspaceRepository;
//    private final AttachmentRepository attachmentRepository;
//    private final WorkspaceUserRepository workspaceUserRepository;
//    private final WorkspaceRoleRepository workspaceRoleRepository;
//    private final WorkspacePermissionRepository workspacePermissionRepository;
//    private final UserRepository userRepository;
//
//    @Transactional
//    public WorkspaceResponseDto create(WorkspaceRequestDto workspaceDto, User user) {
//        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceDto.getName())) {
//            throw new AlreadyExistsException("Workspace already exists: " + workspaceDto.getName());
//        }
//
//        Attachment attachment = null;
//        if (workspaceDto.getAvatarId() != null) {
//            attachment = attachmentRepository.findById(workspaceDto.getAvatarId())
//                    .orElseThrow(() -> new NotFoundException("Avatar not found"));
//        }
//
//        Workspace workspace = new Workspace(
//                workspaceDto.getName(),
//                workspaceDto.getColor(),
//                user,
//                attachment
//        );
//        Workspace saved = workspaceRepository.save(workspace);
//
//        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(
//                workspace,
//                WorkspaceRoleName.ROLE_OWNER.name(),
//                null
//        ));
//
//        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(
//                workspace,
//                WorkspaceRoleName.ROLE_ADMIN.name(),
//                null
//        ));
//
//        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(
//                workspace,
//                WorkspaceRoleName.ROLE_MEMBER.name(),
//                null
//        ));
//
//        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(
//                workspace,
//                WorkspaceRoleName.ROLE_GUEST.name(),
//                null
//        ));
//
//
//        List<WorkspacePermission> permissions = Arrays.stream(WorkspacePermissionName.values())
//                .flatMap(permissionName -> {
//                    List<WorkspacePermission> list = new ArrayList<>();
//                    list.add(new WorkspacePermission(ownerRole, permissionName));
//
//                    if (permissionName.getRoles().contains(WorkspaceRoleName.ROLE_ADMIN)) {
//                        list.add(new WorkspacePermission(adminRole, permissionName));
//                    }
//                    if (permissionName.getRoles().contains(WorkspaceRoleName.ROLE_MEMBER)) {
//                        list.add(new WorkspacePermission(memberRole, permissionName));
//                    }
//                    if (permissionName.getRoles().contains(WorkspaceRoleName.ROLE_GUEST)) {
//                        list.add(new WorkspacePermission(guestRole, permissionName));
//                    }
//                    return list.stream();
//                })
//                .toList();
//
//        workspacePermissionRepository.saveAll(permissions);
//
//        workspaceUserRepository.save(new WorkspaceUser(workspace, user, ownerRole));
//
//        return WorkspaceResponseDto.builder()
//                .id(saved.getId())
//                .name(saved.getName())
//                .color(saved.getColor())
//                .initialLetter(saved.getInitialLetter())
//                .ownerId(saved.getOwner().getId())
//                .avatarId(saved.getAvatar() != null ? saved.getAvatar().getId() : null)
//                .createdById(saved.getCreatedBy() != null ? saved.getCreatedBy().getId() : null)
//                .createdAt(saved.getCreatedAt())
//                .updatedAt(saved.getUpdatedAt())
//                .build();
//    }
//
//    public void delete(Long id, User user) {
//        Workspace workspace = workspaceRepository.findById(id)
//                .orElseThrow(() -> new NotFoundException("Workspace not found with id: " + id));
//
//        if (!workspace.getOwner().getId().equals(user.getId())) {
//            throw new ForbiddenException("You are not allowed to delete this workspace");
//        }
//
//        workspaceRepository.delete(workspace);
//    }
//
//    @Transactional
//    public void addOrEditOrRemove(Long workspaceId, MemberDto memberDto) {
//        Workspace workspace = workspaceRepository.findById(workspaceId)
//                .orElseThrow(() -> new NotFoundException("Workspace not found: " + workspaceId));
//
//        User user = userRepository.findById(memberDto.getId())
//                .orElseThrow(() -> new NotFoundException("User not found: " + memberDto.getId()));
//
//        switch (memberDto.getTypeOfAction()) {
//            case ADD -> {
//                WorkspaceRole role = workspaceRoleRepository.findById(memberDto.getRoleId())
//                        .orElseThrow(() -> new NotFoundException("Role not found: " + memberDto.getRoleId()));
//
//                if (workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, memberDto.getId()).isPresent()) {
//                    throw new AlreadyExistsException("User already exists in workspace");
//                }
//
//                workspaceUserRepository.save(new WorkspaceUser(workspace, user, role));
//            }
//            case EDIT -> {
//                WorkspaceUser workspaceUser = workspaceUserRepository
//                        .findByWorkspaceIdAndUserId(workspaceId, memberDto.getId())
//                        .orElseThrow(() -> new NotFoundException("User not in workspace"));
//
//                WorkspaceRole role = workspaceRoleRepository.findById(memberDto.getRoleId())
//                        .orElseThrow(() -> new NotFoundException("Role not found: " + memberDto.getRoleId()));
//
//                workspaceUser.setWorkspaceRole(role);
//                workspaceUserRepository.save(workspaceUser);
//            }
//            case REMOVE -> workspaceUserRepository.deleteByWorkspaceIdAndUserId(workspaceId, memberDto.getId());
//
//        }
//    }
//}

package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.*;
import uz.pdp.online.clickup.entity.enums.WorkspacePermissionName;
import uz.pdp.online.clickup.entity.enums.WorkspaceRoleName;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.ForbiddenException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.model.MemberDto;
import uz.pdp.online.clickup.model.WorkspaceRequestDto;
import uz.pdp.online.clickup.model.WorkspaceResponseDto;
import uz.pdp.online.clickup.repository.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final AttachmentRepository attachmentRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspacePermissionRepository workspacePermissionRepository;
    private final UserRepository userRepository;
    private final AuthService authService;

    @Transactional
    public WorkspaceResponseDto create(WorkspaceRequestDto dto, User user) {
        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), dto.getName())) {
            throw new AlreadyExistsException("Workspace already exists: " + dto.getName());
        }

        Attachment attachment = null;
        if (dto.getAvatarId() != null) {
            attachment = attachmentRepository.findById(dto.getAvatarId())
                    .orElseThrow(() -> new NotFoundException("Avatar not found"));
        }

        Workspace workspace = workspaceRepository.save(
                new Workspace(dto.getName(), dto.getColor(), user, attachment));

        WorkspaceRole ownerRole = workspaceRoleRepository.save(
                new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_OWNER.name(), null));
        WorkspaceRole adminRole = workspaceRoleRepository.save(
                new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepository.save(
                new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepository.save(
                new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));

        List<WorkspacePermission> permissions = Arrays.stream(WorkspacePermissionName.values())
                .flatMap(permissionName -> {
                    List<WorkspacePermission> list = new ArrayList<>();
                    list.add(new WorkspacePermission(ownerRole, permissionName));
                    if (permissionName.getRoles().contains(WorkspaceRoleName.ROLE_ADMIN))
                        list.add(new WorkspacePermission(adminRole, permissionName));
                    if (permissionName.getRoles().contains(WorkspaceRoleName.ROLE_MEMBER))
                        list.add(new WorkspacePermission(memberRole, permissionName));
                    if (permissionName.getRoles().contains(WorkspaceRoleName.ROLE_GUEST))
                        list.add(new WorkspacePermission(guestRole, permissionName));
                    return list.stream();
                })
                .toList();

        workspacePermissionRepository.saveAll(permissions);
        workspaceUserRepository.save(new WorkspaceUser(workspace, user, ownerRole));

        return toDto(workspace);
    }

    @Transactional
    public WorkspaceResponseDto edit(Long id, WorkspaceRequestDto dto, User user) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found: " + id));

        if (!workspace.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("You are not allowed to edit this workspace");
        }

        Attachment attachment = null;
        if (dto.getAvatarId() != null) {
            attachment = attachmentRepository.findById(dto.getAvatarId())
                    .orElseThrow(() -> new NotFoundException("Avatar not found"));
        }

        workspace.setName(dto.getName());
        workspace.setColor(dto.getColor());
        workspace.setAvatar(attachment);

        return toDto(workspaceRepository.save(workspace));
    }

    @Transactional
    public void changeOwner(Long id, UUID newOwnerId, User user) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found: " + id));

        if (!workspace.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("Only owner can change owner");
        }

        User newOwner = userRepository.findById(newOwnerId)
                .orElseThrow(() -> new NotFoundException("User not found: " + newOwnerId));

        workspace.setOwner(newOwner);
        workspaceRepository.save(workspace);
    }

    @Transactional
    public void delete(Long id, User user) {
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found: " + id));

        if (!workspace.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("You are not allowed to delete this workspace");
        }

        workspaceRepository.delete(workspace);
    }

    @Transactional
    public void addOrEditOrRemove(Long workspaceId, MemberDto memberDto) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new NotFoundException("Workspace not found: " + workspaceId));

        User user = userRepository.findById(memberDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found: " + memberDto.getUserId()));

        switch (memberDto.getTypeOfAction()) {
            case ADD -> {
                if (workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, memberDto.getUserId()).isPresent()) {
                    throw new AlreadyExistsException("User already exists in workspace");
                }

                WorkspaceRole role = workspaceRoleRepository.findById(memberDto.getRoleId())
                        .orElseThrow(() -> new NotFoundException("Role not found: " + memberDto.getRoleId()));

                workspaceUserRepository.save(new WorkspaceUser(workspace, user, role));

                String inviteLink = "http://localhost:8080/api/workspace/" + workspaceId + "/join";
                boolean emailSent = authService.sendEmail(
                        user.getEmail(),
                        "You have been invited. Click to join: " + inviteLink
                );
                if (!emailSent) {
                    throw new RuntimeException("Failed to send invitation email");
                }
            }
            case EDIT -> {
                WorkspaceUser workspaceUser = workspaceUserRepository
                        .findByWorkspaceIdAndUserId(workspaceId, memberDto.getUserId())
                        .orElseThrow(() -> new NotFoundException("User not in workspace"));

                WorkspaceRole role = workspaceRoleRepository.findById(memberDto.getRoleId())
                        .orElseThrow(() -> new NotFoundException("Role not found: " + memberDto.getRoleId()));

                workspaceUser.setWorkspaceRole(role);
                workspaceUserRepository.save(workspaceUser);
            }
            case REMOVE -> workspaceUserRepository.deleteByWorkspaceIdAndUserId(workspaceId, memberDto.getUserId());
        }
    }

    @Transactional
    public void joinToWorkspace(Long workspaceId, User user) {
        WorkspaceUser workspaceUser = workspaceUserRepository
                .findByWorkspaceIdAndUserId(workspaceId, user.getId())
                .orElseThrow(() -> new NotFoundException("Invitation not found"));

        workspaceUser.setDateJoined(new Timestamp(System.currentTimeMillis()));
        workspaceUserRepository.save(workspaceUser);
    }

    private WorkspaceResponseDto toDto(Workspace workspace) {
        return WorkspaceResponseDto.builder()
                .id(workspace.getId())
                .name(workspace.getName())
                .color(workspace.getColor())
                .initialLetter(workspace.getInitialLetter())
                .ownerId(workspace.getOwner().getId())
                .avatarId(workspace.getAvatar() != null ? workspace.getAvatar().getId() : null)
                .createdById(workspace.getCreatedBy() != null ? workspace.getCreatedBy().getId() : null)
                .createdAt(workspace.getCreatedAt())
                .updatedAt(workspace.getUpdatedAt())
                .build();
    }
}