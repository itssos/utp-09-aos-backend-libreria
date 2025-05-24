package pe.jesusamigo.backend_libreria.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.jesusamigo.backend_libreria.product.dto.ProductCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.ProductResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Author;
import pe.jesusamigo.backend_libreria.product.entity.Category;
import pe.jesusamigo.backend_libreria.product.entity.Editorial;
import pe.jesusamigo.backend_libreria.product.entity.Product;
import pe.jesusamigo.backend_libreria.product.mapper.ProductMapper;
import pe.jesusamigo.backend_libreria.product.repository.AuthorRepository;
import pe.jesusamigo.backend_libreria.product.repository.CategoryRepository;
import pe.jesusamigo.backend_libreria.product.repository.EditorialRepository;
import pe.jesusamigo.backend_libreria.product.repository.ProductRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final EditorialRepository editorialRepository;
    private final ProductMapper productMapper;

    /**
     * Crea un nuevo producto.
     */
    public ProductResponseDTO create(ProductCreateDTO dto) {
        Author author = authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado con ID: " + dto.getAuthorId()));

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + dto.getCategoryId()));

        Editorial editorial = editorialRepository.findById(dto.getEditorialId())
                .orElseThrow(() -> new IllegalArgumentException("Editorial no encontrada con ID: " + dto.getEditorialId()));

        Product product = productMapper.toEntity(dto);
        // Asignar entidades completamente cargadas (para evitar detached entities)
        product.setAuthor(author);
        product.setCategory(category);
        product.setEditorial(editorial);

        Product saved = productRepository.save(product);
        return productMapper.toResponseDTO(saved);
    }

    /**
     * Lista todos los productos.
     */
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Busca un producto por su ID.
     */
    @Transactional(readOnly = true)
    public Optional<ProductResponseDTO> findById(Integer id) {
        return productRepository.findById(id)
                .map(productMapper::toResponseDTO);
    }

    /**
     * Actualiza un producto existente.
     */
    public Optional<ProductResponseDTO> update(Integer id, ProductCreateDTO dto) {
        return productRepository.findById(id).map(existing -> {
            Author author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new IllegalArgumentException("Autor no encontrado con ID: " + dto.getAuthorId()));

            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada con ID: " + dto.getCategoryId()));

            Editorial editorial = editorialRepository.findById(dto.getEditorialId())
                    .orElseThrow(() -> new IllegalArgumentException("Editorial no encontrada con ID: " + dto.getEditorialId()));

            existing.setTitle(dto.getTitle());
            existing.setIsbn(dto.getIsbn());
            existing.setCode(dto.getCode());
            existing.setImageUrl(dto.getImageUrl());
            existing.setAuthor(author);
            existing.setCategory(category);
            existing.setEditorial(editorial);
            existing.setPrice(dto.getPrice());
            existing.setStock(dto.getStock());
            existing.setDescription(dto.getDescription());
            existing.setPublicationDate(dto.getPublicationDate());
            existing.setActive(dto.getActive());

            Product updated = productRepository.save(existing);
            return productMapper.toResponseDTO(updated);
        });
    }

    /**
     * Elimina un producto por su ID.
     */
    public boolean delete(Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
