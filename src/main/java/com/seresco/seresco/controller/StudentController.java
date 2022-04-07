package com.seresco.seresco.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seresco.seresco.entity.Student;
import com.seresco.seresco.entity.Teacher;
import com.seresco.seresco.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/students")
public class StudentController {
  @Autowired
  private StudentService studentService;

  @GetMapping
  public ResponseEntity<List<Student>> listAllStudent(@RequestParam(name = "teacherId", required = false) Long teacherId) {
    List<Student> students = new ArrayList<>();
    if (teacherId == null) {
      students = studentService.findStudentAll();
      if (students.isEmpty()) {
        return ResponseEntity.notFound().build();
      }
    } else {
      Teacher teacher = new Teacher();
      teacher.setId(teacherId);
      students = studentService.findStudentByTeacher(teacher);
      if (students == null) {
        log.error("Docente con Id: {} no existe", teacherId);
        return ResponseEntity.notFound().build();
      }
    }
    return ResponseEntity.ok(students);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Student> getStudent(@PathVariable("id") Long id) {
    Student student = studentService.getStudentById(id);
    if (student == null) {
      log.error("Student with id {} not found", id);
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(student);
  }

  @PostMapping
  public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student, BindingResult result) {
    log.info("Creating Student: {}", student);
    if (result.hasErrors()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
    }
    Student studentDB = studentService.createStudent(student);
    return ResponseEntity.status(HttpStatus.CREATED).body(studentDB);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Student> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
    log.info("Updating Student with id {}", id);
    Student currentStudent = studentService.getStudentById(id);
    if (currentStudent == null) {
      log.error("Unable to update, Student with id {} not found.", id);
      return ResponseEntity.notFound().build();
    }
    student.setId(id);
    currentStudent = studentService.updateStudent(student);
    return ResponseEntity.ok(currentStudent);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
    Student student = studentService.getStudentById(id);
    if (student == null) {
      return ResponseEntity.notFound().build();
    }
    studentService.deleteStudent(id);
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
