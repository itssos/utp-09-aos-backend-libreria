package pe.jesusamigo.backend_libreria.product.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.jesusamigo.backend_libreria.product.dto.ProductCreateDTO;
import pe.jesusamigo.backend_libreria.product.dto.ProductResponseDTO;
import pe.jesusamigo.backend_libreria.product.entity.Author;
import pe.jesusamigo.backend_libreria.product.entity.Category;
import pe.jesusamigo.backend_libreria.product.entity.Editorial;
import pe.jesusamigo.backend_libreria.product.entity.Product;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final AuthorMapper authorMapper;
    private final CategoryMapper categoryMapper;
    private final EditorialMapper editorialMapper;

    public Product toEntity(ProductCreateDTO dto) {
        return Product.builder()
                .title(dto.getTitle())
                .isbn(dto.getIsbn())
                .code(dto.getCode())
                .imageUrl(dto.getImageUrl())
                // Relaciones: referenciamos sólo por ID
                .author(Author.builder().id(dto.getAuthorId()).build())
                .category(Category.builder().id(dto.getCategoryId()).build())
                .editorial(Editorial.builder().id(dto.getEditorialId()).build())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .description(dto.getDescription())
                .publicationDate(dto.getPublicationDate())
                .active(dto.getActive())
                .build();
    }

    public ProductResponseDTO toResponseDTO(Product entity) {
        return ProductResponseDTO.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .code(entity.getCode())
                .imageUrl(entity.getImageUrl())
                .author(authorMapper.toResponseDTO(entity.getAuthor()))
                .category(categoryMapper.toResponseDTO(entity.getCategory()))
                .editorial(editorialMapper.toResponseDTO(entity.getEditorial()))
                .price(entity.getPrice())
                .stock(entity.getStock())
                .description(entity.getDescription())
                .publicationDate(entity.getPublicationDate())
                .active(entity.getActive())
                .build();
    }
}
