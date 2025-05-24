package pe.jesusamigo.backend_libreria.product.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.product.dto.EditorialCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.EditorialResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Editorial;
import pe.jesusamigo.backend_libreria.product.mapper.EditorialMapper;
import pe.jesusamigo.backend_libreria.product.repository.EditorialRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio de negocio para la entidad Editorial.
 */
@Service
@RequiredArgsConstructor
public class EditorialService {

    private final EditorialRepository repository;
    private final EditorialMapper mapper;

    /**
     * Crea una nueva Editorial.
     *
     * @param dto datos para la creación
     * @return DTO con los datos de la Editorial creada
     */
    @Transactional
    public EditorialResponseDTO create(EditorialCreateDTO dto) {
        Editorial entity = mapper.toEntity(dto);
        Editorial saved = repository.save(entity);
        return mapper.toResponseDTO(saved);
    }

    /**
     * Obtiene todas las Editoriales.
     *
     * @return lista de DTOs de Editorial
     */
    @Transactional(readOnly = true)
    public List<EditorialResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca una Editorial por su ID.
     *
     * @param id identificador de la Editorial
     * @return DTO de la Editorial encontrada
     * @throws EntityNotFoundException si no existe la Editorial
     */
    @Transactional(readOnly = true)
    public EditorialResponseDTO findById(Integer id) {
        Editorial entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Editorial no encontrada con id: " + id)
                );
        return mapper.toResponseDTO(entity);
    }

    /**
     * Actualiza una Editorial existente.
     *
     * @param id  identificador de la Editorial a actualizar
     * @param dto datos de actualización
     * @return DTO de la Editorial actualizada
     * @throws EntityNotFoundException si no existe la Editorial
     */
    @Transactional
    public EditorialResponseDTO update(Integer id, EditorialCreateDTO dto) {
        Editorial entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Editorial no encontrada con id: " + id)
                );
        entity.setName(dto.getName());
        entity.setActive(dto.getActive());
        Editorial updated = repository.save(entity);
        return mapper.toResponseDTO(updated);
    }

    /**
     * Elimina (desactiva) una Editorial.
     *
     * @param id identificador de la Editorial
     * @throws EntityNotFoundException si no existe la Editorial
     */
    @Transactional
    public void delete(Integer id) {
        Editorial entity = repository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Editorial no encontrada con id: " + id)
                );
        // Opcional: si prefieres borrado lógico, comentar la línea de abajo y usar entity.setActive(false);
        repository.delete(entity);
    }
}
