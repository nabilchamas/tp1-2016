package EJB;



import Beans.ProductoBean;
import JPA.ClienteEntity;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.core.Context;
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
    public Object crearCliente(String nombre, HttpRequest httpRequest,
                                      String accessToken){
        String mensajeAutenticacion = autenticacionService.autenticar(accessToken, httpRequest);

        if(mensajeAutenticacion.equals("si")){
            try{
                ClienteEntity cliente = new ClienteEntity();
                cliente.setNombre(nombre);
                cliente.setSaldo("0");
                em.persist(cliente);
                return cliente;
            }catch (Exception e){
                e.printStackTrace();
                return "No se pudo crear el cliente.";
            }
        }

        return mensajeAutenticacion;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getClientes(HttpRequest httpRequest,
                            String accessToken){
        String mensajeAutenticacion = autenticacionService.autenticar(accessToken,
                httpRequest);

        if(mensajeAutenticacion.equals("si")){
            Query query = em.createNamedQuery("cliente.findAll");
            return query.getResultList();
        }

        return mensajeAutenticacion;
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getCliente(Integer id, HttpRequest httpRequest,
                             String accessToken){
        String mensajeAutenticacion = autenticacionService.autenticar(accessToken,
                httpRequest);

        if(mensajeAutenticacion.equals("si")) {
            try {
                return em.find(ClienteEntity.class, id.longValue());

            } catch (Exception e) {
                e.printStackTrace();
                return "No existe el cliente.";
            }
        }

        return mensajeAutenticacion;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteCliente(Integer id, HttpRequest httpRequest,
                                String accessToken){
        String mensajeAutenticacion = autenticacionService.autenticar(accessToken,
                httpRequest);

        if(mensajeAutenticacion.equals("si")) {
            try {
                ClienteEntity cliente = em.find(ClienteEntity.class, id.longValue());
                em.remove(cliente);
                return "El cliente fue eliminado.";
            } catch (Exception e) {
                e.printStackTrace();
                return "El cliente no existe o no se pudo eliminar.";
            }
        }

        return mensajeAutenticacion;
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateCliente(Integer id, String nombre, HttpRequest httpRequest,
                                String accessToken){
        String mensajeAutenticacion = autenticacionService.autenticar(accessToken,
                httpRequest);

        if(mensajeAutenticacion.equals("si")) {
            try {
                ClienteEntity cliente = em.find(ClienteEntity.class, id.longValue());
                cliente.setNombre(nombre);
                em.persist(cliente);
                return "Cliente actualizado.";
            } catch (Exception e) {
                e.printStackTrace();
                return "No se pudo actualizar el cliente.";
            }
        }

        return mensajeAutenticacion;
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
