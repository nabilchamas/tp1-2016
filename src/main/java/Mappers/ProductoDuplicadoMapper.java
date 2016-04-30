package Mappers;

import JPA.ProductoDuplicadoEntity;

import java.util.List;

/**
 * Created by sortiz on 4/29/16.
 */
public interface ProductoDuplicadoMapper {

    void insertProductoDuplicado(ProductoDuplicadoEntity productoDuplicado);
    ProductoDuplicadoEntity getProductoDuplicadoById(Integer productoDuplicadoId);
    ProductoDuplicadoEntity getProductoByCodProducto(Integer productoDuplicadoId);
    List<ProductoDuplicadoEntity> getAllProductoDuplicados();
    void updateProductoDuplicado(ProductoDuplicadoEntity productoDuplicado);



}
