package EJB;

import JPA.ProductoDuplicadoEntity;
import JPA.ProductoEntity;
import JPA.ProveedorEntity;
import Mappers.MybatisUtils;
import Mappers.ProductoDuplicadoMapper;
import Mappers.ProductoMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by nabil on 12/03/16.
 */

@Stateless
public class ProductoDuplicadoService {



    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearProductoDuplicado(String nombre){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        ProductoEntity producto = productoMapper.getProductoByNombre(nombre);
        ProductoDuplicadoMapper productoDuplicadoMapper = sqlSession.getMapper(ProductoDuplicadoMapper.class);


        try{


            ProductoDuplicadoEntity duplicado = productoDuplicadoMapper.getProductoByCodProducto((int)producto.getId());
            long cantidad = duplicado.getCantidad().longValue();
            cantidad++;
            duplicado.setCantidad(cantidad);
            productoDuplicadoMapper.updateProductoDuplicado(duplicado);
            return duplicado;
        }catch (NoResultException e){
            ProductoDuplicadoEntity duplicado = new ProductoDuplicadoEntity();
            duplicado.setProducto(producto);
            duplicado.setCantidad(Long.parseLong("1"));
           productoDuplicadoMapper.insertProductoDuplicado(duplicado);
            return duplicado;
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el producto en ProductoDuplicado EJB";
        }
    }



}
