package EJB;

import JPA.ProductoDuplicadoEntity;
import JPA.ProductoEntity;
import JPA.ProveedorEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;


/**
 * Created by nabil on 04/03/16.
 */

@Stateless
public class ProductoService {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager em;

    @EJB
    ProductoDuplicadoService productoDuplicadoService;


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearProducto(String nombre, String precio, String cantidad,
                                Integer proveedorId){

        try{
            Query query = em.createNamedQuery("producto.findByNombre");
            query.setParameter("nombre", nombre);
            ProductoEntity producto = (ProductoEntity)query.getSingleResult();

            return productoDuplicadoService.crearProductoDuplicado(producto);
        }catch (NoResultException e){
            try{
                ProductoEntity producto = new ProductoEntity();
                producto.setNombre(nombre);
                producto.setPrecio(precio);
                producto.setCantidad(cantidad);

                ProveedorEntity proveedor = em.find(ProveedorEntity.class, proveedorId.longValue());
                producto.setProveedor(proveedor);

                em.persist(producto);
                return producto;
            }catch (Exception exc){
                exc.printStackTrace();
                return "No se pudo crear el producto.";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el producto repetido.";
        }




    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getProductos(){
        Query query = em.createNamedQuery("producto.findAll");
        return query.getResultList();
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getProducto(Integer id){
        try{
            return em.find(ProveedorEntity.class, id.longValue());
        }catch (Exception e){
            e.printStackTrace();
            return "No se encuentro o no existe el producto.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteProducto(Integer id){
        try{
            ProductoEntity producto = em.find(ProductoEntity.class, id.longValue());
            em.remove(producto);
            return "El producto fue eliminado.";
        }catch (Exception e){
            e.printStackTrace();
            return "El producto no existe o no se pudo eliminar.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateProducto(Integer id, String nombre, String precio,
                                 String cantidad, Integer proveedorId){
        try{
            ProductoEntity producto = em.find(ProductoEntity.class, id.longValue());

            if(nombre!=null){
                producto.setNombre(nombre);
            }

            if(precio!=null){
                producto.setPrecio(precio);
            }

            if (cantidad!=null){
                producto.setCantidad(cantidad);
            }

            if(proveedorId!=null){
                ProveedorEntity proveedor = em.find(ProveedorEntity.class,
                        proveedorId.longValue());
                producto.setProveedor(proveedor);
            }

            em.persist(producto);
            return "El producto ha sido actualizado.";
        }catch (Exception e){
            e.printStackTrace();
            return "El producto no existe o no se pudo actualizarlo.";
        }
    }


//    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
//    @Produces("application/json")
//    public Object getProductoByNombre(String nombre){
//        try{
//            Query query = em.createNamedQuery("producto.findByNombre");
//            query.setParameter("nombre", nombre);
//            ProductoEntity producto = (ProductoEntity)query.getSingleResult();
//            return producto;
////            ProductoEntity producto = em.find(ProductoEntity.class, productoId);
////            return producto;
//        }catch (Exception e){
//            e.printStackTrace();
//            return "No se encontro el producto.";
//        }
//    }

}
