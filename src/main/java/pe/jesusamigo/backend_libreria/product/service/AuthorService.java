package pe.jesusamigo.backend_libreria.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.product.dto.AuthorCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.AuthorResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Author;
import pe.jesusamigo.backend_libreria.product.mapper.AuthorMapper;
import pe.jesusamigo.backend_libreria.product.repository.AuthorRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    /**
     * Crea un nuevo autor.
     */
    public AuthorResponseDTO create(AuthorCreateDTO dto) {
        Author author = authorMapper.toEntity(dto);
        Author saved = authorRepository.save(author);
        return authorMapper.toResponseDTO(saved);
    }

    /**
     * Devuelve la lista de todos los autores.
     */
    @Transactional(readOnly = true)
    public List<AuthorResponseDTO> findAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un autor por su ID.
     */
    @Transactional(readOnly = true)
    public Optional<AuthorResponseDTO> findById(Integer id) {
        return authorRepository.findById(id)
                .map(authorMapper::toResponseDTO);
    }

    /**
     * Actualiza un autor existente.
     */
    public Optional<AuthorResponseDTO> update(Integer id, AuthorCreateDTO dto) {
        return authorRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setBio(dto.getBio());
            existing.setActive(dto.getActive());
            Author updated = authorRepository.save(existing);
            return authorMapper.toResponseDTO(updated);
        });
    }

    /**
     * Elimina (físicamente) un autor por su ID.
     */
    public boolean delete(Integer id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
