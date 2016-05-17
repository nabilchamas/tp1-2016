package EJB;

import JPA.LoginEntity;
import JPA.UrlEntity;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by nabil on 17/05/16.
 */

@Stateless
public class AutenticacionService {

    @PersistenceContext(unitName = "NewPersistenceUnit")
    private EntityManager em;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public String autenticar(String accessToken,
                             HttpRequest httpRequest){
        try {
            Query query = em.createNamedQuery("login.findByAccessToken");
            query.setParameter("accessToken", accessToken);
            LoginEntity login = (LoginEntity) query.getSingleResult();
            String rol = login.getRol();
            //        String rol = "desarrollador";

            query = em.createNamedQuery("url.findByRol");
            query.setParameter("rol", rol);
            List<UrlEntity> urls = query.getResultList();

            for (UrlEntity url : urls) {
                String path = httpRequest.getUri().getPath();
                String method = httpRequest.getHttpMethod();

                if (path.startsWith(url.getNombre()) && url.getMetodo().equals(method)) {
                    return "si";
                }
            }

            return "No posee permisos.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo autenticar.";
        }
    }
}
