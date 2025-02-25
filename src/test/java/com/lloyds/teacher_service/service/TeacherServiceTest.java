package com.lloyds.teacher_service.service;

import com.lloyds.teacher_service.converter.TeacherConverter;
import com.lloyds.teacher_service.dto.FindTeacherByIdsDto;
import com.lloyds.teacher_service.exception.TeacherNotFoundException;
import com.lloyds.teacher_service.model.Teacher;
import com.lloyds.teacher_service.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherConverter teacherConverter;

    @InjectMocks
    private TeacherService teacherService;

    private Teacher teacher1;
    private Teacher teacher2;

    @BeforeEach
    public void setUp() {
        teacher1 = new Teacher(1, "John", "Doe", 1);
        teacher2 = new Teacher(2, "Sam", "Wood", 1);
    }

    @Test
    public void testGetAllTeacher() {
        // Given
        when(teacherRepository.findAll()).thenReturn(Arrays.asList(teacher1, teacher2));

        // When
        List<Teacher> teachers = teacherService.getAllTeacher();

        // Then
        assertThat(teachers).hasSize(2).containsExactlyInAnyOrder(teacher1, teacher2);
    }

    @Test
    public void testGetTeacherById() throws TeacherNotFoundException {
        // Given
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher1));

        // When
        Teacher teacher = teacherService.getTeacherById(1);

        // Then
        assertThat(teacher).isEqualTo(teacher1);
    }

    @Test
    public void testGetTeacherByIdThrowsException() {
        // Given
        when(teacherRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> teacherService.getTeacherById(1))
                .isInstanceOf(TeacherNotFoundException.class)
                .hasMessage("Teacher not found");
    }

    @Test
    public void testSaveTeacher() {
        // Given
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher1);

        // When
        Teacher savedTeacher = teacherService.save(teacher1);

        // Then
        assertThat(savedTeacher).isEqualTo(teacher1);
    }

    @Test
    public void testGetTeachersByIds() {
        // Given
        FindTeacherByIdsDto dto = new FindTeacherByIdsDto();
        dto.setIds(Arrays.asList(1, 2));
        when(teacherRepository.findAllById(dto.getIds())).thenReturn(Arrays.asList(teacher1, teacher2));

        // When
        List<Teacher> teachers = teacherService.getTeachersByIds(dto);

        // Then
        assertThat(teachers).hasSize(2).containsExactlyInAnyOrder(teacher1, teacher2);
    }

    @Test
    public void testDeleteTeacher() {
        // Given
        doNothing().when(teacherRepository).deleteById(1);

        // When
        teacherService.delete(1);

        // Then
        verify(teacherRepository, times(1)).deleteById(1);
    }

    @Test
    public void testUpdateTeacher() throws TeacherNotFoundException {
        // Given
        Teacher updatedTeacher = new Teacher(1, "John", "Doe", 1);
        when(teacherRepository.findById(1)).thenReturn(Optional.of(teacher1));
        when(teacherConverter.convert(any(Teacher.class), any(Teacher.class))).thenReturn(updatedTeacher);
        when(teacherRepository.save(any(Teacher.class))).thenReturn(updatedTeacher);

        // When
        Teacher result = teacherService.update(updatedTeacher, 1);

        // Then
        assertThat(result).isEqualTo(updatedTeacher);
    }

    @Test
    public void testUpdateTeacherThrowsException() {
        // Given
        Teacher updatedTeacher = new Teacher(1, "John", "Doe", 1);
        when(teacherRepository.findById(1)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> teacherService.update(updatedTeacher, 1))
                .isInstanceOf(TeacherNotFoundException.class)
                .hasMessage("Teacher not found");
    }
}