package com.github.vladkorobovdev.staff_manager.mapper;

import com.github.vladkorobovdev.staff_manager.dto.DepartmentDto;
import com.github.vladkorobovdev.staff_manager.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DepartmentMapper {
    DepartmentDto toDepartmentDto(Department department);
}
