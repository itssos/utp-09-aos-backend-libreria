package pe.jesusamigo.backend_libreria.product.mapper;

import org.springframework.stereotype.Component;
import pe.jesusamigo.backend_libreria.product.dto.CategoryCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.CategoryResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Category;

@Component
public class CategoryMapper {

    public Category toEntity(CategoryCreateDTO dto) {
        return Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .active(dto.getActive())
                .build();
    }

    public CategoryResponseDTO toResponseDTO(Category entity) {
        return CategoryResponseDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .active(entity.getActive())
                .build();
    }
}
