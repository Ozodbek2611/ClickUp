package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.Space;
import uz.pdp.online.clickup.entity.SpaceView;
import uz.pdp.online.clickup.entity.View;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.SpaceViewMapper;
import uz.pdp.online.clickup.model.spaceViewDto.SpaceViewRequestDto;
import uz.pdp.online.clickup.model.spaceViewDto.SpaceViewResponseDto;
import uz.pdp.online.clickup.repository.SpaceRepository;
import uz.pdp.online.clickup.repository.SpaceViewRepository;
import uz.pdp.online.clickup.repository.ViewRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceViewService {

    private final SpaceViewRepository spaceViewRepository;
    private final SpaceRepository spaceRepository;
    private final ViewRepository viewRepository;
    private final SpaceViewMapper spaceViewMapper;

    @Transactional
    public SpaceViewResponseDto create(SpaceViewRequestDto dto) {
        Space space = spaceRepository.findById(dto.getSpaceId())
                .orElseThrow(() -> new NotFoundException("Space not found with ID: " + dto.getSpaceId()));

        View view = viewRepository.findById(dto.getViewId())
                .orElseThrow(() -> new NotFoundException("View not found with ID: " + dto.getViewId()));

        if (spaceViewRepository.existsBySpaceIdAndViewId(dto.getSpaceId(), dto.getViewId())) {
            throw new AlreadyExistsException("View already added to this space");
        }

        SpaceView spaceView = new SpaceView();
        spaceView.setSpace(space);
        spaceView.setView(view);

        SpaceView saved = spaceViewRepository.save(spaceView);

        log.info("View successfully added to Space. Space ID: {}, View ID: {}", dto.getSpaceId(), dto.getViewId());

        return spaceViewMapper.toResponseDto(saved);
    }

    @Transactional
    public void delete(UUID spaceId, UUID viewId) {
        SpaceView spaceView = spaceViewRepository.findBySpaceIdAndViewId(spaceId, viewId)
                .orElseThrow(() -> new NotFoundException("Space View not found"));

        spaceViewRepository.delete(spaceView);

        log.info("View removed from Space. Space ID: {}, View ID: {}", spaceId, viewId);
    }

    @Transactional(readOnly = true)
    public List<SpaceViewResponseDto> getBySpaceId(UUID spaceId) {
        return spaceViewMapper.toResponseDtoList(spaceViewRepository.findAllBySpaceId(spaceId));
    }
}
