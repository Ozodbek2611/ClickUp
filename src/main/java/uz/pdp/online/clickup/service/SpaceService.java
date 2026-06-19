package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.domain.Attachment;
import uz.pdp.online.clickup.entity.domain.Space;
import uz.pdp.online.clickup.entity.domain.User;
import uz.pdp.online.clickup.entity.domain.Workspace;
import uz.pdp.online.clickup.entity.relation.SpaceUser;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.ForbiddenException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.SpaceMapper;
import uz.pdp.online.clickup.model.spaceDto.SpaceCreateRequestDto;
import uz.pdp.online.clickup.model.spaceDto.SpaceCreateResponseDto;
import uz.pdp.online.clickup.model.spaceDto.SpaceEditRequestDto;
import uz.pdp.online.clickup.model.spaceDto.SpaceEditResponseDto;
import uz.pdp.online.clickup.model.spaceDto.SpaceMemberRequestDto;
import uz.pdp.online.clickup.model.spaceDto.SpaceMemberResponseDto;
import uz.pdp.online.clickup.repository.AttachmentRepository;
import uz.pdp.online.clickup.repository.SpaceRepository;
import uz.pdp.online.clickup.repository.SpaceUserRepository;
import uz.pdp.online.clickup.repository.UserRepository;
import uz.pdp.online.clickup.repository.WorkspaceRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class SpaceService {

    private final SpaceRepository spaceRepository;
    private final WorkspaceRepository workspaceRepository;
    private final AttachmentRepository attachmentRepository;
    private final UserRepository userRepository;
    private final SpaceUserRepository spaceUserRepository;
    private final SpaceMapper spaceMapper;

    @Transactional
    public SpaceCreateResponseDto createSpace(SpaceCreateRequestDto dto, User user) {
        Workspace workspace = workspaceRepository.findById(dto.getWorkspaceId())
                .orElseThrow(() -> new NotFoundException("Workspace not found"));

        Attachment attachment = resolveAvatar(dto.getAvatarId());

        Space space = Space.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .avatar(attachment)
                .workspace(workspace)
                .owner(user)
                .accessType(dto.getAccessType())
                .build();

        spaceRepository.save(space);
        spaceUserRepository.save(new SpaceUser(space, user));

        log.info("Space created. ID: {}, by user: {}", space.getId(), user.getEmail());
        return spaceMapper.toCreateResponse(space, user);
    }

    @Transactional
    public SpaceEditResponseDto editSpace(UUID id, SpaceEditRequestDto dto, User user) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Space not found"));

        if (!user.getId().equals(space.getOwner().getId())) {
            log.warn("Unauthorized edit attempt. Space ID: {}, User: {}", id, user.getEmail());
            throw new ForbiddenException("You are not allowed to edit this space");
        }

        space.setName(dto.getName());
        space.setColor(dto.getColor());
        space.setAvatar(resolveAvatar(dto.getAvatarId()));

        log.info("Space updated. ID: {}, by user: {}", id, user.getEmail());
        return spaceMapper.toEditResponse(spaceRepository.save(space));
    }

    @Transactional
    public void deleteSpace(UUID id, User user) {
        Space space = spaceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Space not found"));

        if (!user.getId().equals(space.getOwner().getId())) {
            log.warn("Unauthorized delete attempt. Space ID: {}, User: {}", id, user.getEmail());
            throw new ForbiddenException("You are not allowed to delete this space");
        }

        spaceUserRepository.deleteBySpaceId(id);
        spaceRepository.delete(space);
        log.info("Space deleted. ID: {}, by user: {}", id, user.getEmail());
    }

    @Transactional
    public SpaceMemberResponseDto addOrRemoveMember(UUID spaceId, SpaceMemberRequestDto dto, User user) {
        Space space = spaceRepository.findById(spaceId)
                .orElseThrow(() -> new NotFoundException("Space not found"));

        if (!user.getId().equals(space.getOwner().getId())) {
            throw new ForbiddenException("You are not allowed to manage members of this space");
        }

        User member = userRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new NotFoundException("Member not found"));

        boolean exists = spaceUserRepository.existsBySpaceIdAndUserId(spaceId, dto.getMemberId());

        switch (dto.getTypeOfAction()) {
            case ADD -> {
                if (exists) throw new AlreadyExistsException("Member already exists in this space");
                spaceUserRepository.save(new SpaceUser(space, member));
                log.info("Member added. Space: {}, User: {}", spaceId, member.getEmail());
            }
            case REMOVE -> {
                if (!exists) throw new NotFoundException("Member not found in this space");
                spaceUserRepository.deleteBySpaceIdAndUserId(spaceId, member.getId());
                log.info("Member removed. Space: {}, User: {}", spaceId, member.getEmail());
            }
            default -> throw new ForbiddenException("Invalid action type");
        }

        return spaceMapper.toMemberResponse(member, dto.getTypeOfAction());
    }

    private Attachment resolveAvatar(UUID avatarId) {
        if (avatarId == null) return null;
        return attachmentRepository.findById(avatarId)
                .orElseThrow(() -> new NotFoundException("Avatar not found"));
    }
}
