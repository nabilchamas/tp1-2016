package EJB;

import JPA.ProveedorEntity;
import Mappers.MybatisUtils;
import Mappers.ProductoMapper;
import Mappers.ProveedorMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.exolab.castor.mapping.xml.Sql;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by nabil on 04/03/16.
 */

@Stateless
public class ProveedorService {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearProveedor(String nombre){

            ProveedorEntity proveedor = new ProveedorEntity();
            proveedor.setNombre(nombre);
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);
            proveedorMapper.insertProveedor(proveedor);
            sqlSession.close();
            return "Proveedor creado";
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getProveedores(){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        List<ProveedorEntity> listadeProveedores = proveedorMapper.getAllProveedores();
        for(ProveedorEntity p : listadeProveedores){
            p.setProductos(productoMapper.getAllProductosByProveedorId((int)p.getId()));
        }
        sqlSession.close();
        return listadeProveedores;

    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getProveedor(Integer id){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        ProveedorEntity proveedorEntity = proveedorMapper.getProveedorById(id);
        proveedorEntity.setProductos(productoMapper.getAllProductosByProveedorId(id));
        sqlSession.close();
        return proveedorEntity;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteProveedor(Integer id){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);
        proveedorMapper.deleteProveedor(id);
        sqlSession.close();
            return "Proveedor borrado.";

    }



    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateProveedor(Integer id, String nombre){

        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

            ProveedorEntity proveedor =  proveedorMapper.getProveedorById(id);
            proveedor.setNombre(nombre);
           proveedorMapper.updateProveedor(proveedor);
        sqlSession.close();
            return "Proveedor actualizado.";

    }
}
