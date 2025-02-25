package com.lloyds.teacher_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lloyds.teacher_service.dto.FindTeacherByIdsDto;
import com.lloyds.teacher_service.model.Teacher;
import com.lloyds.teacher_service.service.TeacherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.*;

@WebMvcTest(TeacherController.class)
public class TeacherControllerComponentTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TeacherService teacherService;

    @Autowired
    private ObjectMapper objectMapper;

    private Teacher teacher1;
    private Teacher teacher2;

    @BeforeEach
    public void setUp() {
        teacher1 = new Teacher(1, "John", "Doe", 1);
        teacher2 = new Teacher(2, "Sam", "Wood", 1);
    }

    @Test
    public void testRetrieveTeachers() throws Exception {
        // Given
        when(teacherService.getAllTeacher()).thenReturn(Arrays.asList(teacher1, teacher2));

        // When & Then
        mockMvc.perform(get("/teachers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].classroomId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Sam")))
                .andExpect(jsonPath("$[1].lastName", is("Wood")))
                .andExpect(jsonPath("$[0].classroomId", is(1)));
    }

    @Test
    public void testRetrieveTeacher() throws Exception {
        // Given
        when(teacherService.getTeacherById(anyInt())).thenReturn(teacher1);

        // When & Then
        mockMvc.perform(get("/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.classroomId", is(1)));
    }

    @Test
    public void testCreateTeacher() throws Exception {
        // Given
        when(teacherService.save(any(Teacher.class))).thenReturn(teacher1);

        // When & Then
        mockMvc.perform(post("/teacher")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teacher1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.classroomId", is(1)));
    }

    @Test
    public void testDeleteTeacher() throws Exception {
        // Given
        // No need to mock delete method as it returns void

        // When & Then
        mockMvc.perform(delete("/teacher/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(1)));
    }

    @Test
    public void testGetTeachersByIds() throws Exception {
        // Given
        FindTeacherByIdsDto dto = new FindTeacherByIdsDto();
        dto.setIds(Arrays.asList(1, 2));
        when(teacherService.getTeachersByIds(any(FindTeacherByIdsDto.class))).thenReturn(Arrays.asList(teacher1, teacher2));

        // When & Then
        mockMvc.perform(post("/teacher/find-by-ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].firstName", is("John")))
                .andExpect(jsonPath("$[0].lastName", is("Doe")))
                .andExpect(jsonPath("$[0].classroomId", is(1)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].firstName", is("Sam")))
                .andExpect(jsonPath("$[1].lastName", is("Wood")))
                .andExpect(jsonPath("$[0].classroomId", is(1)));
    }

    @Test
    public void testUpdateTeacher() throws Exception {
        // Given
        Teacher updatedTeacher = new Teacher(1, "John", "Doe", 1);
        when(teacherService.update(any(Teacher.class), anyInt())).thenReturn(updatedTeacher);

        // When & Then
        mockMvc.perform(put("/teacher/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTeacher)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")))
                .andExpect(jsonPath("$.classroomId", is(1)));
    }
}