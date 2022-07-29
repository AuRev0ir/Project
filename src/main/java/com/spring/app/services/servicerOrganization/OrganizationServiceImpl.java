package com.spring.app.services.servicerOrganization;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.OrganizationRepository;
import com.spring.app.repository.nativeQuery.OrganizationNativeQueryRepository;
import com.spring.app.rest.dto.organizationDto.OrganizationDto;
import com.spring.app.rest.dto.organizationDto.OrganizationDtoOnlyNane;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrganizationServiceImpl implements OrganizationService{


    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public OrganizationDto updateOrganization(OrganizationDto dto , String name) {

        Organization editingOrganizations = organizationRepository.findByName(name).orElseThrow(RepositoryException::new);
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
    public OrganizationDtoOnlyNane addOrganization(OrganizationDto dto) {
        Organization newOrganization = OrganizationDto.toDomainObject(dto);
        return OrganizationDtoOnlyNane.toDto(organizationRepository.save(newOrganization));
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
        boolean isSuccessfully = false;

        List <Employee> deleteEmployee = organizationRepository.newMethodSortedPlus(name);
        List<Organization> organizations = organizationRepository.findAll();

        for (Organization organization : organizations) {
            if(Objects.equals(organization.getName(),name)){
                for (Employee employee : deleteEmployee) {
                    organizationRepository.newMethodDeleteEmployeeById(employee.getId());
                }

                organizationRepository.newMethodDeleteOrganizationById(name);
                isSuccessfully = true;
            }
        }
        if(!isSuccessfully){
            throw new ServiceException();
        }

        return "Organization successfully deleted";
    }
}
