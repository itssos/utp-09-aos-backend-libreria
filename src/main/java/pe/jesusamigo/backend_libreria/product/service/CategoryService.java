package pe.jesusamigo.backend_libreria.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.product.dto.CategoryCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.CategoryResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Category;
import pe.jesusamigo.backend_libreria.product.mapper.CategoryMapper;
import pe.jesusamigo.backend_libreria.product.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    /**
     * Crea una nueva categoría.
     */
    public CategoryResponseDTO create(CategoryCreateDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        Category saved = categoryRepository.save(category);
        return categoryMapper.toResponseDTO(saved);
    }

    /**
     * Devuelve la lista de todas las categorías.
     */
    @Transactional(readOnly = true)
    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca una categoría por su ID.
     */
    @Transactional(readOnly = true)
    public Optional<CategoryResponseDTO> findById(Integer id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toResponseDTO);
    }

    /**
     * Actualiza una categoría existente.
     */
    public Optional<CategoryResponseDTO> update(Integer id, CategoryCreateDTO dto) {
        return categoryRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setDescription(dto.getDescription());
            existing.setActive(dto.getActive());
            Category updated = categoryRepository.save(existing);
            return categoryMapper.toResponseDTO(updated);
        });
    }

    /**
     * Elimina (lógico o físico) una categoría por su ID.
     */
    public boolean delete(Integer id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
