package com.lloyds.teacher_service.exception;

public class TeacherNotFoundException extends Exception{

    private static final long serialVersionUID = 1L;

    public TeacherNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
