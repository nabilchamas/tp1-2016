package EJB;


import JPA.ProductoEntity;
import JPA.ProveedorEntity;
import Mappers.MybatisUtils;
import Mappers.ProductoMapper;
import Mappers.ProveedorMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.postgresql.util.PSQLException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.*;
import java.io.*;
import java.nio.charset.Charset;
import java.util.List;


/**
 * Created by nabil on 04/03/16.
 */

@Stateless
public class ProductoService {



    @EJB
    ProductoDuplicadoService productoDuplicadoService;

    private static final String FILE_NAME = "/tmp/producto";


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearProducto(String nombre, String precio, String cantidad,
                                Integer proveedorId){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        try {
            ProductoEntity producto = new ProductoEntity();
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setCantidad(cantidad);

            ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
            ProveedorMapper proveedorMapper = sqlSession.getMapper(ProveedorMapper.class);

            ProveedorEntity proveedor = proveedorMapper.getProveedorById(proveedorId);
            producto.setProveedor(proveedor);
            productoMapper.insertProducto(producto);
            return producto;
        }catch (org.apache.ibatis.exceptions.PersistenceException e){
            return productoDuplicadoService.crearProductoDuplicado(nombre);
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el producto.";
        }finally {
            sqlSession.close();
        }




    }


    




    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getProductos(){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        sqlSession.close();
        return productoMapper.getAllProductosByProveedorId(1);
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getProducto(Integer id){

            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
            ProductoEntity producto = productoMapper.getProductoById(id);
            sqlSession.close();
            if(producto != null){
                return producto;
            }else{
                return "No existe el producto con el id: " + id.toString();
            }

    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deleteProducto(Integer id){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        try{

            ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
            productoMapper.deleteProducto(id);
            return "El producto fue eliminado.";
        }catch (Exception e){
            e.printStackTrace();
            return "El producto no existe o no se pudo eliminar.";
        }finally {
            sqlSession.close();
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object updateProducto(Integer id, String nombre, String precio,
                                 String cantidad, Integer proveedorId){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        try{

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
        }finally {
            sqlSession.close();
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void crearFileProductos(){
        try{

            File file =new File(FILE_NAME);
            if(!file.exists()){
                file.createNewFile();
            }else {
                file.delete();
                file.createNewFile();
            }
            Writer w = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter pw = new PrintWriter(bw);

            //JSON
            boolean empieza=true;
            int offset=0, total = getTotalProductos();

            RowBounds rowBounds = new RowBounds(offset,100);
            List productos;
            ObjectMapper mapper = new ObjectMapper();
            String jsonString;
            pw.println("[");
            while(rowBounds.getOffset() != total) {
                productos = getPorcionProductos(rowBounds);
                for (Object producto : productos) {
                    jsonString = mapper.writeValueAsString(producto);
                    if(!empieza) pw.print(",");
                    pw.println(jsonString);
                    empieza=false;
                }
                offset += productos.size();
                rowBounds = new RowBounds(offset,100);
            }
            pw.println("]");
            w.close();
            pw.close();

            System.out.println("Data successfully appended at the end of file");

        }catch(IOException ioe){
            System.out.println("Exception occurred:");
            ioe.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    private List getPorcionProductos(RowBounds rowBounds){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        return productoMapper.getAllProductos(rowBounds);
    }

    private int getTotalProductos(){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        ProductoMapper productoMapper = sqlSession.getMapper(ProductoMapper.class);
        return productoMapper.getCantidadTotalProductos();
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
