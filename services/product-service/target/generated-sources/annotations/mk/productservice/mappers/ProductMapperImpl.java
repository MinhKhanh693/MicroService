package mk.productservice.mappers;

import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import mk.productservice.controllers.requests.CreateProductRequestDto;
import mk.productservice.controllers.requests.ProductPurchaseRequestDto;
import mk.productservice.controllers.requests.UpdateProductRequestDto;
import mk.productservice.controllers.responses.ProductPurchaseResponseDto;
import mk.productservice.dtos.ProductDto;
import mk.productservice.entities.ProductEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-08T15:34:57+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductEntity toEntity(CreateProductRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setName( dto.name() );
        productEntity.setDescription( dto.description() );
        productEntity.setAvailableQuantity( dto.availableQuantity() );
        productEntity.setPrice( dto.price() );

        return productEntity;
    }

    @Override
    public ProductEntity toEntity(UpdateProductRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( dto.getId() );
        productEntity.setName( dto.getName() );
        productEntity.setDescription( dto.getDescription() );
        productEntity.setAvailableQuantity( dto.getAvailableQuantity() );
        productEntity.setPrice( dto.getPrice() );

        return productEntity;
    }

    @Override
    public ProductDto toDto(ProductEntity entity) {
        if ( entity == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;
        Integer availableQuantity = null;
        BigDecimal price = null;

        id = entity.getId();
        name = entity.getName();
        description = entity.getDescription();
        availableQuantity = entity.getAvailableQuantity();
        price = entity.getPrice();

        ProductDto productDto = new ProductDto( id, name, description, availableQuantity, price );

        return productDto;
    }

    @Override
    public ProductEntity toEntity(ProductPurchaseResponseDto productPurchaseResponseDto) {
        if ( productPurchaseResponseDto == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( productPurchaseResponseDto.id() );
        productEntity.setName( productPurchaseResponseDto.name() );
        productEntity.setDescription( productPurchaseResponseDto.description() );
        productEntity.setPrice( productPurchaseResponseDto.price() );

        return productEntity;
    }

    @Override
    public ProductPurchaseResponseDto toDto1(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;
        BigDecimal price = null;

        id = productEntity.getId();
        name = productEntity.getName();
        description = productEntity.getDescription();
        price = productEntity.getPrice();

        double quantity = 0.0d;

        ProductPurchaseResponseDto productPurchaseResponseDto = new ProductPurchaseResponseDto( id, name, description, price, quantity );

        return productPurchaseResponseDto;
    }

    @Override
    public ProductEntity partialUpdate(ProductPurchaseResponseDto productPurchaseResponseDto, ProductEntity productEntity) {
        if ( productPurchaseResponseDto == null ) {
            return productEntity;
        }

        if ( productPurchaseResponseDto.id() != null ) {
            productEntity.setId( productPurchaseResponseDto.id() );
        }
        if ( productPurchaseResponseDto.name() != null ) {
            productEntity.setName( productPurchaseResponseDto.name() );
        }
        if ( productPurchaseResponseDto.description() != null ) {
            productEntity.setDescription( productPurchaseResponseDto.description() );
        }
        if ( productPurchaseResponseDto.price() != null ) {
            productEntity.setPrice( productPurchaseResponseDto.price() );
        }

        return productEntity;
    }

    @Override
    public ProductEntity toEntity(ProductPurchaseRequestDto productPurchaseRequestDto) {
        if ( productPurchaseRequestDto == null ) {
            return null;
        }

        ProductEntity productEntity = new ProductEntity();

        productEntity.setId( productPurchaseRequestDto.id() );

        return productEntity;
    }

    @Override
    public ProductPurchaseRequestDto toDto2(ProductEntity productEntity) {
        if ( productEntity == null ) {
            return null;
        }

        Integer id = null;

        id = productEntity.getId();

        Double quantity = null;

        ProductPurchaseRequestDto productPurchaseRequestDto = new ProductPurchaseRequestDto( id, quantity );

        return productPurchaseRequestDto;
    }

    @Override
    public ProductEntity partialUpdate(ProductPurchaseRequestDto productPurchaseRequestDto, ProductEntity productEntity) {
        if ( productPurchaseRequestDto == null ) {
            return productEntity;
        }

        if ( productPurchaseRequestDto.id() != null ) {
            productEntity.setId( productPurchaseRequestDto.id() );
        }

        return productEntity;
    }

    @Override
    public ProductPurchaseResponseDto topProductPurchaseResponse(ProductEntity product, Double quantity) {
        if ( product == null && quantity == null ) {
            return null;
        }

        Integer id = null;
        String name = null;
        String description = null;
        BigDecimal price = null;
        if ( product != null ) {
            id = product.getId();
            name = product.getName();
            description = product.getDescription();
            price = product.getPrice();
        }
        double quantity1 = 0.0d;
        if ( quantity != null ) {
            quantity1 = quantity;
        }

        ProductPurchaseResponseDto productPurchaseResponseDto = new ProductPurchaseResponseDto( id, name, description, price, quantity1 );

        return productPurchaseResponseDto;
    }
}
