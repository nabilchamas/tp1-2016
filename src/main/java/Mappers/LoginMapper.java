package Mappers;

import JPA.LoginEntity;

/**
 * Created by nabil on 21/05/16.
 */
public interface LoginMapper {

    LoginEntity getLoginByUsuario(String usuario);
}
