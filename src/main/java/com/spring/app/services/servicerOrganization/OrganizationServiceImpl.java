package com.spring.app.services.servicerOrganization;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.OrganizationRepository;
import com.spring.app.rest.dto.organizationDto.OrganizationDto;
import com.spring.app.rest.dto.organizationDto.OrganizationDtoOnlyNane;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class OrganizationServiceImpl implements OrganizationService{


    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto dto , String name) {

        Organization organizationFromTheRepository  = organizationRepository.findByName(name)
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
                organizationRepository.save(organizationFromTheRepository));
    }

    @Override
    public OrganizationDtoOnlyNane addOrganization(OrganizationDto dto) {
        Optional<Organization> organizationName = organizationRepository.findAll()
                .stream()
                .filter(organization -> Objects.equals(organization.getName(),dto.getName()))
                .findFirst();

        if(organizationName.isPresent()){
            throw new ServiceException();
        }

        return OrganizationDtoOnlyNane.toDto(
                organizationRepository.save(OrganizationDto.toDomainObject(dto)));
    }

    @Override
    public List<OrganizationDto> getOrganizations() {
        return organizationRepository
                .sortOrganizationsByRating()
                .stream()
                .map(OrganizationDto::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public String removeOrganization(String name) {

        Organization organizationFromTheRepository  = organizationRepository.findByName(name)
                .orElseThrow(RepositoryException::new);

        List<Employee> remoteEmployees = organizationRepository.newMethodSortedPlus(organizationFromTheRepository.getName());
        remoteEmployees
                .forEach(employee -> organizationRepository.newMethodDeleteEmployeeById(employee.getId()));
        organizationRepository.newMethodDeleteOrganizationByName(organizationFromTheRepository.getName());

        return "Organization successfully deleted";
    }
}
