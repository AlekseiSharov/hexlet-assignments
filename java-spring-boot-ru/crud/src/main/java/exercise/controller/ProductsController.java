package exercise.controller;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream().map(p -> productMapper.map(p))
                .toList();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable long id) {
        return productMapper.map(
                productRepository.findById(id).orElseThrow(
                        () -> new ResourceNotFoundException("Product with id " + id + " not found")
                )
        );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody ProductCreateDTO body) {
        return productMapper.map(
                productRepository.save(
                    productMapper.map(body)
                )
        );
    }

    @PutMapping("/{id}")
    public ProductDTO updateProductById(@RequestBody ProductUpdateDTO updateDTO, @PathVariable long id) {
        var findedProduct = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product with id " + id + " not found")
        );
        productMapper.update(updateDTO, findedProduct);

        return productMapper.map(
                productRepository.save(findedProduct)
        );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById(@PathVariable long id) {
        productRepository.deleteById(id);
    }
}
