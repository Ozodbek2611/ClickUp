package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.CheckList;
import uz.pdp.online.clickup.entity.Task;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.CheckListMapper;
import uz.pdp.online.clickup.model.checkListDto.CheckListRequestDto;
import uz.pdp.online.clickup.model.checkListDto.CheckListResponseDto;
import uz.pdp.online.clickup.repository.CheckListItemRepository;
import uz.pdp.online.clickup.repository.CheckListRepository;
import uz.pdp.online.clickup.repository.TaskRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckListService {

    private final CheckListRepository checkListRepository;
    private final CheckListItemRepository checkListItemRepository;
    private final TaskRepository taskRepository;
    private final CheckListMapper checkListMapper;

    public CheckListResponseDto create(CheckListRequestDto dto) {
        log.debug("Request to create CheckList started for Task ID: {}", dto.getTaskId());

        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new NotFoundException("Task not found with ID: " + dto.getTaskId()));

        CheckList checkList = new CheckList();
        checkList.setName(dto.getName());
        checkList.setTask(task);

        CheckList savedCheckList = checkListRepository.save(checkList);
        log.info("CheckList successfully created. ID: {}, Task ID: {}", savedCheckList.getId(), dto.getTaskId());

        return checkListMapper.toResponseDto(savedCheckList);
    }

    public CheckListResponseDto edit(UUID checkListId, String newName) {
        log.debug("Request to edit CheckList started. ID: {}", checkListId);

        CheckList checkList = checkListRepository.findById(checkListId)
                .orElseThrow(() -> new NotFoundException("CheckList not found with ID: " + checkListId));

        checkList.setName(newName);
        CheckList updatedCheckList = checkListRepository.save(checkList);

        log.info("CheckList name successfully updated. ID: {}", checkListId);
        return checkListMapper.toResponseDto(updatedCheckList);
    }

    @Transactional
    public void delete(UUID checkListId) {
        log.debug("Request to delete CheckList started. ID: {}", checkListId);

        CheckList checkList = checkListRepository.findById(checkListId)
                .orElseThrow(() -> new NotFoundException("CheckList not found with ID: " + checkListId));

        checkListItemRepository.deleteAll(checkListItemRepository.findAllByCheckListId(checkListId));

        checkListRepository.delete(checkList);
        log.info("CheckList and its items successfully deleted. ID: {}", checkListId);
    }

    @Transactional(readOnly = true)
    public List<CheckListResponseDto> getByTaskId(UUID taskId) {
        log.debug("Request to fetch CheckLists for Task ID: {}", taskId);
        List<CheckList> checkLists = checkListRepository.findAllByTaskId(taskId);
        return checkListMapper.toResponseDtoList(checkLists);
    }
}
