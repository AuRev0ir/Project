package com.spring.app.services.organization;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.exception.EntityNotCreatedException;
import com.spring.app.repository.organization.JpaOrganizationRepository;
import com.spring.app.rest.dto.organization.OrganizationDto;
import com.spring.app.rest.dto.organization.OrganizationNameDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrganizationServiceImpl implements OrganizationService{

    private final JpaOrganizationRepository jpaOrganizationRepository;

    public OrganizationServiceImpl(JpaOrganizationRepository jpaOrganizationRepository) {
        this.jpaOrganizationRepository = jpaOrganizationRepository;
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto dto , String name) {

        Organization organizationFromDB  = jpaOrganizationRepository.findByName(name)
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

        return OrganizationDto.toDto(
                jpaOrganizationRepository.save(organizationFromDB));
    }

    @Override
    public OrganizationNameDto addOrganization(OrganizationDto dto) {

        // Проверка на уже существующую Organization by name
        Optional<Organization> organizationFromDB = jpaOrganizationRepository.findAll()
                .stream()
                .filter(organization -> Objects.equals(organization.getName(),dto.getName()))
                .findFirst();

        if(organizationFromDB.isPresent()){
            throw new EntityNotCreatedException("This organization already exists");
        }

        return OrganizationNameDto.toDto(
                jpaOrganizationRepository.save(OrganizationDto.toDomainObject(dto)));
    }

    @Override
    public List<OrganizationDto> getOrganizations() {
        return jpaOrganizationRepository
                .sortOrganizationsByRating()
                .stream()
                .map(OrganizationDto::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public String removeOrganization(String name) {

        Organization organizationFromDB  = jpaOrganizationRepository.findByName(name)
                .orElseThrow(() -> new NotFoundEntityException("Organization not found in database"));

        List<Employee> remoteEmployees = jpaOrganizationRepository.sortEmployeeByOrganizationName(organizationFromDB.getName());
        remoteEmployees
                .forEach(employee -> jpaOrganizationRepository.removeEmployeeById(employee.getId()));
        jpaOrganizationRepository.removeOrganizationByName(organizationFromDB.getName());

        return "Organization successfully deleted";
    }
}
