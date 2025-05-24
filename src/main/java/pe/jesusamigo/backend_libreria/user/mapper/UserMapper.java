package pe.jesusamigo.backend_libreria.user.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pe.jesusamigo.backend_libreria.persons.person.entity.Person;
import pe.jesusamigo.backend_libreria.role.entity.Permission;
import pe.jesusamigo.backend_libreria.role.entity.Role;
import pe.jesusamigo.backend_libreria.role.service.RoleService;
import pe.jesusamigo.backend_libreria.user.dto.UserCreateDTO;
import pe.jesusamigo.backend_libreria.user.dto.UserResponseDTO;
import pe.jesusamigo.backend_libreria.user.dto.UserShortResponseDTO;
import pe.jesusamigo.backend_libreria.user.entity.User;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    private final RoleService roleService;

    @Autowired
    public UserMapper(RoleService roleService) {
        this.roleService = roleService;
    }

    public UserResponseDTO toDto(User user) {
        if (user == null) return null;

        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole() != null ? user.getRole().getName() : null);
        dto.setPermissions(
                user.getRole() != null && user.getRole().getPermissions() != null
                        ? user.getRole().getPermissions()
                        .stream()
                        .map(Permission::getName)
                        .collect(Collectors.toSet())
                        : null
        );
        return dto;
    }

    public User fromCreateDto(UserCreateDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        if (dto.getRole() != null) {
            Role role = roleService.getRoleByName(dto.getRole());
            user.setRole(role);
        }
        return user;
    }

    public UserShortResponseDTO toShortDto(User user, Person person) {
        if (user == null) return null;
        String fullName = null;
        if (person != null) {
            String firstName = person.getFirstName() != null ? person.getFirstName() : "";
            String lastName = person.getLastName() != null ? person.getLastName() : "";
            fullName = (firstName + " " + lastName).trim();
        }
        return UserShortResponseDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(fullName)
                .build();
    }

}
