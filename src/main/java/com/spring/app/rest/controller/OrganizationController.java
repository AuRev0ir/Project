package com.spring.app.rest.controller;

import com.spring.app.rest.dto.OrganizationDto;
import com.spring.app.service.OrganizationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrganizationController implements OrganizationApi {

    OrganizationService organizationService;

    // Спецификация должны быть описана в соответствующем файле, а не аннотациями, если используешь автогенерацию кода из спецификации

    @Override
    public ResponseEntity<Long> addNewOrganization(OrganizationDto organizationDto) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(organizationService.create(organizationDto));
    }

    @Override
    public ResponseEntity<Void> deleteOrganization(String name) {
        organizationService.remove(name);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<OrganizationDto>> getAllWithRatingOrdering() {
        return ResponseEntity.ok(organizationService.getAll());
    }

    @Override
    public ResponseEntity<Void> updateOrganizationData(String name, OrganizationDto organizationDto) {
        organizationService.update(organizationDto, name);
        return ResponseEntity.noContent().build();
    }
}
