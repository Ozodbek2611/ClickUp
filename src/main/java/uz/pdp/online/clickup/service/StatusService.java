package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.domain.Category;
import uz.pdp.online.clickup.entity.domain.Project;
import uz.pdp.online.clickup.entity.domain.Space;
import uz.pdp.online.clickup.entity.domain.Status;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.StatusMapper;
import uz.pdp.online.clickup.model.statusDto.StatusRequestDto;
import uz.pdp.online.clickup.model.statusDto.StatusResponseDto;
import uz.pdp.online.clickup.repository.CategoryRepository;
import uz.pdp.online.clickup.repository.ProjectRepository;
import uz.pdp.online.clickup.repository.SpaceRepository;
import uz.pdp.online.clickup.repository.StatusRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatusService {

    private final StatusRepository statusRepository;
    private final SpaceRepository spaceRepository;
    private final ProjectRepository projectRepository;
    private final CategoryRepository categoryRepository;
    private final StatusMapper statusMapper;

    public StatusResponseDto create(StatusRequestDto dto) {
        log.debug("Request to create Status started. Name: {}", dto.getName());

        Status status = new Status();
        status.setName(dto.getName());
        status.setColor(dto.getColor());
        status.setType(dto.getType());

        if (dto.getSpaceId() != null) {
            Space space = spaceRepository.findById(dto.getSpaceId())
                    .orElseThrow(() -> new NotFoundException("Space not found with ID: " + dto.getSpaceId()));
            status.setSpace(space);
        }

        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new NotFoundException("Project not found with ID: " + dto.getProjectId()));
            status.setProject(project);
        }

        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new NotFoundException("Category not found with ID: " + dto.getCategoryId()));
            status.setCategory(category);
        }

        Status saved = statusRepository.save(status);
        log.info("Status successfully created. ID: {}, Name: {}", saved.getId(), saved.getName());
        return statusMapper.toResponseDto(saved);
    }

    public StatusResponseDto edit(UUID id, StatusRequestDto dto) {
        log.debug("Request to edit Status started. ID: {}", id);

        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + id));

        status.setName(dto.getName());
        status.setColor(dto.getColor());
        status.setType(dto.getType());

        Status updated = statusRepository.save(status);
        log.info("Status successfully updated. ID: {}", id);
        return statusMapper.toResponseDto(updated);
    }

    public void delete(UUID id) {
        log.debug("Request to delete Status started. ID: {}", id);

        Status status = statusRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Status not found with ID: " + id));

        statusRepository.delete(status);
        log.info("Status successfully deleted. ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<StatusResponseDto> getBySpaceId(UUID spaceId) {
        log.debug("Request to fetch Statuses by Space ID: {}", spaceId);
        List<Status> statuses = statusRepository.findAllBySpaceId(spaceId);
        return statusMapper.toResponseDtoList(statuses);
    }
}
