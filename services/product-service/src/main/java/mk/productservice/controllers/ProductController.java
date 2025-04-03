package mk.productservice.controllers;

import lombok.RequiredArgsConstructor;
import mk.productservice.controllers.requests.CreateProductRequestDto;
import mk.productservice.controllers.requests.ProductPurchaseRequestDto;
import mk.productservice.controllers.requests.UpdateProductRequestDto;
import mk.productservice.controllers.responses.ProductPurchaseResponseDto;
import mk.productservice.dtos.ProductDto;
import mk.productservice.services.IProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final IProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductRequestDto dto) {
        ProductDto product = this.productService.createProduct(dto);
        return ResponseEntity.ok(product);
    }

    @PutMapping
    public ResponseEntity<ProductDto> updateProduct(@RequestBody UpdateProductRequestDto dto) {
        ProductDto product = this.productService.updateProduct(dto);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable int id) {
        ProductDto product = this.productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = this.productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponseDto>> purchaseProducts(
            @RequestBody List<ProductPurchaseRequestDto> request
    ) {
        return ResponseEntity.ok(this.productService.purchaseProducts(request));
    }
}
