package EJB;

import JPA.LoginEntity;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.UUID;

/**
 * Created by nabil on 17/05/16.
 */

@Stateless
public class LoginService {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object crearLogin(String usuario, String password){
        try{
            Query query = em.createNamedQuery("login.findByUsuario");
            query.setParameter("usuario", usuario);
            LoginEntity login = (LoginEntity)query.getSingleResult();

            if(login.getPassword().equals(password)) {
//              Que son los UUID?
//              Only after generating 1 billion UUIDs every second for the next 100 years,
//              the probability of creating just one duplicate would be about 50%.
//              Or, to put it another way, the probability of one duplicate would be
//              about 50% if every person on earth owned 600 million UUIDs.

                UUID uuid = UUID.randomUUID();
                login.setAccessToken(uuid.toString());
                return uuid.toString();
            }else{
                return "Password incorrecto.";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo crear el login.";
        }
    }
}
