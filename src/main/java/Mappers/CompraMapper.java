package Mappers;

import JPA.CompraEntity;

import java.util.List;

/**
 * Created by sortiz on 4/29/16.
 */
public interface CompraMapper {

    void insertCompra(CompraEntity compra);
    CompraEntity getCompraById(Integer compraId);
    List<CompraEntity> getAllCompras();
    void updateCompra(CompraEntity compra);
    void deleteCompra(Integer compraId);
}
