package com.lloyds.teacher_service.model;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "TEACHER")
public class Teacher {

    public Teacher(int id, String firstName, String lastName, Integer classroomId) {
        super();
        this.classroomId = classroomId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.id = id;
    }

    public Teacher() {
        super();
    }
    @Id
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Integer classroomId;
}
