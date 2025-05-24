package pe.jesusamigo.backend_libreria.product.mapper;

import org.springframework.stereotype.Component;
import pe.jesusamigo.backend_libreria.product.dto.AuthorCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.AuthorResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Author;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorCreateDTO dto) {
        return Author.builder()
                .name(dto.getName())
                .bio(dto.getBio())
                .active(dto.getActive())
                .build();
    }

    public AuthorResponseDTO toResponseDTO(Author entity) {
        return AuthorResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .bio(entity.getBio())
                .active(entity.getActive())
                .build();
    }
}
