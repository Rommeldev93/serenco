package com.seresco.seresco.repository;

import com.seresco.seresco.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeacherReporitory extends JpaRepository<Teacher, Long> {
  public Teacher findByNumberID(String numberID);

  public List<Teacher> findByLastName(String lastName);
}
