package Mappers;

import JPA.ProductoEntity;

import java.util.List;

/**
 * Created by sortiz on 4/21/16.
 */
public interface ProductoMapper {

    void insertProducto(ProductoEntity producto);
    ProductoEntity getProductoById(Integer productoId);
    List<ProductoEntity> getAllProductos();
    void updateProducto(ProductoEntity producto);
    void deleteProducto(Integer productoId);
    List <ProductoEntity> getAllProductosByProveedorId(int proveedorId);
}
