package com.lloyds.teacher_service.controller;


import com.lloyds.teacher_service.dto.FindTeacherByIdsDto;
import com.lloyds.teacher_service.exception.TeacherNotFoundException;
import com.lloyds.teacher_service.model.Teacher;
import com.lloyds.teacher_service.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping(value = "/teachers")
    public List<Teacher> retrieveTeachers() {
        return teacherService.getAllTeacher();
    }

    @PostMapping(value = "/teacher")
    public Teacher createTeachers(@RequestBody Teacher teacher) {
        return teacherService.save(teacher);
    }

    @GetMapping(value = "/teacher/{id}")
    public Teacher retrieveTeacher(@PathVariable("id") Integer id) throws TeacherNotFoundException {
        return teacherService.getTeacherById(id);
    }

    @DeleteMapping(value = "/teacher/{id}")
    public Integer deleteTeacher(@PathVariable("id") Integer id){
        teacherService.delete(id);
        return id;
    }

    @PostMapping(value = "/teacher/find-by-ids")
    public List<Teacher> getTeachersByIds(@RequestBody FindTeacherByIdsDto ids) {
        return teacherService.getTeachersByIds(ids);
    }

    @PutMapping(value = "/teacher/{id}")
    public Teacher updateStudent(@RequestBody Teacher teacher, @PathVariable("id") Integer id) throws TeacherNotFoundException {
        return teacherService.update(teacher, id);
    }
}
