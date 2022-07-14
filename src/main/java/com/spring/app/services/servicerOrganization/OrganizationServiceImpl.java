package com.spring.app.services.servicerOrganization;

import com.spring.app.domain.Employee;
import com.spring.app.domain.Organization;
import com.spring.app.exception.RepositoryException;
import com.spring.app.exception.ServiceException;
import com.spring.app.repository.dataJpa.OrganizationRepository;
import com.spring.app.repository.jpql.JpqlQueryRepository;
import com.spring.app.repository.nativeQuery.OrganizationNativeQueryRepository;
import com.spring.app.rest.dto.organizationDto.OrganizationDto;
import com.spring.app.rest.dto.organizationDto.OrganizationIdDto;
import com.spring.app.rest.pojo.FormOrganization;
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
    public OrganizationDto editingOrganization(FormOrganization formOrganization , Long idOrganization) {


        Organization editingOrganizations = organizationRepository.findById(idOrganization).orElseThrow(RepositoryException::new);
        if(!Objects.equals(editingOrganizations.getNameOrganization(),formOrganization.getNameOrganization())){
            editingOrganizations.setNameOrganization(formOrganization.getNameOrganization());

        }
        if(!Objects.equals(editingOrganizations.getDescriptionOrganization(),formOrganization.getDescriptionOrganization())){
            editingOrganizations.setDescriptionOrganization(formOrganization.getDescriptionOrganization());

        }
        if(!Objects.equals(editingOrganizations.getRatingOrganization(),formOrganization.getRatingOrganization())){
            editingOrganizations.setRatingOrganization(formOrganization.getRatingOrganization());

        }
        organizationRepository.save(editingOrganizations);
        return OrganizationDto.toDto(editingOrganizations);
    }

    @Override
    public OrganizationIdDto addOrganization(FormOrganization formOrganization) {
        Organization newOrganization = new Organization(
                formOrganization.getNameOrganization(),
                formOrganization.getDescriptionOrganization()
                ,formOrganization.getRatingOrganization());
        return OrganizationIdDto.toDto(organizationRepository.save(newOrganization));
    }

    @Override
    public List<OrganizationDto> organizationList() {
        return organizationNativeQueryRepository
                .sortOrganizationsByRating()
                .stream()
                .map(OrganizationDto::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public String deleteOrganization(Long idOrganization) {
        boolean isSuccessfully = false;

        List <Employee> deleteEmployee = jpqlQueryRepository.sortedEmployees(idOrganization);
        List<Organization> organizations = organizationRepository.findAll();

        for (Organization organization : organizations) {
            if(Objects.equals(organization.getIdOrganization(),idOrganization)){
                for (Employee employee : deleteEmployee) {
                    jpqlQueryRepository.deleteEmployeeById(employee.getIdEmployee());
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
