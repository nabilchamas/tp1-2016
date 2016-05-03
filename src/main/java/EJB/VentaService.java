package EJB;



import JPA.ClienteEntity;
import JPA.DetalleVentaEntity;
import JPA.ProductoEntity;
import JPA.VentaEntity;
import Mappers.*;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nabil on 05/03/16.
 */

@Stateless
public class VentaService {

    @PersistenceContext(name = "NewPersistenceUnit")
    EntityManager em;


    @POST
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearVenta(Integer clienteId, List<Integer> productosId,
                             List<Integer> cantidades){

        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
        VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        DetalleVentaMapper detalleventaMapper = sqlSession.getMapper(DetalleVentaMapper.class);
        if(productosId.size()!=cantidades.size()){
            return "Se necesita igual numero de parametros de productosId y cantidades.";
        }

        try{
            VentaEntity venta = new VentaEntity();
            ClienteEntity cliente = clienteMapper.getClienteById(clienteId);
            venta.setCliente(cliente);
            ventaMapper.insertVenta(venta);
            //em.flush();

            Integer nuevoSaldo=Integer.parseInt(cliente.getSaldo());
            List<DetalleVentaEntity> detalles = new ArrayList<DetalleVentaEntity>();
            for(int i=0; i<productosId.size(); i++){
                Integer productoId = productosId.get(i);
                Integer cantidad = cantidades.get(i);
                DetalleVentaEntity detalleVentaEntity = new DetalleVentaEntity();

                ProductoEntity producto = productoMapper.getProductoById(productoId);
                detalleVentaEntity.setProducto(producto);
                detalleVentaEntity.setCantidad(cantidad);
                detalleVentaEntity.setVenta(venta);

                //calcula nuevo saldo
                nuevoSaldo += Integer.parseInt(producto.getPrecio())*cantidad;

                detalles.add(detalleVentaEntity);
                detalleventaMapper.insertDetalleVenta(detalleVentaEntity);
            }
            cliente.setSaldo(nuevoSaldo.toString());

            venta.setDetalles(detalles);
            ventaMapper.updateVenta(venta);
            clienteMapper.updateCliente(cliente);
            return venta;
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear la venta.";
        }finally {
            sqlSession.close();
        }
    }


    @GET
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getVentas(){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        //ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
        VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
        //ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        //DetalleVentaMapper detalleventaMapper = sqlSession.getMapper(DetalleVentaMapper.class);
        List<VentaEntity> ventas = ventaMapper.getAllVentas();
        sqlSession.close();
        return ventas;
    }


    @GET
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getVenta(Integer id){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        //ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
        VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
        //ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        //DetalleVentaMapper detalleventaMapper = sqlSession.getMapper(DetalleVentaMapper.class);
        try {
            VentaEntity venta = ventaMapper.getVentaById(id);
            return venta;
        }catch (Exception e){
            e.printStackTrace();
            return "No se encontro o no existe la venta.";
        }finally {
            sqlSession.close();
        }
    }


    @DELETE
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteVenta(Integer id){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        try{

            VentaMapper ventaMapper = sqlSession.getMapper(VentaMapper.class);
            ventaMapper.deleteVenta(id);
            return "Venta eliminada.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo eliminar o no existe la venta.";
        }finally {
            sqlSession.close();
        }
    }


}
