package mk.productservice.services.impls;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mk.productservice.exceptions.ProductPurchaseException;
import mk.productservice.controllers.requests.CreateProductRequestDto;
import mk.productservice.controllers.requests.ProductPurchaseRequestDto;
import mk.productservice.controllers.requests.UpdateProductRequestDto;
import mk.productservice.controllers.responses.ProductPurchaseResponseDto;
import mk.productservice.dtos.ProductDto;
import mk.productservice.entities.ProductEntity;
import mk.productservice.mappers.ProductMapper;
import mk.productservice.repositories.ProductRepository;
import mk.productservice.services.IProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j(topic = "PRODUCT_SERVICE")
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductDto createProduct(CreateProductRequestDto product) {
        ProductEntity newProduct = this.productMapper.toEntity(product);
        ProductEntity productSaved = this.productRepository.save(newProduct);
        ProductDto productMapToDto = this.productMapper.toDto(productSaved);
        log.info("Product created: {}", newProduct);
        return productMapToDto;
    }

    @Override
    public ProductDto updateProduct(UpdateProductRequestDto product) {
        boolean isProductExists = this.isProductExists(product.getId());
        if (isProductExists) {
            ProductEntity productEntity = this.productMapper.toEntity(product);
            ProductEntity productSaved = this.productRepository.save(productEntity);
            ProductDto productMapToDto = this.productMapper.toDto(productSaved);
            log.info("Product updated: {}", productMapToDto);
            return productMapToDto;
        }
        throw new RuntimeException("Product not found with id: " + product.getId());
    }

    @Override
    public ProductDto getProductById(int id) {
        var productEntity = this.productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
        ProductDto productDto = this.productMapper.toDto(productEntity);
        log.info("Product found: {}", productDto);
        return productDto;
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<ProductEntity> productEntities = this.productRepository.findAll();
        List<ProductDto> productDTOs = productEntities.stream()
                .map(this.productMapper::toDto)
                .toList();
        log.info("All products found: {}", productDTOs);
        return productDTOs;
    }

    @Override
    public void deleteProduct(int id) {
        this.productRepository.deleteById(id);
        log.info("Product deleted with id: {}", id);
    }

    @Override
    public boolean isProductExists(int id) {
        boolean isExist = this.productRepository.existsById(id);
        log.info("Product exists with id: {} - {}", id, isExist);
        return isExist;
    }

    @Override
    public List<ProductPurchaseResponseDto> purchaseProducts(List<ProductPurchaseRequestDto> request) {
        var productIds = request.stream()
                .map(ProductPurchaseRequestDto::id)
                .toList();
        var storedProducts = this.productRepository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("One or more products does not exist");
        }
        var sortedRequest = request.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequestDto::id))
                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponseDto>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("Insufficient stock quantity for product with ID: " + productRequest.id());
            }
            product.setAvailableQuantity((int) (product.getAvailableQuantity() - productRequest.quantity()));
            purchasedProducts.add(this.productMapper.topProductPurchaseResponse(product, productRequest.quantity()));
        }
        this.productRepository.saveAll(storedProducts);
        log.info("Products purchased: {}", purchasedProducts);
        return purchasedProducts;
    }
}
