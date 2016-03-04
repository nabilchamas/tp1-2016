package EJB;

import JPA.ProveedorEntity;

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
        try{
            ProveedorEntity proveedor = new ProveedorEntity();
            proveedor.setNombre(nombre);
            em.persist(proveedor);
            return proveedor;
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el proveedor.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getProveedores(){
        Query query = em.createNamedQuery("proveedor.findAll");
        return query.getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getProveedor(Integer id){
        try{
            return em.find(ProveedorEntity.class, id.longValue());
        }catch (Exception e){
            e.printStackTrace();
            return "No se encuentra el proveedor.";
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteProveedor(Integer id){
        try{
            ProveedorEntity proveedor = em.find(ProveedorEntity.class, id.longValue());
            em.remove(proveedor);
            return "Proveedor borrado.";
        }catch (Exception e){
            e.printStackTrace();
            return "El proveedor no existe o no se pudo borrarlo.";
        }
    }



    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateProveedor(Integer id, String nombre){
        try{
            ProveedorEntity proveedor = em.find(ProveedorEntity.class, id.longValue());
            proveedor.setNombre(nombre);
            em.persist(proveedor);
            return "Proveedor actualizado.";
        }catch (Exception e){
            e.printStackTrace();
            return "El proveedor no existe o no se pudo actualizarlo.";
        }
    }
}
