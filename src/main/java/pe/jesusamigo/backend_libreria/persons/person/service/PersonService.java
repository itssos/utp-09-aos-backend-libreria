package pe.jesusamigo.backend_libreria.persons.person.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.exception.ResourceNotFoundException;
import pe.jesusamigo.backend_libreria.persons.person.dto.PersonCreateDTO;
import pe.jesusamigo.backend_libreria.persons.person.dto.PersonResponseDTO;
import pe.jesusamigo.backend_libreria.persons.person.entity.Person;
import pe.jesusamigo.backend_libreria.persons.person.mapper.PersonMapper;
import pe.jesusamigo.backend_libreria.persons.person.repository.PersonRepository;
import pe.jesusamigo.backend_libreria.role.service.RoleService;
import pe.jesusamigo.backend_libreria.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PersonMapper personMapper;

    /**
     * Crea una nueva persona y su usuario asociado.
     * Valida que el DNI y username sean únicos.
     */
    public PersonResponseDTO createPerson(PersonCreateDTO dto) {
        String dni = dto.getDni();
        String username = dto.getUser().getUsername();

        if (personRepository.findByDni(dni).isPresent()) {
            throw new IllegalArgumentException("Ya existe una persona registrada con el DNI: " + dni);
        }

        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Ya existe un usuario con el nombre de usuario: " + username);
        }

        // Asignar rol desde nombre (el DTO debe traerlo explícitamente o forzarlo desde afuera)
        dto.getUser().setRole(
                roleService.getRoleByName(dto.getUser().getRole()).getName()
        );

        // Mapear y guardar entidad
        Person personEntity = personMapper.fromCreateDto(dto);
        personEntity = personRepository.save(personEntity);

        return personMapper.toDto(personEntity);
    }

    /**
     * Obtiene una persona por ID.
     */
    public PersonResponseDTO getById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
        return personMapper.toDto(person);
    }

    /**
     * Elimina una persona por ID (y su usuario asociado, por cascade).
     */
    public void deleteById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
        personRepository.delete(person);
    }
}