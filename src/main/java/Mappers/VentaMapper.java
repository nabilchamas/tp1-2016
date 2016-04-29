package Mappers;

import JPA.VentaEntity;

import java.util.List;

/**
 * Created by sortiz on 4/29/16.
 */
public interface VentaMapper {

    void insertVenta(VentaEntity venta);
    VentaEntity getVentaById(Integer ventaId);
    List<VentaEntity> getAllVentas();
    void updateVenta(VentaEntity venta);
    void deleteVenta(Integer ventaId);
}
