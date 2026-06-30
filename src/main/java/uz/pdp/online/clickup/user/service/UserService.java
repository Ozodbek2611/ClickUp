package uz.pdp.online.clickup.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.attachment.entity.Attachment;
import uz.pdp.online.clickup.user.entity.User;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.user.dto.UserResponseDto;
import uz.pdp.online.clickup.user.dto.UserUpdateDto;
import uz.pdp.online.clickup.attachment.repository.AttachmentRepository;
import uz.pdp.online.clickup.user.mapper.UserMapper;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final AttachmentRepository attachmentRepository;
    private final UserMapper userMapper;

    @Transactional(readOnly = true)
    public UserResponseDto getMe(User currentUser) {
        log.debug("Request to get current profile for user: {}", currentUser.getEmail());
        return userMapper.toResponseDto(currentUser);
    }

    @Transactional
    public UserResponseDto updateProfile(User currentUser, UserUpdateDto dto) {
        log.debug("Request to update profile for user: {}", currentUser.getEmail());

        currentUser.setFullName(dto.getFullName());
        currentUser.setColor(dto.getColor());

        log.info("Profile successfully updated for user: {}", currentUser.getEmail());
        return userMapper.toResponseDto(currentUser);
    }

    @Transactional
    public UserResponseDto updateAvatar(User currentUser, UUID avatarId) {
        log.debug("Request to update avatar for user: {}", currentUser.getEmail());

        Attachment attachment = attachmentRepository.findById(avatarId)
                .orElseThrow(() -> new NotFoundException("Avatar file not found with ID: " + avatarId));

        currentUser.setAvatar(attachment);

        log.info("Avatar successfully updated for user: {}", currentUser.getEmail());
        return userMapper.toResponseDto(currentUser);
    }
}
