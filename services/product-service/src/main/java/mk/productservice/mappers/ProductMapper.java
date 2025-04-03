package mk.productservice.mappers;

import jakarta.validation.constraints.Positive;
import mk.productservice.controllers.requests.CreateProductRequestDto;
import mk.productservice.controllers.requests.ProductPurchaseRequestDto;
import mk.productservice.controllers.requests.UpdateProductRequestDto;
import mk.productservice.controllers.responses.ProductPurchaseResponseDto;
import mk.productservice.dtos.ProductDto;
import mk.productservice.entities.ProductEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(CreateProductRequestDto dto);

    ProductEntity toEntity(UpdateProductRequestDto dto);

    ProductDto toDto(ProductEntity entity);

    ProductEntity toEntity(ProductPurchaseResponseDto productPurchaseResponseDto);

    ProductPurchaseResponseDto toDto1(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity partialUpdate(ProductPurchaseResponseDto productPurchaseResponseDto, @MappingTarget ProductEntity productEntity);

    ProductEntity toEntity(ProductPurchaseRequestDto productPurchaseRequestDto);

    ProductPurchaseRequestDto toDto2(ProductEntity productEntity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductEntity partialUpdate(ProductPurchaseRequestDto productPurchaseRequestDto, @MappingTarget ProductEntity productEntity);

    ProductPurchaseResponseDto topProductPurchaseResponse(ProductEntity product, @Positive(message = "Product quantity is mandatory") Double quantity);
}
