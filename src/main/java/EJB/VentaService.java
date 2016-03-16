package EJB;



import JPA.ClienteEntity;
import JPA.DetalleVentaEntity;
import JPA.ProductoEntity;
import JPA.VentaEntity;

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
        if(productosId.size()!=cantidades.size()){
            return "Se necesita igual numero de parametros de productosId y cantidades.";
        }

        try{
            VentaEntity venta = new VentaEntity();
            ClienteEntity cliente = em.find(ClienteEntity.class, clienteId.longValue());
            venta.setCliente(cliente);
            em.persist(venta);
            em.flush();

            Integer nuevoSaldo=Integer.parseInt(cliente.getSaldo());
            List<DetalleVentaEntity> detalles = new ArrayList<DetalleVentaEntity>();
            for(int i=0; i<productosId.size(); i++){
                Integer productoId = productosId.get(i);
                Integer cantidad = cantidades.get(i);
                DetalleVentaEntity detalleVentaEntity = new DetalleVentaEntity();

                ProductoEntity producto = em.find(ProductoEntity.class, productoId.longValue());
                detalleVentaEntity.setProducto(producto);
                detalleVentaEntity.setCantidad(cantidad);
                detalleVentaEntity.setVenta(venta);

                //calcula nuevo saldo
                nuevoSaldo += Integer.parseInt(producto.getPrecio())*cantidad;

                detalles.add(detalleVentaEntity);
                em.persist(detalleVentaEntity);
            }
            cliente.setSaldo(nuevoSaldo.toString());

            venta.setDetalles(detalles);
            em.persist(venta);
            em.persist(cliente);
            return venta;
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear la venta.";
        }
    }


    @GET
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getVentas(){
        Query query = em.createNamedQuery("venta.findAll");
        return query.getResultList();
    }


    @GET
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getVenta(Integer id){
        try {
            return em.find(VentaEntity.class, id.longValue());
        }catch (Exception e){
            e.printStackTrace();
            return "No se encontro o no existe la venta.";
        }
    }


    @DELETE
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteVenta(Integer id){
        try{
            VentaEntity venta = em.find(VentaEntity.class, id.longValue());
            em.remove(venta);
            return "Venta eliminada.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo eliminar o no existe la venta.";
        }
    }


}
