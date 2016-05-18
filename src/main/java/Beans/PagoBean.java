package Beans;

import java.util.HashMap;

/**
 * Created by nabil on 29/02/16.
 */
public class PagoBean {
    public static HashMap<Integer, PagoBean> pagos = new HashMap<Integer, PagoBean>();


    private static Integer idCounter=0;
    private ClienteBean cliente;
    private Integer monto;
    private String fecha;

    public PagoBean(ClienteBean cliente, Integer monto, String fecha) {
        this.cliente = cliente;
        this.monto = monto;
        this.fecha = fecha;
    }




    public ClienteBean getCliente() {
        return cliente;
    }

    public void setCliente(ClienteBean cliente) {
        this.cliente = cliente;
    }

    public Integer getMonto() {
        return monto;
    }

    public void setMonto(Integer monto) {
        this.monto = monto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
