package com.spring.app.service.organization;

import com.spring.app.dao.model.Employee;
import com.spring.app.dao.model.Organization;
import com.spring.app.dao.repository.employee.EmployeeRepository;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.exception.EntityNotCreatedException;
import com.spring.app.dao.repository.organization.OrganizationRepository;
import com.spring.app.rest.dto.organization.OrganizationDto;
import com.spring.app.rest.dto.organization.OrganizationNameDto;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationServiceImpl implements OrganizationService{

    EmployeeRepository employeeRepository;
    OrganizationRepository organizationRepository;


    @Override
    public OrganizationDto update (OrganizationDto dto , String name) {

        Organization organizationFromDB  = organizationRepository.findByName(name)
                .orElseThrow(() -> new NotFoundEntityException("Organization not found in database"));

        if(!Objects.equals(organizationFromDB.getName(),dto.getName())){
            organizationFromDB.setName(dto.getName());
        }
        if(!Objects.equals(organizationFromDB.getDescription(),dto.getDescription())){
            organizationFromDB.setDescription(dto.getDescription());
        }
        if(!Objects.equals(organizationFromDB.getRating(),dto.getRating())){
            organizationFromDB.setRating(dto.getRating());
        }

        return OrganizationDto.toDto(organizationRepository.save(organizationFromDB));
    }

    @Override
    public OrganizationNameDto create (OrganizationDto dto) {

        Optional<Organization> organizationFromDB = organizationRepository.findAll()
                .stream()
                .filter(organization -> Objects.equals(organization.getName(),dto.getName()))
                .findFirst();

        if(organizationFromDB.isPresent()){
            throw new EntityNotCreatedException("This organization already exists");
        }

        return OrganizationNameDto.toDto(
                organizationRepository.save(OrganizationDto.toDomainObject(dto)));
    }

    @Override
    public List<OrganizationDto> getAll () {
        return organizationRepository
                .sortOrganizationsByRating()
                .stream()
                .map(OrganizationDto::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public String remove (String name) {

        Organization organizationFromDB  = organizationRepository.findByName(name)
                .orElseThrow(() -> new NotFoundEntityException("Organization not found in database"));

        List<Employee> remoteEmployees = employeeRepository.findEmployeesByOrganizationName(organizationFromDB.getName());
        remoteEmployees
                .forEach(employee -> employeeRepository.removeEmployeeById(employee.getId()));
        organizationRepository.removeOrganizationByName(organizationFromDB.getName());

        return "Organization successfully deleted";
    }
}
