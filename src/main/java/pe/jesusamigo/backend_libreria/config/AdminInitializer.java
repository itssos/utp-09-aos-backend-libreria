package pe.jesusamigo.backend_libreria.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.persons.admin.entity.Admin;
import pe.jesusamigo.backend_libreria.persons.person.enums.Gender;
import pe.jesusamigo.backend_libreria.persons.admin.repository.AdminRepository;
import pe.jesusamigo.backend_libreria.persons.person.entity.Person;
import pe.jesusamigo.backend_libreria.persons.person.repository.PersonRepository;
import pe.jesusamigo.backend_libreria.role.entity.Permission;
import pe.jesusamigo.backend_libreria.role.entity.Role;
import pe.jesusamigo.backend_libreria.role.enums.PermissionConstants;
import pe.jesusamigo.backend_libreria.role.enums.RoleConstants;
import pe.jesusamigo.backend_libreria.role.repository.PermissionRepository;
import pe.jesusamigo.backend_libreria.role.repository.RoleRepository;
import pe.jesusamigo.backend_libreria.user.entity.User;
import pe.jesusamigo.backend_libreria.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class AdminInitializer implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(AdminInitializer.class);

    private final Environment env;
    private final RoleRepository    roleRepo;
    private final PermissionRepository permRepo;
    private final UserRepository    userRepo;
    private final AdminRepository adminRepository;
    private final PasswordEncoder   passwordEncoder;
    private final PersonRepository personRepository;

    public AdminInitializer(Environment env,
                            RoleRepository roleRepo,
                            PermissionRepository permRepo,
                            UserRepository userRepo,
                            AdminRepository adminRepository,
                            PasswordEncoder passwordEncoder, PersonRepository personRepository) {
        this.env             = env;
        this.roleRepo        = roleRepo;
        this.permRepo        = permRepo;
        this.userRepo        = userRepo;
        this.adminRepository      = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.personRepository = personRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        initializeRoles();
        initializePermissions();
        assignPermissionsToRoles();
        initializeAdminUserAndPerson();
    }

    private void initializeRoles() {
        for (RoleConstants rc : RoleConstants.values()) {
            roleRepo.findByName(rc.getCode()).ifPresentOrElse(
                    existing -> log.debug("Rol ya existe: {}", rc.getCode()),
                    () -> {
                        Role r = Role.builder()
                                .name(rc.getCode())
                                .description(rc.getLabel())
                                .build();
                        roleRepo.save(r);
                        log.info("✔ Rol creado: {} ({})", rc.getCode(), rc.getLabel());
                    }
            );
        }
    }

    private void initializePermissions() {
        for (PermissionConstants pc : PermissionConstants.values()) {
            permRepo.findByName(pc.getCode()).ifPresentOrElse(
                    existing -> log.debug("Permiso ya existe: {}", pc.getCode()),
                    () -> {
                        Permission p = Permission.builder()
                                .name(pc.getCode())
                                .label(pc.getLabel())
                                .build();
                        permRepo.save(p);
                        log.info("✔ Permiso creado: {} ({})", pc.getCode(), pc.getLabel());
                    }
            );
        }
    }

    private void assignPermissionsToRoles() {
        Map<RoleConstants,List<PermissionConstants>> mapping = rolePermissionsMapping();
        for (var entry : mapping.entrySet()) {
            RoleConstants rc = entry.getKey();
            Role role = roleRepo.findByName(rc.getCode())
                    .orElseThrow(() -> new IllegalStateException("Rol no encontrado: " + rc.getCode()));

            Set<Permission> perms = entry.getValue().stream()
                    .map(pc -> permRepo.findByName(pc.getCode())
                            .orElseThrow(() -> new IllegalStateException("Permiso no encontrado: " + pc.getCode())))
                    .collect(Collectors.toCollection(HashSet::new));

            role.setPermissions(perms);
            roleRepo.save(role);
            log.info("✔ Permisos asignados a {}: {}", rc.getCode(),
                    perms.stream().map(Permission::getName).toList());
        }
    }

    private Map<RoleConstants,List<PermissionConstants>> rolePermissionsMapping() {
        List<PermissionConstants> all = Arrays.asList(PermissionConstants.values());
        return Map.of(
                RoleConstants.ADMINISTRADOR, all
        );
    }

    private void initializeAdminUserAndPerson() {
        String adminUsername = env.getProperty("admin.user.username");
        if (adminUsername == null) {
            log.warn("Propiedad admin.user.username no configurada, omito creación de admin.");
            return;
        }
        if (userRepo.findByUsername(adminUsername).isEmpty()) {
            // Crear User de administrador
            String email = env.getProperty("admin.user.email", "");
            String rawPwd = env.getProperty("admin.user.password", "");
            Role adminRole = roleRepo.findByName(RoleConstants.ADMINISTRADOR.getCode())
                    .orElseThrow(() -> new IllegalStateException("Rol ADMINISTRADOR no encontrado"));
            User adminUser = User.builder()
                    .username(adminUsername)
                    .email(email)
                    .password(passwordEncoder.encode(rawPwd))
                    .active(true)
                    .role(adminRole)
                    .build();
            userRepo.save(adminUser);
            log.info("✔ Usuario administrador creado: {}", adminUsername);

            // Crear y guardar primero la persona
            Person person = new Person();
            person.setFirstName("Sair");
            person.setLastName("Marquez Hidalgo");
            person.setDni("12345678");
            person.setBirthDate(LocalDate.of(2003, 7, 22));
            person.setGender(Gender.MASCULINO);
            person.setAddress("Calle Aleatoria 123");
            person.setPhone("987654321");
            person.setUser(adminUser);

            Person savedPerson = personRepository.save(person);

            // Asociar la persona al admin y guardar
            Admin admin = new Admin();
            admin.setPerson(savedPerson);
            adminRepository.save(admin);
            log.info("✔ Persona creada para admin: {} {}", savedPerson.getFirstName(), savedPerson.getLastName());
        }
    }
}
