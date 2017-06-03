package repository;

import model.ProductsOnWarehouses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductOnWarehousesRepository extends JpaRepository<ProductsOnWarehouses, Long> {
    ProductsOnWarehouses findOneByProductId(long id);
    ProductsOnWarehouses findOneByProductIdAndWarehouseId(long productId, long warehouseId);
    Page<ProductsOnWarehouses> findAllByWarehouseId(long warehouseId, Pageable pageable);
}
