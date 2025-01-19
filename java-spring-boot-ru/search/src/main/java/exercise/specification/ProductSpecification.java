package exercise.specification;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

// BEGIN
@Component
public class ProductSpecification {

    public Specification<Product> build(ProductParamsDTO paramsDTO) {
        return withCategoryId(paramsDTO.getCategoryId())
                    .and(withPriceMin(paramsDTO.getPriceLt())
                    .and(withPriceMax(paramsDTO.getPriceGt()))
                    .and(withRating(paramsDTO.getRatingGt()))
                    .and(withTitle(paramsDTO.getTitleCont()))
                );
    }

    private Specification<Product> withCategoryId(Long categoryId) {
        return (
                root, query, cb
        ) -> categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), categoryId);
    }

    private Specification<Product> withPriceMin(Integer priceMin) {
        return (
                root, query, cb
        ) -> priceMin == null ? cb.conjunction() : cb.greaterThan(root.get("price"), priceMin);
    }

    private Specification<Product> withPriceMax(Integer priceMax) {
        return (
                root, query, cb
        ) -> priceMax == null ? cb.conjunction() : cb.lessThan(root.get("price"), priceMax);
    }

    private Specification<Product> withRating(Double rating) {
        return (
                root, query, cb
        ) -> rating == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), rating);
    }

    private Specification<Product> withTitle(String title) {
        return (
                root, query, cb
        ) -> title == null ? cb.conjunction() : cb.like(root.get("title"), title.toLowerCase());
    }
}
// END
