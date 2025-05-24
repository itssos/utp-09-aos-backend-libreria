package pe.jesusamigo.backend_libreria.product.mapper;

import org.springframework.stereotype.Component;
import pe.jesusamigo.backend_libreria.product.dto.EditorialCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.EditorialResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Editorial;

@Component
public class EditorialMapper {

    public Editorial toEntity(EditorialCreateDTO dto) {
        return Editorial.builder()
                .name(dto.getName())
                .active(dto.getActive())
                .build();
    }

    public EditorialResponseDTO toResponseDTO(Editorial entity) {
        return EditorialResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .active(entity.getActive())
                .build();
    }
}
