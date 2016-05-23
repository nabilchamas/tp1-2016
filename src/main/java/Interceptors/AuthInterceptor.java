package Interceptors;

import JPA.LoginEntity;
import JPA.UrlEntity;
import Mappers.LoginMapper;
import Mappers.MybatisUtils;
import Mappers.UrlMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jboss.resteasy.spi.HttpRequest;

import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.List;

/**
 * Created by nabil on 22/05/16.
 */


public class AuthInterceptor {

    @AroundInvoke
    public Object autenticar(InvocationContext context){
        Object parametros[] = context.getParameters();

        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        try{
            String accessToken = (String)parametros[0];
            HttpRequest httpRequest = (HttpRequest)parametros[1];

            LoginMapper loginMapper = sqlSession.getMapper(LoginMapper.class);
            LoginEntity loginEntity = loginMapper.getLoginByAccessToken(accessToken);

            UrlMapper urlMapper = sqlSession.getMapper(UrlMapper.class);
            List<UrlEntity> urls = urlMapper.getUrlsByRol(loginEntity.getRol());

            for(UrlEntity urlEntity: urls){
                String path = httpRequest.getUri().getPath();
                String metodo = httpRequest.getHttpMethod();
                if(path.startsWith(urlEntity.getNombre()) &&
                        urlEntity.getMetodo().equals(metodo)){
                    return context.proceed();
                }
            }

            return "No posee los permisos para esta url+metodo.";
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo autenticar";
        }finally {
            sqlSession.close();
        }
    }

}
