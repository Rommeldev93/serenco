package com.seresco.seresco.service.impl;

import com.seresco.seresco.entity.Teacher;
import com.seresco.seresco.repository.TeacherReporitory;
import com.seresco.seresco.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
  @Autowired
  private TeacherReporitory teacherReporitory;

  @Override
  public List<Teacher> findTeacherAll() {
    return teacherReporitory.findAll();
  }

  @Override
  public Teacher getTeacherById(Long id) {
    return teacherReporitory.findById(id).orElse(null);
  }

  @Override
  public Teacher createTeacher(Teacher teacher) {
    Teacher teacherDB = teacherReporitory.findByNumberID(teacher.getNumberID());
    if (teacherDB != null) {
      return teacherDB;
    }
    teacherDB = teacherReporitory.save(teacher);
    return teacherDB;
  }

  @Override
  public Teacher updateTeacher(Teacher teacher) {
    Teacher teacherDB = getTeacherById(teacher.getId());
    if (teacherDB == null) {
      return null;
    }
    teacherDB.setNumberID(teacher.getNumberID());
    teacherDB.setName(teacher.getName());
    teacherDB.setLastName(teacher.getLastName());
    teacherDB.setEmail(teacher.getEmail());
    teacherDB.setPhone(teacher.getPhone());
    return teacherReporitory.save(teacherDB);
  }

  @Override
  public void deleteTeacher(Long id) {
    teacherReporitory.deleteById(id);
  }
}
