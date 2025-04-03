package mk.productservice.services;

import mk.productservice.controllers.requests.CreateProductRequestDto;
import mk.productservice.controllers.requests.ProductPurchaseRequestDto;
import mk.productservice.controllers.requests.UpdateProductRequestDto;
import mk.productservice.controllers.responses.ProductPurchaseResponseDto;
import mk.productservice.dtos.ProductDto;

import java.util.List;

public interface IProductService {
    ProductDto createProduct(CreateProductRequestDto product);

    ProductDto updateProduct(UpdateProductRequestDto product);

    ProductDto getProductById(int id);

    List<ProductDto> getAllProducts();

    void deleteProduct(int id);

    boolean isProductExists(int id);

    List<ProductPurchaseResponseDto> purchaseProducts(List<ProductPurchaseRequestDto> request);
}
