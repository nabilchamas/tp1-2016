package EJB;

import JPA.ClienteEntity;
import JPA.PagoEntity;

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

    @PersistenceContext(name = "NewPersistenceUnit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearPago(Integer clienteId, String monto, String fecha){
        try{
            PagoEntity pago = new PagoEntity();
            ClienteEntity cliente = em.find(ClienteEntity.class, clienteId.longValue());
            pago.setCliente(cliente);
            pago.setMonto(monto);
            pago.setFecha(fecha);

            Integer nuevoSaldo = Integer.parseInt(cliente.getSaldo());
            nuevoSaldo -= Integer.parseInt(monto);
            cliente.setSaldo(nuevoSaldo.toString());

            em.persist(pago);
            em.persist(cliente);
            return pago;
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el pago.";
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List getPagos(){
        return em.createNamedQuery("pago.findAll").getResultList();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object getPago(Integer pagoId){
        try{
            return em.find(PagoEntity.class, pagoId.longValue());
        }catch (Exception e){
            e.printStackTrace();
            return "No se encuentra o no existe el pago.";
        }
    }


    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object deletePago(Integer id){
        try{
            PagoEntity pago = em.find(PagoEntity.class, id.longValue());
            em.remove(pago);
            return "Pago eliminado.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo eliminar o no existe el pago.";
        }
    }
}
