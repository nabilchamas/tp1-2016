package EJB;


import JPA.ClienteEntity;
import JPA.DetalleVentaEntity;
import JPA.ProductoEntity;
import JPA.VentaEntity;
import Mappers.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import Interceptors.AuthInterceptor;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabil on 05/03/16.
 */

@Stateless
@Interceptors(AuthInterceptor.class)
public class VentaService {


    @POST
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Object crearVenta(String accessToken,
                             HttpRequest httpRequest,
                             Integer clienteId, List<Integer> productosId,
                             List<Integer> cantidades) throws Exception {
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
        VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        DetalleVentaMapper detalleventaMapper = sqlSession.getMapper(DetalleVentaMapper.class);
        if (productosId.size() != cantidades.size()) {
            return "Se necesita igual numero de parametros de productosId y cantidades.";
        }

        try {
            VentaEntity venta = new VentaEntity();
            ClienteEntity cliente = clienteMapper.getClienteById(clienteId);
            venta.setCliente(cliente);
            ventaMapper.insertVenta(venta);

            //em.flush();

            Integer nuevoSaldo = Integer.parseInt(cliente.getSaldo());
            List<DetalleVentaEntity> detalles = new ArrayList<DetalleVentaEntity>();
            for (int i = 0; i < productosId.size(); i++) {
                Integer productoId = productosId.get(i);
                Integer cantidad = cantidades.get(i), nuevaCantidad = 0;
                DetalleVentaEntity detalleVentaEntity = new DetalleVentaEntity();

                ProductoEntity producto = productoMapper.getProductoById(productoId);
                detalleVentaEntity.setProducto(producto);
                detalleVentaEntity.setCantidad(cantidad);
                detalleVentaEntity.setVenta(venta);

                //calcula nuevo saldo
                nuevoSaldo += (int) Float.parseFloat(producto.getPrecio()) * cantidad;
                //calcula nuevo stock
                if (Integer.parseInt(producto.getCantidad()) > cantidad) {
                    nuevaCantidad = Integer.parseInt(producto.getCantidad()) - cantidad;
                    producto.setCantidad(nuevaCantidad.toString());
                    productoMapper.updateProducto(producto);
                } else {
                    throw new Exception("Cantidad Supera a stock");
                }

                detalles.add(detalleVentaEntity);
                detalleventaMapper.insertDetalleVenta(detalleVentaEntity);
            }
            cliente.setSaldo(nuevoSaldo.toString());

            venta.setDetalles(detalles);
            ventaMapper.updateVenta(venta);
            clienteMapper.updateCliente(cliente);
            return "Venta realizada exitosamente";

        } finally {
            sqlSession.close();
        }
    }


    @GET
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getVentas(String accessToken,
                          HttpRequest httpRequest) {
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
        List<VentaEntity> ventas = ventaMapper.getAllVentas();
        sqlSession.close();
        return ventas;
    }


    @GET
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getVenta(String accessToken,
                           HttpRequest httpRequest, Integer id) {
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
        DetalleVentaMapper detalleventaMapper = sqlSession.getMapper(DetalleVentaMapper.class);
        try {
            VentaEntity venta = ventaMapper.getVentaById(id);
            venta.setDetalles(detalleventaMapper.getAllDetallesVentasByVentaId(id));
            return venta;
        } catch (Exception e) {
            e.printStackTrace();
            return "No se encontro o no existe la venta.";
        } finally {
            sqlSession.close();
        }
    }


    @DELETE
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteVenta(String accessToken,
                              HttpRequest httpRequest,
                              Integer id) {
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        try {
            DetalleVentaMapper detalleVentaMapper = sqlSession.getMapper(DetalleVentaMapper.class);
            VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
            detalleVentaMapper.deleteDetalleVenta(id);
            ventaMapper.deleteVenta(id);
            return "Venta eliminada.";
        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo eliminar o no existe la venta.";
        } finally {
            sqlSession.close();
        }
    }


}
