package com.seresco.seresco.service;

import com.seresco.seresco.entity.Teacher;

import java.util.List;

public interface TeacherService {
  public List<Teacher> findTeacherAll();

  public Teacher getTeacherById(Long id);

  public Teacher createTeacher(Teacher teacher);

  public Teacher updateTeacher(Teacher teacher);

  public void deleteTeacher(Long id);
}
