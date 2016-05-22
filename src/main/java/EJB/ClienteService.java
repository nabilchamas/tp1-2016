package EJB;


import Mappers.ClienteMapper;
import Beans.ProductoBean;
import JPA.ClienteEntity;
import Mappers.MybatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.jboss.resteasy.spi.HttpRequest;

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

    @EJB
    private AutenticacionService autenticacionService;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearCliente(String nombre,
                               HttpRequest httpRequest, String accessToken) {
        String respuesta = autenticacionService.autenticar(accessToken, httpRequest);

        if(respuesta.equals("si")) {
            try {
                ClienteEntity cliente = new ClienteEntity();
                cliente.setNombre(nombre);
                cliente.setSaldo("0");
                SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
                SqlSession sqlSession = factory.openSession();
                ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
                clienteMapper.insertCliente(cliente);
                sqlSession.close();
                return cliente;
            } catch (Exception e) {
                e.printStackTrace();
                return "No se pudo crear el cliente.";
            }
        }

        return respuesta;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getClientes(HttpRequest httpRequest, String accessToken) {

        String respuesta = autenticacionService.autenticar(accessToken, httpRequest);

        if(respuesta.equals("si")){
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

        return respuesta;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getCliente(Integer id,
                             HttpRequest httpRequest, String accessToken) {
        String respuesta = autenticacionService.autenticar(accessToken, httpRequest);

        if(respuesta.equals("si")) {
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
        }

        return respuesta;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteCliente(Integer id,
                                HttpRequest httpRequest, String accessToken) {
        String respuesta = autenticacionService.autenticar(accessToken, httpRequest);

        if(respuesta.equals("si")) {
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            clienteMapper.deleteCliente(id);
            sqlSession.close();
            return "Cliente eliminado";
        }

        return respuesta;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateCliente(Integer id, String nombre,
                                HttpRequest httpRequest, String accessToken) {
        String respuesta = autenticacionService.autenticar(accessToken, httpRequest);

        if(respuesta.equals("si")) {
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

        return respuesta;
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
