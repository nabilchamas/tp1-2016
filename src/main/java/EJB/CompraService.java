package EJB;

import JPA.CompraEntity;
import JPA.ProveedorEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by nabil on 05/03/16.
 */

@Stateless
public class CompraService {

    @PersistenceContext(name = "NewPersistenceUnit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearCompra(Integer proveedorId){
        try {
            CompraEntity compra = new CompraEntity();
            ProveedorEntity proveedor = em.find(ProveedorEntity.class, proveedorId.longValue());
            compra.setProveedor(proveedor);
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
}
