package one.digitalinnovation.personapi.service;


import lombok.AllArgsConstructor;
import one.digitalinnovation.personapi.dto.response.MessageResponseDTO;
import one.digitalinnovation.personapi.dto.response.request.PersonDTO;
import one.digitalinnovation.personapi.entity.Person;
import one.digitalinnovation.personapi.exception.PersonNotFoundException;
import one.digitalinnovation.personapi.dto.response.mapper.PersonMapper;
import one.digitalinnovation.personapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PersonService {

    private final PersonRepository personRepository;

    private final PersonMapper personMapper;

    public MessageResponseDTO create(PersonDTO personDTO) {
        Person person = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(person);

        MessageResponseDTO messageResponse = createMessageResponse("Person successfully created with ID ", savedPerson.getId());

        return messageResponse;
    }


    public List<PersonDTO> listAll() {
        List<Person> allPeople = personRepository.findAll();
        return allPeople.stream()
                .map(personMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PersonDTO findById(Long id) throws PersonNotFoundException {
        Person person = verifyExists(id);

        return personMapper.toDTO(person);
    }


    public void delete(Long id) throws PersonNotFoundException {
        verifyExists(id);

        personRepository.deleteById(id);
    }

    private Person verifyExists(Long id) throws PersonNotFoundException {
        return personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));
    }

    public MessageResponseDTO update(Long id, PersonDTO personDTO) throws PersonNotFoundException {
        personRepository.findById(id)
                .orElseThrow(() -> new PersonNotFoundException(id));

        Person updatedPerson = personMapper.toModel(personDTO);
        Person savedPerson = personRepository.save(updatedPerson);

        MessageResponseDTO messageResponse = createMessageResponse("Person successfully updated with ID ", savedPerson.getId());

        return messageResponse;
    }

    private MessageResponseDTO createMessageResponse(String message, Long id) {
        return MessageResponseDTO.builder()
                .message(message + id)
                .build();
    }
}
