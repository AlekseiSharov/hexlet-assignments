package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<Product> getProducts(
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max
    ) {
        if (min != null && max != null) {
            return productRepository.findProductByPriceBetweenOrderByPriceAsc(min, max);
        } else if (min != null) {
            return productRepository.findProductByPriceAfterOrderByPriceAsc(min);
        } else if (max != null) {
            return productRepository.findProductByPriceBeforeOrderByPriceAsc(max);
        } else {
            return productRepository.findAll();
        }
    }

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
