package com.seresco.seresco.service;

import com.seresco.seresco.entity.Student;
import com.seresco.seresco.entity.Teacher;

import java.util.List;

public interface StudentService {
  public List<Student> findStudentAll();

  public List<Student> findStudentByTeacher(Teacher teacher);

  public Student getStudentById(Long id);

  public Student createStudent(Student student);

  public Student updateStudent(Student student);

  public void deleteStudent(Long id);
}
