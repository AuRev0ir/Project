package com.spring.app.rest.mapper;


import com.spring.app.dao.model.Employee;
import com.spring.app.rest.dto.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {

    @Mapping(target = "middleName", source = "thirdName")
    EmployeeDto toDto (Employee employee);

    @Mapping(source = "middleName", target = "thirdName")
    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeDto dto);

    @Mapping(source = "middleName", target = "thirdName")
    @Mapping(target = "id", ignore = true)
    void updateFromDto(EmployeeDto employeeFillFormDto, @MappingTarget Employee employee);
}
