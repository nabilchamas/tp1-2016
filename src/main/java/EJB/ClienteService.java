package EJB;


import Interceptors.AuthInterceptor;
import Mappers.ClienteMapper;
import Beans.ProductoBean;
import JPA.ClienteEntity;
import Mappers.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import java.util.List;

/**
 * Created by nabil on 03/03/16.
 */

@Stateless
public class ClienteService {

    @EJB
    private ProductoService productoService;

    @EJB
    private AutenticacionService autenticacionService;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Interceptors(AuthInterceptor.class)
    public Object crearCliente(String accessToken,
                               HttpRequest httpRequest,
                               String nombre) {
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        try {
            ClienteEntity cliente = new ClienteEntity();
            cliente.setNombre(nombre);
            cliente.setSaldo("0");

            ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            clienteMapper.insertCliente(cliente);

            return cliente;
        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo crear el cliente.";
        }finally {
            sqlSession.close();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Interceptors(AuthInterceptor.class)
    public Object getClientes(String accessToken, HttpRequest httpRequest) {
        try{
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            List<ClienteEntity> clientes = clienteMapper.getAllClientes();
            sqlSession.close();
            return clientes;
        }catch (Exception e){
            e.printStackTrace();
            return "Error al tratar obtener clientes.";
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Interceptors(AuthInterceptor.class)
    public Object getCliente(String accessToken,
                             HttpRequest httpRequest,
                             Integer id) {
        try {
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            ClienteEntity cliente = clienteMapper.getClienteById(id);
            sqlSession.close();
            if (cliente != null) {
                return cliente;
            } else {
                return "No existe el cliente con el id: " + id.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo obtener el cliente.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Interceptors(AuthInterceptor.class)
    public Object deleteCliente(String accessToken,
                                HttpRequest httpRequest,
                                Integer id) {
        try {
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            clienteMapper.deleteCliente(id);
            sqlSession.close();
            return "Cliente eliminado";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo borrar el cliente.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    @Interceptors(AuthInterceptor.class)
    public Object updateCliente(String accessToken,
                                HttpRequest httpRequest,
                                Integer id,
                                String nombre) {
        try {
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            ClienteEntity cliente = clienteMapper.getClienteById(id);
            cliente.setNombre(nombre);
            clienteMapper.updateCliente(cliente);
            sqlSession.close();
            return "Cliente actualizado.";
        } catch (Exception e) {
            e.printStackTrace();
            return "No se pudo actualizar el cliente.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void crearProductos(List<ProductoBean> productosBean) {
        for (ProductoBean productoBean : productosBean) {
            Object o = productoService.crearProducto(productoBean.getNombre(),
                    productoBean.getPrecio(),
                    productoBean.getCantidad(),
                    productoBean.getProveedorId());
        }
    }
}
