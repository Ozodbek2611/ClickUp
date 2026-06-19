package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.domain.Project;
import uz.pdp.online.clickup.entity.relation.ProjectUser;
import uz.pdp.online.clickup.entity.domain.User;
import uz.pdp.online.clickup.entity.enums.TaskPermission;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.ProjectUserMapper;
import uz.pdp.online.clickup.model.projectUserDto.ProjectUserRequestDto;
import uz.pdp.online.clickup.model.projectUserDto.ProjectUserResponseDto;
import uz.pdp.online.clickup.repository.ProjectRepository;
import uz.pdp.online.clickup.repository.ProjectUserRepository;
import uz.pdp.online.clickup.repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProjectUserService {

    private final ProjectUserRepository projectUserRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectUserMapper projectUserMapper;

    public ProjectUserResponseDto assign(ProjectUserRequestDto dto) {
        log.debug("Request to assign User to Project started. Project ID: {}, User ID: {}",
                dto.getProjectId(), dto.getUserId());

        Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new NotFoundException("Project not found with ID: " + dto.getProjectId()));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + dto.getUserId()));

        if (projectUserRepository.findByProjectIdAndUserId(dto.getProjectId(), dto.getUserId()).isPresent()) {
            throw new AlreadyExistsException("User is already assigned to Project! " + dto.getUserId());
        }

        ProjectUser projectUser = new ProjectUser();
        projectUser.setProject(project);
        projectUser.setUser(user);
        projectUser.setTaskPermission(dto.getTaskPermission());

        ProjectUser saved = projectUserRepository.save(projectUser);

        log.info("User successfully assigned to Project. ProjectUser ID: {}, Project ID: {}, User ID: {}",
                saved.getId(), dto.getProjectId(), dto.getUserId());

        return projectUserMapper.toResponseDto(saved);
    }

    @Transactional
    public void updatePermission(UUID projectId, UUID userId, TaskPermission taskPermission) {
        log.debug("Request to update permission started. Project ID: {}, User ID: {}", projectId, userId);

        ProjectUser projectUser = projectUserRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new NotFoundException("User is not assigned to Project! " + userId));

        projectUser.setTaskPermission(taskPermission);

        log.info("Permission successfully updated. Project ID: {}, User ID: {}", projectId, userId);
    }

    @Transactional
    public void unassign(UUID projectId, UUID userId) {
        log.debug("Request to unassign User from Project started. Project ID: {}, User ID: {}", projectId, userId);

        projectUserRepository.findByProjectIdAndUserId(projectId, userId)
                .orElseThrow(() -> new NotFoundException("User is not assigned to Project" + userId));

        projectUserRepository.deleteByProjectIdAndUserId(projectId, userId);

        log.info("User successfully unassigned from Project. Project ID: {}, User ID: {}", projectId, userId);
    }

    @Transactional(readOnly = true)
    public List<ProjectUserResponseDto> getByProjectId(UUID projectId) {
        log.debug("Request to fetch assigned users for Project ID: {}", projectId);

        List<ProjectUser> projectUsers = projectUserRepository.findAllByProjectId(projectId);

        return projectUserMapper.toResponseDtoList(projectUsers);
    }
}
