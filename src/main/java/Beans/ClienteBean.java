package Beans;

import java.util.HashMap;

/**
 * Created by nabil on 26/02/16.
 */
public class ClienteBean {
    public static HashMap<Integer, ClienteBean> clientes = new HashMap<Integer, ClienteBean>();

    private Integer id;
    private String nombre;
    private Integer saldo;
    private static Integer idCounter=0;

    public ClienteBean(String nombre) {
        this.nombre = nombre;
        this.id = idCounter++;
        this.saldo = 0;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
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
