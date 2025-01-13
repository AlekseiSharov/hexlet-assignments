package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import exercise.model.Contact;
import exercise.repository.ContactRepository;
import exercise.dto.ContactDTO;
import exercise.dto.ContactCreateDTO;

@RestController
@RequestMapping("/contacts")
public class ContactsController {

    @Autowired
    private ContactRepository contactRepository;

    // BEGIN
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ContactDTO createContact(@RequestBody ContactCreateDTO body) {
        var contact = toEntity(body);
        contactRepository.save(contact);

        return toDTO(contact);
    }

    /**
     * @param createDTO ContactCreateDTO
     */
    private Contact toEntity(ContactCreateDTO createDTO) {
        var entity = new Contact();

        entity.setFirstName(createDTO.getFirstName());
        entity.setLastName(createDTO.getLastName());
        entity.setPhone(createDTO.getPhone());

        return entity;
    }

    /**
     * @param entity Contact entity
     */
    private ContactDTO toDTO(Contact entity) {
        var dto = new ContactDTO();

        dto.setId(entity.getId());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }
    // END
}
