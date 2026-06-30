package uz.pdp.online.clickup.priority.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.icon.entity.Icon;
import uz.pdp.online.clickup.priority.entity.Priority;
import uz.pdp.online.clickup.common.exceptions.NotFoundException;
import uz.pdp.online.clickup.priority.mapper.PriorityMapper;
import uz.pdp.online.clickup.priority.dto.PriorityRequestDto;
import uz.pdp.online.clickup.priority.dto.PriorityResponseDto;
import uz.pdp.online.clickup.icon.repository.IconRepository;
import uz.pdp.online.clickup.priority.repository.PriorityRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PriorityService {

    private final PriorityRepository priorityRepository;
    private final IconRepository iconRepository;
    private final PriorityMapper priorityMapper;

    @Transactional
    public PriorityResponseDto create(PriorityRequestDto dto) {
        Icon icon = null;
        if (dto.getIconId() != null) {
            icon = iconRepository.findById(dto.getIconId())
                    .orElseThrow(() -> new NotFoundException("Icon not found with ID: " + dto.getIconId()));
        }

        Priority priority = Priority.builder()
                .name(dto.getName())
                .icon(icon)
                .build();

        Priority savedPriority = priorityRepository.save(priority);
        log.info("Priority successfully created with ID: {}", savedPriority.getId());
        return priorityMapper.toResponseDto(savedPriority);
    }

    @Transactional
    public PriorityResponseDto edit(UUID id, PriorityRequestDto dto) {
        Priority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Priority not found with ID: " + id));

        Icon icon = null;
        if (dto.getIconId() != null) {
            icon = iconRepository.findById(dto.getIconId())
                    .orElseThrow(() -> new NotFoundException("Icon not found with ID: " + dto.getIconId()));
        }

        priority.setName(dto.getName());
        priority.setIcon(icon);

        log.info("Priority successfully updated with ID: {}", id);
        return priorityMapper.toResponseDto(priority);
    }

    @Transactional
    public void delete(UUID id) {
        Priority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Priority not found with ID: " + id));
        priorityRepository.delete(priority);
        log.info("Priority successfully deleted with ID: {}", id);
    }

    @Transactional(readOnly = true)
    public List<PriorityResponseDto> getAll() {
        List<Priority> priorities = priorityRepository.findAll();
        return priorityMapper.toResponseDtoList(priorities);
    }
}
