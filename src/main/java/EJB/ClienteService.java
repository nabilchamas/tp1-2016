package EJB;



import Mappers.ClienteMapper;
import Beans.ProductoBean;
import JPA.ClienteEntity;
import Mappers.MybatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;

/**
 * Created by nabil on 03/03/16.
 */

@Stateless
public class ClienteService {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager em;



    @EJB
    private ProductoService productoService;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public ClienteEntity crearCliente(String nombre){
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(nombre);
        cliente.setSaldo("0");
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
        clienteMapper.insertCliente(cliente);
        return cliente;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getClientes() {
       SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            return clienteMapper.getAllClientes();




    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getCliente(Integer id){
        try{
            return em.find(ClienteEntity.class, id.longValue());

        }catch (Exception e){
            e.printStackTrace();
            return "No existe el cliente.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteCliente(Integer id){
        try{
            ClienteEntity cliente = em.find(ClienteEntity.class, id.longValue());
            em.remove(cliente);
            return "El cliente fue eliminado.";
        }catch (Exception e){
            e.printStackTrace();
            return "El cliente no existe o no se pudo eliminar.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateCliente(Integer id, String nombre){
        try{
            ClienteEntity cliente = em.find(ClienteEntity.class, id.longValue());
            cliente.setNombre(nombre);
            em.persist(cliente);
            return "Cliente actualizado.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo actualizar el cliente.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void crearProductos(List<ProductoBean> productosBean){
        for(ProductoBean productoBean: productosBean){
            Object o = productoService.crearProducto(productoBean.getNombre(),
                    productoBean.getPrecio(),
                    productoBean.getCantidad(),
                    productoBean.getProveedorId());
        }
    }
}
