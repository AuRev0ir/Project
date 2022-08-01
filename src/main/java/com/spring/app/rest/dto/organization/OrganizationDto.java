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


    String name;

    String description;

    Long rating;



    public static OrganizationDto toDto (Organization organization){
        return new OrganizationDto(

                organization.getName(),
                organization.getDescription(),
                organization.getRating());
    }

    public static Organization toDomainObject (OrganizationDto dto) {
        return new Organization(
                dto.getName(),
                dto.getDescription(),
                dto.getRating());
    }
}
