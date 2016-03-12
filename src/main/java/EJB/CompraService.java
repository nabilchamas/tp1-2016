package EJB;

import JPA.CompraEntity;
import JPA.DetalleCompraEntity;
import JPA.ProductoEntity;
import JPA.ProveedorEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by nabil on 05/03/16.
 */

@Stateless
public class CompraService {

    @PersistenceContext(name = "NewPersistenceUnit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearCompra(Integer proveedorId, List<Integer> productosId,
                              List<Integer> cantidades){
        try {
            CompraEntity compra = new CompraEntity();
            ProveedorEntity proveedor = em.find(ProveedorEntity.class, proveedorId.longValue());
            compra.setProveedor(proveedor);
            em.persist(compra);
            em.flush();

            List<DetalleCompraEntity> detalles = new ArrayList<DetalleCompraEntity>();
            for(int i=0; i<productosId.size(); i++){
                Integer productoId = productosId.get(i);
                Integer cantidad = cantidades.get(i);
                ProductoEntity producto = em.find(ProductoEntity.class, productoId.longValue());
                DetalleCompraEntity detalle = new DetalleCompraEntity();
                detalle.setProducto(producto);
                detalle.setCantidad(cantidad.toString());
                detalle.setCompra(compra);

                detalles.add(detalle);
                em.persist(detalle);
            }

            compra.setDetalles(detalles);
            em.persist(compra);
            return compra;
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear la compra.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getCompras(){
        Query query = em.createNamedQuery("compra.findAll");
        return query.getResultList();
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getCompra(Integer id){
        try{
            return em.find(ProveedorEntity.class, id.longValue());
        }catch (Exception e){
            e.printStackTrace();
            return "No se encuentra o no existe la compra.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteCompra(Integer id){
        try {
            CompraEntity compra = em.find(CompraEntity.class, id.longValue());
            em.remove(compra);
            return "Compra eliminada.";
        }catch (Exception e){
            e.printStackTrace();
            return "No existe o no se pudo eliminar la compra.";
        }
    }

}
