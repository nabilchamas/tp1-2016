package Mappers;

import JPA.PagoEntity;

import java.util.List;

/**
 * Created by sortiz on 4/29/16.
 */
public interface PagoMapper {

    void insertPago(PagoEntity pago);
    PagoEntity getPagoById(Integer pagoId);
    List<PagoEntity> getAllPagos();
    void deletePago(Integer pagoId);
}
