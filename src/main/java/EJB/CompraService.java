package EJB;

import JPA.CompraEntity;
import JPA.DetalleCompraEntity;
import JPA.ProductoEntity;
import JPA.ProveedorEntity;
import Mappers.*;
import REST.Producto;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nabil on 05/03/16.
 */

@Stateless
public class CompraService {



    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public Object crearCompra(Integer proveedorId, List<Integer> productosId,
                              List<Integer> cantidades) throws Exception{
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);
        CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        DetalleCompraMapper detalleCompraMapper = sqlSession.getMapper(DetalleCompraMapper.class);

        if(productosId.size()!=cantidades.size()){
            return "Se necesita igual numero de parametros de productosId y cantidades.";
        }

        try {

            CompraEntity compra = new CompraEntity();
            ProveedorEntity proveedor = proveedorMapper.getProveedorById(proveedorId);
            compra.setProveedor(proveedor);
            compraMapper.insertCompra(compra);

            List<DetalleCompraEntity> detalles = new ArrayList<DetalleCompraEntity>();
            for(int i=0; i<productosId.size(); i++){
                Integer productoId = productosId.get(i);
                Integer cantidad = cantidades.get(i), cantidadTotal= 0;
                ProductoEntity producto = productoMapper.getProductoById(productoId);
                DetalleCompraEntity detalle = new DetalleCompraEntity();
                detalle.setProducto(producto);
                detalle.setCantidad(cantidad.toString());
                detalle.setCompra(compra);
                cantidadTotal = Integer.parseInt(producto.getCantidad()) + cantidad;
                producto.setCantidad(cantidadTotal.toString());
                productoMapper.updateProducto(producto);

                detalles.add(detalle);
                detalleCompraMapper.insertDetalleCompra(detalle);

            }

            compra.setDetalles(detalles);
            compraMapper.updateCompra(compra);
            return "Se realizo la compra";
        }finally {
            sqlSession.close();
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getCompras(){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);

        List<CompraEntity> compraEntities = compraMapper.getAllCompras();

        sqlSession.close();
        return compraEntities;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getCompra(Integer id){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);
        DetalleCompraMapper detalleCompraMapper = sqlSession.getMapper(DetalleCompraMapper.class);
        try{
            CompraEntity compraEntity = compraMapper.getCompraById(id);
            compraEntity.setDetalles(detalleCompraMapper.getAllDetallesComprasByCompraId(id));
            return compraEntity;
        }catch (Exception e){
            e.printStackTrace();
            return "No se encuentra o no existe la compra.";
        }finally {
            sqlSession.close();
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteCompra(Integer id){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        try {
            ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
            ProductoEntity productoEntity = null;
            CompraMapper compraMapper = sqlSession.getMapper(CompraMapper.class);
            DetalleCompraMapper detalleCompraMapper = sqlSession.getMapper(DetalleCompraMapper.class);
            List<DetalleCompraEntity> detalles = detalleCompraMapper.getAllDetallesComprasByCompraId(id);
            for(DetalleCompraEntity detalle : detalles){
                productoEntity = productoMapper.getProductoById((int)detalle.getProducto().getId());
                Integer cantidad = Integer.parseInt(productoEntity.getCantidad())-Integer.parseInt(detalle.getCantidad());
                productoEntity.setCantidad(cantidad.toString());
                productoMapper.updateProducto(productoEntity);
            }
            detalleCompraMapper.deleteDetalleCompra(id);
            compraMapper.deleteCompra(id);
            return "Compra eliminada.";
        }catch (Exception e){
            e.printStackTrace();
            return "No existe o no se pudo eliminar la compra.";
        }finally {
            sqlSession.close();
        }
    }

}
