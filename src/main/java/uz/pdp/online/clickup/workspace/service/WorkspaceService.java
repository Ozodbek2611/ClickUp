package uz.pdp.online.clickup.workspace.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.attachment.entity.Attachment;
import uz.pdp.online.clickup.attachment.repository.AttachmentRepository;
import uz.pdp.online.clickup.notification.service.EmailService;
import uz.pdp.online.clickup.user.entity.User;
import uz.pdp.online.clickup.workspace.dto.*;
import uz.pdp.online.clickup.workspace.entity.Workspace;
import uz.pdp.online.clickup.workspace.entity.WorkspaceRole;
import uz.pdp.online.clickup.workspace.entity.WorkspacePermission;
import uz.pdp.online.clickup.workspace.entity.WorkspaceUser;
import uz.pdp.online.clickup.common.enums.TypeOfAction;
import uz.pdp.online.clickup.common.enums.WorkspacePermissionName;
import uz.pdp.online.clickup.common.enums.WorkspaceRoleName;
import uz.pdp.online.clickup.common.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.common.exceptions.ForbiddenException;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.workspace.mapper.WorkspaceMapper;
import uz.pdp.online.clickup.audit.dto.VerifyRequest;
import uz.pdp.online.clickup.user.repository.UserRepository;
import uz.pdp.online.clickup.workspace.repository.WorkspacePermissionRepository;
import uz.pdp.online.clickup.workspace.repository.WorkspaceRepository;
import uz.pdp.online.clickup.workspace.repository.WorkspaceRoleRepository;
import uz.pdp.online.clickup.workspace.repository.WorkspaceUserRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkspaceService {

    private final WorkspaceRepository workspaceRepository;
    private final AttachmentRepository attachmentRepository;
    private final WorkspaceUserRepository workspaceUserRepository;
    private final WorkspaceRoleRepository workspaceRoleRepository;
    private final WorkspacePermissionRepository workspacePermissionRepository;
    private final UserRepository userRepository;
    private final WorkspaceMapper workspaceMapper;
    private final EmailService emailService;

    @Transactional
    public WorkspaceCreateResponseDto create(WorkspaceCreateRequestDto workspaceCreateRequestDto, User user) {
        log.debug("Workspace creation request started. Name: {}, User Email: {}", workspaceCreateRequestDto.getName(), user.getEmail());

        if (workspaceRepository.existsByOwnerIdAndName(user.getId(), workspaceCreateRequestDto.getName())) {
            throw new AlreadyExistsException("Workspace already exists with name: " + workspaceCreateRequestDto.getName());
        }

        Attachment attachment = getAvatar(workspaceCreateRequestDto.getAvatarId());

        Workspace workspace = workspaceRepository.save(
                new Workspace(workspaceCreateRequestDto.getName(), workspaceCreateRequestDto.getColor(), user, attachment));

        WorkspaceRole ownerRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_OWNER.name(), null));
        WorkspaceRole adminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_ADMIN.name(), null));
        WorkspaceRole memberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_MEMBER.name(), null));
        WorkspaceRole guestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, WorkspaceRoleName.ROLE_GUEST.name(), null));

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

        log.info("Successfully created new Workspace. ID: {}, Name: {}", workspace.getId(), workspace.getName());
        return workspaceMapper.toCreateResponse(workspace);
    }

    @Transactional
    public WorkspaceEditResponseDto edit(Long id, WorkspaceEditRequestDto workspaceEditRequestDto, User user) {
        log.debug("Workspace edit request started for ID: {}", id);

        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found with ID: " + id));

        if (!workspace.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("You are not allowed to edit this workspace");
        }

        Attachment attachment = getAvatar(workspaceEditRequestDto.getAvatarId());

        workspace.setName(workspaceEditRequestDto.getName());
        workspace.setColor(workspaceEditRequestDto.getColor());
        workspace.setAvatar(attachment);

        Workspace updated = workspaceRepository.save(workspace);
        log.info("Successfully updated Workspace. ID: {}", updated.getId());

        return workspaceMapper.toEditResponse(updated);
    }

    @Transactional
    public void changeOwner(Long id, UUID newOwnerId, User user) {
        log.debug("Change owner request started for Workspace ID: {}", id);

        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found with ID: " + id));

        if (!workspace.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("Only the workspace owner can change ownership");
        }

        User newOwner = userRepository.findById(newOwnerId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + newOwnerId));

        workspace.setOwner(newOwner);
        workspaceRepository.save(workspace);
        log.info("Workspace ownership changed successfully. Workspace ID: {}, New Owner ID: {}", id, newOwnerId);
    }

    @Transactional
    public void delete(Long id, User user) {
        log.debug("Workspace deletion request started for ID: {}", id);

        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Workspace not found with ID: " + id));

        if (!workspace.getOwner().getId().equals(user.getId())) {
            throw new ForbiddenException("You are not allowed to delete this workspace");
        }

        List<WorkspaceRole> roles = workspaceRoleRepository.findByWorkspaceId(id);
        workspacePermissionRepository.deleteAllByWorkspaceRoleIn(roles);
        workspaceUserRepository.deleteAllByWorkspaceId(id);
        workspaceRoleRepository.deleteAllByWorkspaceId(id);

        workspaceRepository.delete(workspace);
        log.info("Workspace deleted successfully. ID: {}", id);
    }

    @Transactional
    public void addOrEditOrRemove(Long workspaceId, MemberRequestDto memberDto) {
        log.debug("Member management request started. Workspace ID: {}, Action: {}", workspaceId, memberDto.getTypeOfAction());

        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new NotFoundException("Workspace not found with ID: " + workspaceId));

        User user = userRepository.findById(memberDto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + memberDto.getUserId()));

        switch (memberDto.getTypeOfAction()) {
            case ADD -> {
                if (workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, memberDto.getUserId()).isPresent()) {
                    throw new AlreadyExistsException("User already exists in this workspace");
                }

                WorkspaceRole role = workspaceRoleRepository.findById(memberDto.getRoleId())
                        .orElseThrow(() -> new NotFoundException("Role not found with ID: " + memberDto.getRoleId()));

                workspaceUserRepository.save(new WorkspaceUser(workspace, user, role));

                String inviteLink = "http://localhost:8080/api/workspace/" + workspaceId + "/join";
                emailService.sendWorkspaceInvitation(user.getEmail(), workspace.getName(), inviteLink);

                log.info("Member added successfully. Workspace ID: {}, User ID: {}", workspaceId, user.getId());
            }
            case EDIT -> {
                WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, memberDto.getUserId())
                        .orElseThrow(() -> new NotFoundException("User is not a member of this workspace"));

                WorkspaceRole role = workspaceRoleRepository.findById(memberDto.getRoleId())
                        .orElseThrow(() -> new NotFoundException("Role not found with ID: " + memberDto.getRoleId()));

                workspaceUser.setWorkspaceRole(role);
                workspaceUserRepository.save(workspaceUser);
                log.info("Member role updated successfully. Workspace ID: {}, User ID: {}", workspaceId, user.getId());
            }
            case REMOVE -> {
                workspaceUserRepository.deleteByWorkspaceIdAndUserId(workspaceId, memberDto.getUserId());
                log.info("Member removed successfully from workspace. Workspace ID: {}, User ID: {}", workspaceId, memberDto.getUserId());
            }
        }
    }

    @Transactional
    public void joinToWorkspace(Long workspaceId, User user, VerifyRequest verifyRequest) {
        log.debug("Join workspace request started. Workspace ID: {}, User ID: {}", workspaceId, user.getId());

        WorkspaceUser workspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, user.getId())
                .orElseThrow(() -> new NotFoundException("Invitation not found for this user"));

        if (!user.getEmail().equalsIgnoreCase(verifyRequest.getEmail())) {
            throw new ForbiddenException("Email verification failed. Emails do not match");
        }

        String emailCode = userRepository.findEmailCodeById(user.getId())
                .orElseThrow(() -> new NotFoundException("Email verification code not found"));

        if (emailCode.equals(verifyRequest.getEmailCode())) {
            workspaceUserRepository.save(workspaceUser);
            log.info("User successfully joined the workspace. Workspace ID: {}, User ID: {}", workspaceId, user.getId());
        } else {
            throw new ForbiddenException("Invalid email verification code");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDto> getMembers(Long id) {
        log.debug("Fetching members and guests for Workspace ID: {}", id);
        return workspaceUserRepository.findAllByWorkspaceId(id).stream()
                .map(this::toMemberResponseDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<WorkspaceGetAllResponseDto> getMyWorkspaces(User user) {
        log.debug("Fetching all workspaces for User ID: {}", user.getId());
        return workspaceUserRepository.findAllByUserId(user.getId()).stream()
                .map(WorkspaceUser::getWorkspace)
                .map(workspaceMapper::toGetAllResponse)
                .toList();
    }

    @Transactional
    public void addOrRemovePermissionToRole(WorkspaceRoleDto workspaceRoleDto) {
        log.debug("Permission role modification started. Role ID: {}, Action: {}", workspaceRoleDto.getId(), workspaceRoleDto.getTypeOfAction());

        WorkspaceRole workspaceRole = workspaceRoleRepository.findById(workspaceRoleDto.getId())
                .orElseThrow(() -> new NotFoundException("Workspace role not found with ID: " + workspaceRoleDto.getId()));

        Optional<WorkspacePermission> permission = workspacePermissionRepository
                .findByWorkspaceRoleIdAndPermission(workspaceRoleDto.getId(), workspaceRoleDto.getWorkspacePermissionName());

        if (workspaceRoleDto.getTypeOfAction().equals(TypeOfAction.ADD)) {
            if (permission.isPresent()) {
                throw new AlreadyExistsException("Permission is already assigned to this role");
            }
            workspacePermissionRepository.save(new WorkspacePermission(workspaceRole, workspaceRoleDto.getWorkspacePermissionName()));
            log.info("Permission assigned successfully. Role ID: {}, Permission: {}", workspaceRoleDto.getId(), workspaceRoleDto.getWorkspacePermissionName());
        } else if (workspaceRoleDto.getTypeOfAction().equals(TypeOfAction.REMOVE)) {
            if (permission.isEmpty()) {
                throw new NotFoundException("Permission not found for this role");
            }
            workspacePermissionRepository.delete(permission.get());
            log.info("Permission removed successfully. Role ID: {}, Permission: {}", workspaceRoleDto.getId(), workspaceRoleDto.getWorkspacePermissionName());
        }
    }

    private Attachment getAvatar(UUID avatarId) {
        if (avatarId != null) {
            return attachmentRepository.findById(avatarId)
                    .orElseThrow(() -> new NotFoundException("Avatar not found with ID: " + avatarId));
        }
        return null;
    }

    private MemberResponseDto toMemberResponseDto(WorkspaceUser workspaceUser) {
        User user = workspaceUser.getUser();
        return MemberResponseDto.builder()
                .userId(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .roleName(workspaceUser.getWorkspaceRole().getName())
                .lastActive(user.getLastActive())
                .build();
    }
}
