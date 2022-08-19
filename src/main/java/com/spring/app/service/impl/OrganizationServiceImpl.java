package com.spring.app.service.impl;

import com.spring.app.dao.model.Organization;
import com.spring.app.dao.repository.EmployeeRepository;
import com.spring.app.dao.repository.OrganizationRepository;
import com.spring.app.exception.EntityNotCreatedException;
import com.spring.app.exception.NotFoundEntityException;
import com.spring.app.rest.dto.OrganizationDto;
import com.spring.app.rest.mapper.OrganizationMapper;
import com.spring.app.service.OrganizationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationServiceImpl implements OrganizationService {

    EmployeeRepository employeeRepository;
    OrganizationRepository organizationRepository;

    OrganizationMapper organizationMapper;

    @Override
    @Transactional
    public void update(OrganizationDto organizationDto, String name) {
        Organization organizationFromDB = organizationRepository.findByName(name)
                                                                .orElseThrow(() -> new NotFoundEntityException(
                                                                        "Organization not found in database"));
        organizationMapper.updateFromDto(organizationDto, organizationFromDB);
        organizationRepository.save(organizationFromDB);
    }

    @Override
    @Transactional
    public Long create(OrganizationDto organizationFillFormDto) {
        if (organizationRepository.existsByName(organizationFillFormDto.getName())) {
            throw new EntityNotCreatedException("This organization already exists");
        }
        var organization = organizationRepository.save(organizationMapper.toEntity(organizationFillFormDto));
        return organization.getId();
    }

    @Override
    public List<OrganizationDto> getAll() {
        return organizationRepository
                .sortOrganizationsByRating()
                .stream()
                .map(organizationMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void remove(String name) {
        var organizationFromDB = organizationRepository.findByName(name)
                                                       .orElseThrow(() -> new NotFoundEntityException(
                                                               "Organization not found in database"));

        employeeRepository.deleteAllByOrganization_Id(organizationFromDB.getId());
        organizationRepository.removeOrganizationByName(organizationFromDB.getName());
    }
}
