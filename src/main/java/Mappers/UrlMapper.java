package Mappers;

import JPA.UrlEntity;

import java.util.List;

/**
 * Created by nabil on 21/05/16.
 */
public interface UrlMapper {
    public List<UrlEntity> getUrlsByRol(String rol);
}
