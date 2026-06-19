package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.domain.Attachment;
import uz.pdp.online.clickup.entity.domain.User;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.UserMapper;
import uz.pdp.online.clickup.model.userDto.UserResponseDto;
import uz.pdp.online.clickup.model.userDto.UserUpdateDto;
import uz.pdp.online.clickup.repository.AttachmentRepository;

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
