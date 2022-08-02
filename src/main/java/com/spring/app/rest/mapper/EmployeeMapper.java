package com.spring.app.rest.mapper;


import com.spring.app.dao.model.Employee;
import com.spring.app.dao.model.Organization;
import com.spring.app.rest.dto.employee.EmployeeFillFormDto;
import com.spring.app.rest.dto.employee.EmployeeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    EmployeeDto toDto (Employee employee);

    @Mapping(target = "id", ignore = true)
    Employee toDomainObject (EmployeeFillFormDto dto, Organization organization);

}
