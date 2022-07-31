package com.spring.app.services.servicerOrganization;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.JpaOrganizationRepository;
import com.spring.app.rest.dto.organizationDto.OrganizationDto;
import com.spring.app.rest.dto.organizationDto.OrganizationDtoOnlyNane;
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

        Organization organizationFromTheRepository  = jpaOrganizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);

        if(!Objects.equals(organizationFromTheRepository.getName(),dto.getName())){
            organizationFromTheRepository.setName(dto.getName());
        }
        if(!Objects.equals(organizationFromTheRepository.getDescription(),dto.getDescription())){
            organizationFromTheRepository.setDescription(dto.getDescription());
        }
        if(!Objects.equals(organizationFromTheRepository.getRating(),dto.getRating())){
            organizationFromTheRepository.setRating(dto.getRating());
        }

        return OrganizationDto.toDto(
                jpaOrganizationRepository.save(organizationFromTheRepository));
    }

    @Override
    public OrganizationDtoOnlyNane addOrganization(OrganizationDto dto) {
        Optional<Organization> organizationName = jpaOrganizationRepository.findAll()
                .stream()
                .filter(organization -> Objects.equals(organization.getName(),dto.getName()))
                .findFirst();

        if(organizationName.isPresent()){
            throw new ServiceException();
        }

        return OrganizationDtoOnlyNane.toDto(
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

        Organization organizationFromTheRepository  = jpaOrganizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);

        List<Employee> remoteEmployees = jpaOrganizationRepository.newMethodSortedPlus(organizationFromTheRepository.getName());
        remoteEmployees
                .forEach(employee -> jpaOrganizationRepository.newMethodDeleteEmployeeById(employee.getId()));
        jpaOrganizationRepository.newMethodDeleteOrganizationByName(organizationFromTheRepository.getName());

        return "Organization successfully deleted";
    }
}
