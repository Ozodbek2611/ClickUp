package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.*;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.ForbiddenException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.SpaceMapper;
import uz.pdp.online.clickup.model.spaceDto.*;
import uz.pdp.online.clickup.repository.*;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final WorkspaceRepository workspaceRepository;
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;
    private final SpaceUserRepository spaceUserRepository;
    private final SpaceMapper spaceMapper;

    public SpaceCreateResponseDto createSpace(SpaceCreateRequestDto spaceCreateRequestDto, User user) {
        Workspace workspace = workspaceRepository.findById(spaceCreateRequestDto.getWorkspaceId())
                .orElseThrow(() -> new NotFoundException("Workspace not found"));

        Attachment attachment = getAvatarId(spaceCreateRequestDto.getAvatarId());

        Space space = Space.builder()
                .name(spaceCreateRequestDto.getName())
                .color(spaceCreateRequestDto.getColor())
                .avatar(attachment)
                .workspace(workspace)
                .owner(user)
                .accessType(spaceCreateRequestDto.getAccessType() != null ? spaceCreateRequestDto.getAccessType() : null)
                .build();
        spaceRepository.save(space);

        SpaceUser spaceUser = new SpaceUser(space, user);
        spaceUserRepository.save(spaceUser);

        log.info("Space successfully created with id {} by user {}", space.getId(), user.getUsername());

        return spaceMapper.toCreateResponse(space, user);
    }

    public SpaceEditResponseDto editSpace(UUID id, SpaceEditRequestDto spaceEditRequestDto, User user) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Space not found"));

        User owner = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!owner.getId().equals(space.getOwner().getId())) {
            log.warn("Unauthorized attempt to edit space ID:{} with same owner {}", id, owner.getUsername());
            throw new ForbiddenException("You are not allowed to edit this space");
        }

        Attachment attachment = getAvatarId(spaceEditRequestDto.getAvatarId());

        space.setName(spaceEditRequestDto.getName());
        space.setColor(spaceEditRequestDto.getColor());
        space.setAvatar(attachment);

        log.info("Space successfully edited with id {} by user {}", space.getId(), user.getUsername());

        return spaceMapper.toEditResponse(spaceRepository.save(space));
    }

    @Transactional
    public void deleteSpace(UUID id, User user) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Space not found"));

        User owner = userRepository.findById(user.getId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!owner.getId().equals(space.getOwner().getId())) {
            log.warn("Unauthorized attempt to delete space ID:{} by user {}", id, user.getUsername());
            throw new ForbiddenException("You are not allowed to delete this space");
        }
        spaceUserRepository.deleteBySpaceId(id);
        spaceRepository.delete(space);
        log.info("Space successfully deleted with id {} by user {}", id, user.getUsername());
    }

    public SpaceMemberResponseDto addOrRemoveMember(UUID spaceId, SpaceMemberRequestDto spaceMemberRequestDto, User user) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new NotFoundException("Space not found"));

        if (!user.getId().equals(space.getOwner().getId())) {
            throw new ForbiddenException("You are not allowed to add this space");
        }

        User member = userRepository.findById(spaceMemberRequestDto.getMemberId())
                .orElseThrow(() -> new NotFoundException("Member not found"));

        boolean isMemberExist = spaceUserRepository.existsBySpaceIdAndUserId(spaceId,
                spaceMemberRequestDto.getMemberId());

        SpaceMemberResponseDto responseDto;

        switch (spaceMemberRequestDto.getTypeOfAction()) {
            case ADD -> {
                if (isMemberExist) {
                    log.warn("User with id {} already a member of space ID: {}", spaceMemberRequestDto.getMemberId(), spaceId);
                    throw new AlreadyExistsException("Member already exists");
                } else {
                    spaceUserRepository.save(new SpaceUser(space, member));
                    log.info("User ID: {} successfully added to space ID: {}", spaceMemberRequestDto.getMemberId(), spaceId);
                }
                responseDto = spaceMapper.toMemberResponse(member, spaceMemberRequestDto.getTypeOfAction());
            }
            case REMOVE -> {
                if (!isMemberExist) {
                    log.warn("User ID: {} not found in Space ID: {}", member.getId(), spaceId);
                    throw new NotFoundException("Member not found in this space");
                }
                spaceUserRepository.deleteBySpaceIdAndUserId(spaceId, member.getId());

                responseDto = spaceMapper.toMemberResponse(member, spaceMemberRequestDto.getTypeOfAction());
                log.info("User ID: {} successfully removed from Space ID: {}", member.getId(), spaceId);
            }
            default -> throw new ForbiddenException("Invalid action type");
        }
        return responseDto;
    }

    private Attachment getAvatarId(UUID avatarId) {
        if (avatarId != null) {
            return attachmentRepository.findById(avatarId)
                    .orElseThrow(() -> new NotFoundException("Avatar not found"));
        }
        return null;
    }
}
