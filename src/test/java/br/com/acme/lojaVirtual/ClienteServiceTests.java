package br.com.acme.lojaVirtual;

import br.com.acme.lojaVirtual.exception.ResourceNotFoundException;
import br.com.acme.lojaVirtual.model.Cliente;
import br.com.acme.lojaVirtual.service.ClienteService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ClienteServiceTests {
    @Autowired
    ClienteService clienteService;

    @Test
    @DisplayName("Deve retornar todos os clientes")
    void deveRetornarTodosOsClientes(){
       List<Cliente> clientes =  clienteService.getAll();
       assertEquals(202, clientes.size());
    }
    @Test
    @DisplayName("Deve retornar um cliente pelo ID")
    void deveRetornarUmClientePeloId(){
        Cliente cliente = clienteService.getById(1L);
        assertEquals(cliente.getNome(), "Joao");
        assertEquals(cliente.getId(), 1L);
        assertEquals(cliente.getCpf(), "12496329874");

        assertThrows(ResourceNotFoundException.class, ()->{
            clienteService.getById(-1);
        });
    }
    @Test
    @DisplayName("Deve remover um cliente pelo ID")
    void testaRemove(){
        clienteService.deleteById(1L);
        List<Cliente> clientes = clienteService.getAll();
        assertEquals(2, clientes.size());
    }
    @Test
    @DisplayName("Deve ataualizar um valor no Map")
    void testUpdate(){
        Cliente leonardo = new Cliente(1L, "Leonardo", "12496329874");
        clienteService.update(1L, leonardo);
        Cliente atualizado = clienteService.getById(1L);
        assertEquals("Leonardo", atualizado.getNome());
    }
    @Test
    @DisplayName("Deve Inserir um cliente")
    void testaInsere(){
        Cliente leonardo = Cliente.builder()
                .nome("Leonardo")
                .cpf("11122288875")
                .build();
        Cliente zezinho = Cliente.builder()
                .nome("Zezinho")
                .cpf("11122288875")
                .build();
        clienteService.create(leonardo);

        List<Cliente> all = clienteService.getAll();
        Cliente retornado = clienteService.getById(4);
        assertEquals(4, all.size());
        assertEquals("Leonardo", retornado.getNome());
        assertEquals(4L, retornado.getId());
        clienteService.create(zezinho);

        all = clienteService.getAll();
        retornado = clienteService.getById(5);
        assertEquals(5, all.size());
        assertEquals("Zezinho", retornado.getNome());
        assertEquals(5L, retornado.getId());

    }
    @Test
    public void testaNome(){
        BigDecimal bigInteger = new BigDecimal("50");
        BigDecimal bigInteger2 = new BigDecimal("202");
        int valor = 202 / 50; // =4
        double valorD = 202.0 / 50.0 ; //= 4.2
        int ceil = (int )Math.ceil(202.0/50.0);

        System.out.println(ceil);

        BigDecimal divide = bigInteger2.divide(bigInteger, RoundingMode.CEILING);
        System.out.println(divide);


    }
    @Test
    public void filtraPorNome(){
        List<Cliente> filtrados = clienteService.filterByName("Leo", 1);
        assertEquals(0,filtrados.size());

    }

}
