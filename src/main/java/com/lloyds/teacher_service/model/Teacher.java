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

    @Id
    private Integer id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private Integer classroomId;
}
