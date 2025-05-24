package pe.jesusamigo.backend_libreria.auth.service;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import pe.jesusamigo.backend_libreria.auth.dto.JwtAuthResponse;
import pe.jesusamigo.backend_libreria.auth.dto.LoginRequest;
import pe.jesusamigo.backend_libreria.persons.person.dto.PersonResponseDTO;
import pe.jesusamigo.backend_libreria.persons.person.entity.Person;
import pe.jesusamigo.backend_libreria.persons.person.mapper.PersonMapper;
import pe.jesusamigo.backend_libreria.persons.person.repository.PersonRepository;
import pe.jesusamigo.backend_libreria.security.JwtTokenProvider;
import pe.jesusamigo.backend_libreria.user.entity.User;
import pe.jesusamigo.backend_libreria.user.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final String TOKEN_TYPE = "Bearer";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public JwtAuthResponse authenticate(LoginRequest request) {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());
        authenticationManager.authenticate(authToken);

        String jwt = tokenProvider.generateToken(authToken);

        User userEntity = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado."));

        Optional<Person> personOptional = personRepository.findByUserUsername(request.getUsername());
        PersonResponseDTO personResponseDTO = personOptional
                .map(personMapper::toDto)     // ahora llamamos al método de instancia
                .orElse(null);

        return new JwtAuthResponse(jwt, TOKEN_TYPE, personResponseDTO);
    }
}
