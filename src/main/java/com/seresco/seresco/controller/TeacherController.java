package com.seresco.seresco.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seresco.seresco.entity.Teacher;
import com.seresco.seresco.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {
  @Autowired
  private TeacherService teacherService;

  @GetMapping
  public ResponseEntity<List<Teacher>> listAllTeachers() {
    List<Teacher> teachers = teacherService.findTeacherAll();
    return ResponseEntity.ok(teachers);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Teacher> getTeacher(@PathVariable("id") Long id) {
    Teacher teacher = teacherService.getTeacherById(id);
    if (teacher == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(teacher);
  }

  @PostMapping
  public ResponseEntity<Teacher> createTeacher(@Valid @RequestBody Teacher teacher, BindingResult result) {
    if (result.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
    }
    Teacher teacherBD = teacherService.createTeacher(teacher);
    return ResponseEntity.status(HttpStatus.CREATED).body(teacherBD);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Teacher> updateTeacher(@PathVariable("id") Long id, @RequestBody Teacher teacher) {
    Teacher currentTeacher = teacherService.getTeacherById(id);
    if (currentTeacher == null) {
      return ResponseEntity.noContent().build();
    }
    teacher.setId(id);
    currentTeacher = teacherService.updateTeacher(teacher);
    return ResponseEntity.ok(currentTeacher);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteTeacher(@PathVariable("id") Long id) {
    Teacher teacher = teacherService.getTeacherById(id);
    if (teacher == null) {
      return ResponseEntity.notFound().build();
    }
    teacherService.deleteTeacher(id);
    return ResponseEntity.ok(null);
  }


  /****MESSAGE VALIDATION****/
  private String formatMessage(BindingResult result) {
    List<Map<String, String>> errrs = result.getFieldErrors().stream()
            .map(err -> {
              Map<String, String> error = new HashMap<>();
              error.put(err.getField(), err.getDefaultMessage());
              return error;
            }).collect(Collectors.toList());
    ErrorMessage errorMessage = ErrorMessage.builder()
            .code("01")
            .message(errrs).build();
    ObjectMapper mapper = new ObjectMapper();
    String jsonString = "";
    try {
      jsonString = mapper.writeValueAsString(errorMessage);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return jsonString;
  }
}
