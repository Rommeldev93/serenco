package com.seresco.seresco;

import com.seresco.seresco.entity.Student;
import com.seresco.seresco.entity.Teacher;
import com.seresco.seresco.repository.StudentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
public class StudentRepositoryMockTest {

  @Autowired
  private StudentRepository studentRepository;

@Test
  public void whenFindByTeacher_thenReturnListStudent(){
    Student student4=Student.builder()
            .numberID("32323232")
            .firstName("Rommel")
            .lastName("Melcam")
            .email("melcam@gmail.com")
            .teacher(Teacher.builder().id(1L).build())
            .phone("").build();
    studentRepository.save(student4);

    List<Student> listStudent = studentRepository.findByTeacher(student4.getTeacher());

    Assertions.assertEquals(listStudent.size(),3);

  }
}
