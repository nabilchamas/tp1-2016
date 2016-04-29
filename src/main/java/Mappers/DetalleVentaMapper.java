package Mappers;

import JPA.DetalleVentaEntity;

import java.util.List;

/**
 * Created by sortiz on 4/29/16.
 */
public interface DetalleVentaMapper {

    void insertDetalleVenta(DetalleVentaEntity detalleVenta);
    DetalleVentaEntity getDetalleVentaById(Integer detalleVentaId);
    List<DetalleVentaEntity> getAllDetallesVentas();
    void updateDetalleVenta(DetalleVentaEntity detalleVenta);
    void deleteDetalleVenta(Integer detalleVentaId);
    List<DetalleVentaEntity> getAllDetallesVentasByProductoId(int productoId);
    List<DetalleVentaEntity> getAllDetallesVentasByVentaId(int ventaId);
}
