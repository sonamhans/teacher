package com.lloyds.teacher_service.converter;


import com.lloyds.teacher_service.model.Teacher;
import org.springframework.stereotype.Component;

@Component
public class TeacherConverter {

    public Teacher convert(Teacher newTeacher, Teacher teacher) {
        if (newTeacher.getFirstName() != null) {
            teacher.setFirstName(newTeacher.getFirstName());
        }
        if (newTeacher.getLastName() != null) {
            teacher.setLastName(newTeacher.getLastName());
        }
        if (newTeacher.getClassroomId() != null) {
            teacher.setClassroomId(newTeacher.getClassroomId());
        }

        return teacher;
    }

}
