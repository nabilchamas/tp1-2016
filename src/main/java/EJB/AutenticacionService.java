package EJB;

import JPA.LoginEntity;
import JPA.UrlEntity;
import Mappers.LoginMapper;
import Mappers.MybatisUtils;
import Mappers.UrlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.Stateless;
import java.util.List;

/**
 * Created by nabil on 21/05/16.
 */

@Stateless
public class AutenticacionService {


    public String autenticar(String accessToken,
                             HttpRequest httpRequest){
        try{
            SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
            SqlSession sqlSession = factory.openSession();
            LoginMapper loginMapper = sqlSession.getMapper(LoginMapper.class);
            LoginEntity loginEntity = loginMapper.getLoginByAccessToken(accessToken);

            UrlMapper urlMapper = sqlSession.getMapper(UrlMapper.class);
            List<UrlEntity> urls = urlMapper.getUrlsByRol(loginEntity.getRol());

            sqlSession.close();
            for(UrlEntity urlEntity: urls){
                String path = httpRequest.getUri().getPath();
                String metodo = httpRequest.getHttpMethod();
                if(path.startsWith(urlEntity.getNombre()) &&
                        urlEntity.getMetodo().equals(metodo)){
                    return "si";
                }
            }

            return "No posee los permisos para esta url+metodo.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo autenticar";
        }
    }
}