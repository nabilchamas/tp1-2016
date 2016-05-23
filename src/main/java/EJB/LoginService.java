package EJB;

import JPA.LoginEntity;
import Mappers.LoginMapper;
import Mappers.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.ejb.Stateless;

/**
 * Created by nabil on 21/05/16.
 */

@Stateless
public class LoginService {

    public Object getLogin(String usuario, String password) {
        SqlSessionFactory factory = MybatisUtils.getSqlSessionFactory();
        SqlSession sqlSession = factory.openSession();

        try {
            LoginMapper loginMapper = sqlSession.getMapper(LoginMapper.class);
            LoginEntity loginEntity = loginMapper.getLoginByUsuario(usuario);

            if (loginEntity.getPassword().equals(password)) {
                return loginEntity.getAccessToken();
            } else {
                return "Password incorrecto.";
            }
        }catch (Exception e){
            e.printStackTrace();
            return "No se pudo realizar el login.";
        }finally {
            sqlSession.close();
        }
    }
}
