package com.lloyds.teacher_service.repository;

import com.lloyds.teacher_service.model.Teacher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void testSaveAndFindById() {
        // Given
        Teacher teacher = new Teacher(1, "John", "Doe", 1);

        teacherRepository.save(teacher);

        // When
        Optional<Teacher> foundTeacher = teacherRepository.findById(1);

        // Then
        assertThat(foundTeacher).isPresent();
        assertThat(foundTeacher.get().getId()).isEqualTo(1);
        assertThat(foundTeacher.get().getFirstName()).isEqualTo("John");
        assertThat(foundTeacher.get().getLastName()).isEqualTo("Doe");
    }

    @Test
    public void testDeleteById() {
        // Given
        Teacher teacher = new Teacher(1, "John", "Doe", 1);

        teacherRepository.save(teacher);

        // When
        teacherRepository.deleteById(1);
        Optional<Teacher> foundTeacher = teacherRepository.findById(1);

        // Then
        assertThat(foundTeacher).isNotPresent();
    }
}