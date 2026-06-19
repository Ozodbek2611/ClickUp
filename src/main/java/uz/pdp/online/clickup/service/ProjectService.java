package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.domain.Project;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.ProjectMapper;
import uz.pdp.online.clickup.model.projectDto.ProjectRequestDto;
import uz.pdp.online.clickup.model.projectDto.ProjectResponseDto;
import uz.pdp.online.clickup.model.projectDto.ProjectUpdateDto;
import uz.pdp.online.clickup.repository.ProjectRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    @Transactional
    public ProjectResponseDto create(ProjectRequestDto dto) {
        Project project = Project.builder()
                .name(dto.getName())
                .color(dto.getColor())
                .build();

        Project savedProject = projectRepository.save(project);
        log.info("Project successfully created with ID: {}", savedProject.getId());
        return projectMapper.toResponseDto(savedProject);
    }

    @Transactional
    public ProjectResponseDto edit(UUID id, ProjectUpdateDto dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found with ID: " + id));

        project.setName(dto.getName());
        project.setColor(dto.getColor());

        log.info("Project successfully updated with ID: {}", id);
        return projectMapper.toResponseDto(project);
    }

    @Transactional
    public void delete(UUID id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Project not found with ID: " + id));
        projectRepository.delete(project);
        log.info("Project successfully deleted with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponseDto> getBySpaceId(UUID spaceId) {
        List<Project> projects = projectRepository.findAllBySpaceId(spaceId);
        return projectMapper.toResponseDtoList(projects);
    }
}
