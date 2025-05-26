package pe.jesusamigo.backend_libreria.persons.person.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.exception.ResourceNotFoundException;
import pe.jesusamigo.backend_libreria.persons.person.dto.PersonCreateDTO;
import pe.jesusamigo.backend_libreria.persons.person.dto.PersonResponseDTO;
import pe.jesusamigo.backend_libreria.persons.person.entity.Person;
import pe.jesusamigo.backend_libreria.persons.person.mapper.PersonMapper;
import pe.jesusamigo.backend_libreria.persons.person.repository.PersonRepository;
import pe.jesusamigo.backend_libreria.role.service.RoleService;
import pe.jesusamigo.backend_libreria.user.entity.User;
import pe.jesusamigo.backend_libreria.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class PersonService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PersonMapper personMapper;
    private final PasswordEncoder passwordEncoder;

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

        personEntity.getUser().setPassword(passwordEncoder.encode(personEntity.getUser().getPassword()));

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

    public PersonResponseDTO update(Long id, PersonCreateDTO dto) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));

        // Validar cambios de DNI y username únicos si cambiaron
        if (!person.getDni().equals(dto.getDni()) && personRepository.findByDni(dto.getDni()).isPresent()) {
            throw new IllegalArgumentException("Ya existe una persona registrada con el DNI: " + dto.getDni());
        }

        if (!person.getUser().getUsername().equals(dto.getUser().getUsername()) && userRepository.existsByUsername(dto.getUser().getUsername())) {
            throw new IllegalArgumentException("Ya existe un usuario con el nombre de usuario: " + dto.getUser().getUsername());
        }

        // Actualiza datos de persona
        person.setFirstName(dto.getFirstName());
        person.setLastName(dto.getLastName());
        person.setDni(dto.getDni());
        person.setGender(dto.getGender());
        person.setAddress(dto.getAddress());
        person.setPhone(dto.getPhone());
        person.setBirthDate(dto.getBirthDate());

        // Actualiza datos de usuario
        User user = person.getUser();
        user.setUsername(dto.getUser().getUsername());
        user.setPassword(passwordEncoder.encode(dto.getUser().getPassword())); // Hashear si corresponde
        user.setRole(roleService.getRoleByName(dto.getUser().getRole()));

        person.setUser(user);

        person = personRepository.save(person);

        return personMapper.toDto(person);
    }

    /**
     * Elimina una persona por ID (y su usuario asociado, por cascade).
     */
    public void deleteById(Long id) {
        Person person = personRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Persona no encontrada con ID: " + id));
//        // Elimina primero el usuario
//        User user = person.getUser();
//        if (user != null) {
//            userRepository.delete(user);
//        }
        personRepository.delete(person);
    }
}