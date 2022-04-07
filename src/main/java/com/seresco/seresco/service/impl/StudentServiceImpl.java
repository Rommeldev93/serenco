package com.seresco.seresco.service.impl;

import com.seresco.seresco.entity.Student;
import com.seresco.seresco.entity.Teacher;
import com.seresco.seresco.repository.StudentRepository;
import com.seresco.seresco.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
  @Autowired
  private StudentRepository studentRepository;

  @Override
  public List<Student> findStudentAll() {
    return studentRepository.findAll();
  }

  @Override
  public List<Student> findStudentByTeacher(Teacher teacher) {
    return studentRepository.findByTeacher(teacher);
  }

  @Override
  public Student getStudentById(Long id) {
    return studentRepository.findById(id).orElse(null);
  }

  @Override
  public Student createStudent(Student student) {
    Student studentDB = studentRepository.findByNumberID(student.getNumberID());
    if (studentDB != null) {
      return studentDB;
    }
    studentDB = studentRepository.save(student);
    return studentDB;
  }

  @Override
  public Student updateStudent(Student student) {
    Student studentDB = getStudentById(student.getId());
    if (studentDB == null) {
      return null;
    }
    studentDB.setFirstName(student.getFirstName());
    studentDB.setLastName(student.getLastName());
    studentDB.setEmail(student.getEmail());
    studentDB.setPhone(student.getPhone());
    return studentRepository.save(studentDB);
  }

  @Override
  public void deleteStudent(Long id) {
    studentRepository.deleteById(id);
  }
}
