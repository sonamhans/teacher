package com.lloyds.teacher_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FindTeacherByIdsDto {
    List<Integer> ids;
}
