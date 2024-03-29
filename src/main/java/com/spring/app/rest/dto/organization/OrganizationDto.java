package com.spring.app.rest.dto.organization;

import com.spring.app.dao.model.Organization;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;


@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
public class OrganizationDto {

    Long id;

    String name;

    String description;

    Long rating;

}
