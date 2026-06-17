package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.ClickApps;
import uz.pdp.online.clickup.entity.Space;
import uz.pdp.online.clickup.entity.SpaceClickApps;
import uz.pdp.online.clickup.exceptions.AlreadyExistsException;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.SpaceClickAppsMapper;
import uz.pdp.online.clickup.model.spaceClickAppsDto.SpaceClickAppsRequestDto;
import uz.pdp.online.clickup.model.spaceClickAppsDto.SpaceClickAppsResponseDto;
import uz.pdp.online.clickup.repository.ClickAppsRepository;
import uz.pdp.online.clickup.repository.SpaceClickAppsRepository;
import uz.pdp.online.clickup.repository.SpaceRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpaceClickAppsService {

    private final SpaceClickAppsRepository spaceClickAppsRepository;
    private final ClickAppsRepository clickAppsRepository;
    private final SpaceRepository spaceRepository;
    private final SpaceClickAppsMapper spaceClickAppsMapper;

    @Transactional
    public SpaceClickAppsResponseDto create(SpaceClickAppsRequestDto dto) {
        Space space = spaceRepository.findById(dto.getSpaceId())
                .orElseThrow(() -> new NotFoundException("Space not found with ID: " + dto.getSpaceId()));

        ClickApps clickApps = clickAppsRepository.findById(dto.getClickAppsId())
                .orElseThrow(() -> new NotFoundException("ClickApps not found with ID: " + dto.getClickAppsId()));

        if (spaceClickAppsRepository.existsBySpaceIdAndClickAppsId(dto.getSpaceId(), dto.getClickAppsId())) {
            throw new AlreadyExistsException("ClickApps already added to this space");
        }

        SpaceClickApps entity = new SpaceClickApps();
        entity.setSpace(space);
        entity.setClickApps(clickApps);

        SpaceClickApps saved = spaceClickAppsRepository.save(entity);

        log.info("ClickApps successfully added to Space. Space ID: {}, ClickApps ID: {}", dto.getSpaceId(), dto.getClickAppsId());

        return spaceClickAppsMapper.toResponseDto(saved);
    }

    @Transactional
    public void delete(UUID spaceId, UUID clickAppsId) {
        SpaceClickApps entity = spaceClickAppsRepository.findBySpaceIdAndClickAppsId(spaceId, clickAppsId)
                .orElseThrow(() -> new NotFoundException("Space ClickApps not found"));

        spaceClickAppsRepository.delete(entity);

        log.info("ClickApps removed from Space. Space ID: {}, ClickApps ID: {}", spaceId, clickAppsId);
    }

    @Transactional(readOnly = true)
    public List<SpaceClickAppsResponseDto> getBySpaceId(UUID spaceId) {
        return spaceClickAppsMapper.toResponseDtoList(spaceClickAppsRepository.findAllBySpaceId(spaceId));
    }
}
