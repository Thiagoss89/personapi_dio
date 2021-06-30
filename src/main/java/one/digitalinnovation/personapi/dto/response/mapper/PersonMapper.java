package one.digitalinnovation.personapi.dto.response.mapper;

import one.digitalinnovation.personapi.dto.response.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = "dd-MM-yyyy")
    Person toModel(PersonDTO dto);

    PersonDTO toDTO(Person dto);
}
