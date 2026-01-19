package com.github.vladkorobovdev.staff_manager.mapper;

import com.github.vladkorobovdev.staff_manager.dto.EmployeeFullDto;
import com.github.vladkorobovdev.staff_manager.dto.EmployeeShortDto;
import com.github.vladkorobovdev.staff_manager.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    @Mapping(target = "departmentName", source = "department.name")
    EmployeeShortDto toShortDto(Employee employee);

    @Mapping(target = "departmentName", source = "department.name")
    EmployeeFullDto toFullDto(Employee employee);
}
