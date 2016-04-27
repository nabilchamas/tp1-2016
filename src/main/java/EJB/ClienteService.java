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
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
        return clienteMapper.getClienteById(id);
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteCliente(Integer id){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
        clienteMapper.deleteCliente(id);
        return "Cliente eliminado";
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateCliente(Integer id, String nombre){

            ClienteEntity cliente = em.find(ClienteEntity.class, id.longValue());
            cliente.setNombre(nombre);
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            clienteMapper.updateCLiente(cliente);

            return "Cliente actualizado.";

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
