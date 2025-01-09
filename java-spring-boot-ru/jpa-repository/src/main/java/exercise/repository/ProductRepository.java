package exercise.repository;

import exercise.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findProductByPriceBetweenOrderByPriceAsc(int priceAfter, int priceBefore);

    List<Product> findProductByPriceBeforeOrderByPriceAsc(Integer max);

    List<Product> findProductByPriceAfterOrderByPriceAsc(Integer min);
}