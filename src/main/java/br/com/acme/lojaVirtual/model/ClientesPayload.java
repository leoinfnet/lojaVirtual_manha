package br.com.acme.lojaVirtual.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data@AllArgsConstructor@NoArgsConstructor
public class ClientesPayload {
    private List<Cliente> clientes;
    private InformacoesPayload info;
}
