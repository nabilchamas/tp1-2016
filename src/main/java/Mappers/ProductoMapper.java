package Mappers;

import JPA.ProductoEntity;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

/**
 * Created by sortiz on 4/21/16.
 */
public interface ProductoMapper {

    void insertProducto(ProductoEntity producto);
    ProductoEntity getProductoById(Integer productoId);
    ProductoEntity getProductoByNombre(String productoNombre);
    List<ProductoEntity> getAllProductos(RowBounds rowBounds);
    int getCantidadTotalProductos();
    void updateProducto(ProductoEntity producto);
    void deleteProducto(Integer productoId);
    List <ProductoEntity> getAllProductosByProveedorId(int proveedorId);
}
