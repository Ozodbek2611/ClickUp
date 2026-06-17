package uz.pdp.online.clickup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.pdp.online.clickup.entity.Task;
import uz.pdp.online.clickup.entity.TaskHistory;
import uz.pdp.online.clickup.entity.enums.TaskHistoryType;
import uz.pdp.online.clickup.repository.TaskHistoryRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskHistoryService {

    private final TaskHistoryRepository taskHistoryRepository;

    @Transactional
    public void logChange(Task task, String fieldName, String before, String after, TaskHistoryType type) {
        TaskHistory history = new TaskHistory();
        history.setTask(task);
        history.setChangeFieldName(fieldName);
        history.setBeforeValue(before);
        history.setAfterValue(after);
        history.setHistoryType(type);

        taskHistoryRepository.save(history);
    }

    public List<TaskHistory> getHistoryByTaskId(UUID taskId) {
        return taskHistoryRepository.findAllByTaskIdOrderByCreatedAtDesc(taskId);
    }
}
