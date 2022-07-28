package com.spring.app.services.servicerOrganization;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.OrganizationRepository;
import com.spring.app.repository.jpql.JpqlQueryRepository;
import com.spring.app.repository.nativeQuery.OrganizationNativeQueryRepository;
import com.spring.app.rest.dto.organizationDto.OrganizationDto;
import com.spring.app.rest.dto.organizationDto.OrganizationDtoOnlyId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService{

    private final JpqlQueryRepository jpqlQueryRepository;
    private final OrganizationRepository organizationRepository;
    private final OrganizationNativeQueryRepository organizationNativeQueryRepository;

    public OrganizationServiceImpl(JpqlQueryRepository jpqlQueryRepository,
                                   OrganizationRepository organizationRepository,
                                   OrganizationNativeQueryRepository organizationNativeQueryRepository) {
        this.jpqlQueryRepository = jpqlQueryRepository;
        this.organizationRepository = organizationRepository;
        this.organizationNativeQueryRepository = organizationNativeQueryRepository;
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto dto , Long idOrganization) {


        Organization editingOrganizations = organizationRepository.findById(idOrganization).orElseThrow(RepositoryException::new);
        if(!Objects.equals(editingOrganizations.getName(),dto.getName())){
            editingOrganizations.setName(dto.getName());

        }
        if(!Objects.equals(editingOrganizations.getDescription(),dto.getDescription())){
            editingOrganizations.setDescription(dto.getDescription());

        }
        if(!Objects.equals(editingOrganizations.getRating(),dto.getRating())){
            editingOrganizations.setRating(dto.getRating());

        }
        organizationRepository.save(editingOrganizations);
        return OrganizationDto.toDto(editingOrganizations);
    }

    @Override
    public OrganizationDtoOnlyId addOrganization(OrganizationDto dto) {
        Organization newOrganization = OrganizationDto.toDomainObject(dto);
        return OrganizationDtoOnlyId.toDto(organizationRepository.save(newOrganization));
    }

    @Override
    public List<OrganizationDto> getOrganizations() {
        return organizationNativeQueryRepository
                .sortOrganizationsByRating()
                .stream()
                .map(OrganizationDto::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public String removeOrganization(Long idOrganization) {
        boolean isSuccessfully = false;

        List <Employee> deleteEmployee = jpqlQueryRepository.sortedEmployees(idOrganization);
        List<Organization> organizations = organizationRepository.findAll();

        for (Organization organization : organizations) {
            if(Objects.equals(organization.getId(),idOrganization)){
                for (Employee employee : deleteEmployee) {
                    jpqlQueryRepository.deleteEmployeeById(employee.getId());
                }

                jpqlQueryRepository.deleteOrganizationById(idOrganization);
                isSuccessfully = true;
            }
        }
        if(!isSuccessfully){
            throw new ServiceException();
        }

        return "Organization successfully deleted";
    }
}
