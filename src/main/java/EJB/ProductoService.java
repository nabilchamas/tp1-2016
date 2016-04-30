package EJB;

import Beans.ProductoBean;
import JPA.ProductoDuplicadoEntity;
import JPA.ProductoEntity;
import JPA.ProveedorEntity;
import Mappers.MybatisUtils;
import Mappers.ProductoMapper;
import Mappers.ProveedorMapper;
import REST.Producto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.exception.ConstraintViolationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import java.io.*;
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

        try {
            ProductoEntity producto = new ProductoEntity();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
            ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

            ProveedorEntity proveedor = proveedorMapper.getProveedorById(proveedorId);
            producto.setProveedor(proveedor);
            productoMapper.insertProducto(producto);
            return producto;
        }catch (PersistenceException e){
            Throwable t = e.getCause();

            if(t instanceof ConstraintViolationException) {
                try {
                    return productoDuplicadoService.crearProductoDuplicado(nombre);
                } catch (Exception exc) {
                    exc.printStackTrace();
                    return "No se pudo crear el producto repetido.";
                }
            }

            e.printStackTrace();
            return "No se pudo crear el producto. PersistenceException.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el producto.";
        }




    }


    




    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getProductos(){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        return productoMapper.getAllProductos();
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getProducto(Integer id){

            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
            ProductoEntity producto = productoMapper.getProductoById(id);
            if(producto != null){
                return producto;
            }else{
                return "No existe el producto con el id: " + id.toString();
            }

    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteProducto(Integer id){
        try{
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
            productoMapper.deleteProducto(id);
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
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
            ProductoEntity producto = productoMapper.getProductoById(id);

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
                ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);
                ProveedorEntity proveedor = proveedorMapper.getProveedorById(proveedorId);
                producto.setProveedor(proveedor);
            }

            productoMapper.updateProducto(producto);
            return "El producto ha sido actualizado.";
        }catch (Exception e){
            e.printStackTrace();
            return "El producto no existe o no se pudo actualizarlo.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void crearFileProductos(){
        try{
            File file =new File("/home/sortiz/Desktop/productos");
            if(!file.exists()){
                file.createNewFile();
            }else {
                file.delete();
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file,true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            //JSON
            boolean empieza=true;
            int offset=0;
            List productos;
            ObjectMapper mapper = new ObjectMapper();
            String jsonString;
            pw.println("[");
            productos = getPorcionProductos();
            for (Object producto : productos) {
                    jsonString = mapper.writeValueAsString(producto);
                    if(!empieza) pw.print(",");
                    pw.println(jsonString);
                    empieza=false;
                }

            pw.println("]");

            pw.close();

            System.out.println("Data successfully appended at the end of file");

        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private List getPorcionProductos(){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        return productoMapper.getAllProductos();
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
