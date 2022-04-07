package com.seresco.seresco.repository;

import com.seresco.seresco.entity.Student;
import com.seresco.seresco.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {
  public Student findByNumberID(String numberID);

  public List<Student> findByLastName(String lastName);

  public List<Student> findByTeacher(Teacher teacher);
}
