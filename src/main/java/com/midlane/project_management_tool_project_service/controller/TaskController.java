//package com.midlane.project_management_tool_project_service.controller;
//
//import com.midlane.project_management_tool_project_service.dto.TaskRequestDto;
//import com.midlane.project_management_tool_project_service.service.TaskExecuter.TaskHandler;
//import com.midlane.project_management_tool_project_service.service.TaskExecuter.TaskRegistry;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@RestController
//@RequestMapping("api/projecttask")
//public class TaskController {
//
//    private final TaskRegistry taskRegistry;
//
//    public TaskController(TaskRegistry taskRegistry) {
//        this.taskRegistry = taskRegistry;
//    }
//
//    @PostMapping("/{projectId}/task/{taskName}/execute")
//    public ResponseEntity<?> executeTask(@PathVariable Long projectId,
//                                         @PathVariable String taskName,
//                                         @RequestBody TaskRequestDto dto) {
//        TaskHandler handler = taskRegistry.getHandler(taskName);
//        if (handler == null) {
//            return ResponseEntity.badRequest().body("Unknown task: " + taskName);
//        }
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("title", dto.getTitle());
//        data.put("featureId", dto.getFeatureId());
//        if (dto.getExtraData() != null) {
//            data.putAll(dto.getExtraData());
//        }
//
//        handler.execute(projectId, data);
//        return ResponseEntity.ok("Executed: " + taskName);
//    }
//}
