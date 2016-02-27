package Beans;

import java.util.HashMap;

/**
 * Created by nabil on 27/02/16.
 */
public class ProveedorBean {
    private Integer id;
    private String nombre;
    private static Integer idCounter=0;
    public static HashMap<Integer, ProveedorBean> proveedores = new HashMap<Integer, ProveedorBean>();

    public ProveedorBean(String nombre) {
        this.nombre = nombre;
        this.id = this.idCounter++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
