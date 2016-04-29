package Mappers;

import JPA.DetalleCompraEntity;

import java.util.List;

/**
 * Created by sortiz on 4/29/16.
 */
public interface DetalleCompraMapper {

    void insertDetalleCompra(DetalleCompraEntity detalleCompra);
    DetalleCompraEntity getDetalleCompraById(Integer detalleCompraId);
    List<DetalleCompraEntity> getAllDetallesCompras();
    void updateDetalleCompra(DetalleCompraEntity detalleCompra);
    void deleteDetalleCompra(Integer detalleCompraId);
    List<DetalleCompraEntity> getAllDetallesComprasByProductoId(int productoId);
    List<DetalleCompraEntity> getAllDetallesComprasByCompraId(int compraId);


}
