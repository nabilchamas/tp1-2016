package Mappers;

import JPA.ProveedorEntity;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by sortiz on 4/21/16.
 */
public interface ProveedorMapper {

    void insertProveedor(ProveedorEntity proveedor);
    ProveedorEntity getProveedorById(Integer proveedorId);
    List<ProveedorEntity> getAllProveedores();
    void updateProveedor(ProveedorEntity proveedor);
    void deleteProveedor(Integer proveedorId);
}
