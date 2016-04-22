package Mappers;

import JPA.ClienteEntity;

import java.util.List;

/**
 * Created by sortiz on 4/21/16.
 */
public interface ClienteMapper {


        void insertCliente(ClienteEntity cliente);
        ClienteEntity getClienteById(Integer clienteId);
        List<ClienteEntity> getAllClientes();
        void updateCLiente(ClienteEntity cliente);
        void deleteCliente(Integer clienteId);

}
