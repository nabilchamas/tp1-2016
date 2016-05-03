package EJB;

import JPA.ClienteEntity;
import JPA.PagoEntity;
import Mappers.ClienteMapper;
import Mappers.MybatisUtils;
import Mappers.PagoMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.management.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by nabil on 15/03/16.
 */

@Stateless
public class PagoService {



    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearPago(Integer clienteId, String monto, String fecha){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        try{
            PagoEntity pago = new PagoEntity();
           ClienteMapper clienteMapper = sqlSession.getMapper(ClienteMapper.class);
            ClienteEntity cliente = clienteMapper.getClienteById(clienteId);
            pago.setCliente(cliente);
            pago.setMonto(monto);
            pago.setFecha(fecha);

            Integer nuevoSaldo = Integer.parseInt(cliente.getSaldo());
            nuevoSaldo -= Integer.parseInt(monto);
            cliente.setSaldo(nuevoSaldo.toString());

           PagoMapper pagoMapper = sqlSession.getMapper(PagoMapper.class);
            pagoMapper.insertPago(pago);
            clienteMapper.updateCliente(cliente);
            return pago;
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el pago.";
        }finally {
            sqlSession.close();
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getPagos(){
        PagoMapper pagoMapper = iniciarPagoMapper();
        return pagoMapper.getAllPagos();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getPago(Integer pagoId){
        try{
            PagoMapper pagoMapper = iniciarPagoMapper();
            return pagoMapper.getPagoById(pagoId);
        }catch (Exception e){
            e.printStackTrace();
            return "No se encuentra o no existe el pago.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deletePago(Integer id){
        try{
            PagoMapper pagoMapper = iniciarPagoMapper();
            pagoMapper.deletePago(id);
            return "Pago eliminado.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo eliminar o no existe el pago.";
        }
    }

    private PagoMapper iniciarPagoMapper(){
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();
        return sqlSession.getMapper(PagoMapper.class);
    }
}
