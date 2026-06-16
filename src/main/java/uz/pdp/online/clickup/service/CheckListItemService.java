package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.pdp.online.clickup.entity.CheckList;
import uz.pdp.online.clickup.entity.CheckListItem;
import uz.pdp.online.clickup.entity.User;
import uz.pdp.online.clickup.exceptions.NotFoundException;
import uz.pdp.online.clickup.mapper.CheckListItemMapper;
import uz.pdp.online.clickup.model.checkListItemDto.CheckListItemRequestDto;
import uz.pdp.online.clickup.model.checkListItemDto.CheckListItemResponseDto;
import uz.pdp.online.clickup.repository.CheckListItemRepository;
import uz.pdp.online.clickup.repository.CheckListRepository;
import uz.pdp.online.clickup.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckListItemService {

    private final CheckListItemRepository checkListItemRepository;
    private final CheckListRepository checkListRepository;
    private final UserRepository userRepository;
    private final CheckListItemMapper checkListItemMapper;

    public CheckListItemResponseDto addItem(CheckListItemRequestDto dto) {
        log.debug("Request to add CheckListItem started for CheckList ID: {}", dto.getCheckListId());

        CheckList checkList = checkListRepository.findById(dto.getCheckListId())
                .orElseThrow(() -> new NotFoundException("CheckList not found with ID: " + dto.getCheckListId()));

        CheckListItem item = new CheckListItem();
        item.setCheckList(checkList);
        item.setResolved(dto.getResolved());

        if (dto.getAssignedUserId() != null) {
            User user = userRepository.findById(dto.getAssignedUserId())
                    .orElseThrow(() -> new NotFoundException("User not found with ID: " + dto.getAssignedUserId()));
            item.setAssignedUser(user);
        }

        CheckListItem savedItem = checkListItemRepository.save(item);
        log.info("CheckListItem successfully added. ID: {}, CheckList ID: {}", savedItem.getId(), dto.getCheckListId());

        return checkListItemMapper.toResponseDto(savedItem);
    }

    public void deleteItem(UUID itemId) {
        log.debug("Request to delete CheckListItem started. ID: {}", itemId);

        CheckListItem item = checkListItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("CheckListItem not found with ID: " + itemId));

        checkListItemRepository.delete(item);
        log.info("CheckListItem successfully deleted. ID: {}", itemId);
    }

    public CheckListItemResponseDto assignItem(UUID itemId, UUID userId) {
        log.debug("Request to assign CheckListItem started. Item ID: {}, User ID: {}", itemId, userId);

        CheckListItem item = checkListItemRepository.findById(itemId)
                .orElseThrow(() -> new NotFoundException("CheckListItem not found with ID: " + itemId));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));

        item.setAssignedUser(user);
        CheckListItem updatedItem = checkListItemRepository.save(item);

        log.info("CheckListItem successfully assigned. Item ID: {}, Assigned User ID: {}", itemId, userId);

        return checkListItemMapper.toResponseDto(updatedItem);
    }
}
