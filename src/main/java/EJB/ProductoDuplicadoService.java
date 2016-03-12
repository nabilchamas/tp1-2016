package EJB;

import JPA.ProductoDuplicadoEntity;
import JPA.ProductoEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Created by nabil on 12/03/16.
 */

@Stateless
public class ProductoDuplicadoService {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearProductoDuplicado(ProductoEntity producto){
        try{
            Query query = em.createNamedQuery("productoDuplicado.findByProducto");
            query.setParameter("producto", producto);
            ProductoDuplicadoEntity duplicado = (ProductoDuplicadoEntity)query.getSingleResult();
            long cantidad = duplicado.getCantidad().longValue();
            cantidad++;
            duplicado.setCantidad(cantidad);
            em.persist(duplicado);
            return duplicado;
        }catch (NoResultException e){
            ProductoDuplicadoEntity duplicado = new ProductoDuplicadoEntity();
            duplicado.setProducto(producto);
            duplicado.setCantidad(Long.parseLong("1"));
            em.persist(duplicado);
            return duplicado;
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el producto en ProductoDuplicado EJB";
        }
    }
}
