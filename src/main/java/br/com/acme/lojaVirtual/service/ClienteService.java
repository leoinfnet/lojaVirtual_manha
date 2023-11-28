package br.com.acme.lojaVirtual.service;

import br.com.acme.lojaVirtual.exception.ResourceNotFoundException;
import br.com.acme.lojaVirtual.model.Cliente;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClienteService {
    private Map<Long, Cliente> clientes = initClientes();
    private Long lastId = 3L;


    private void qqCoisa(){
        Set<Integer> set = new HashSet<>();
        Set<Integer> treeSet = new TreeSet<>();
        ArrayList<Integer> lista = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();
        lista.add(1); lista.add(2); lista.add(3);lista.add(1);
        linkedList.add(1); linkedList.add(2); linkedList.add(3);linkedList.add(1);

        //[1,2,3,1 ... 1000]
        set.add(1);set.add(2);set.add(3);set.add(1);
        treeSet.add(1);treeSet.add(2);treeSet.add(3);treeSet.add(1);
        //[1,2,3]
        set.contains(3);

        // [{1,"Leonardo"}, {2, "Joao"}]

        Map<String, List<Integer>> grupos = new HashMap<>();
        List<Integer> impares = List.of(1, 3, 5, 7);
        List<Integer> pares = List.of(0, 2, 4, 6);
        grupos.put("impares",impares);
        grupos.put("pares", pares);

        List<Integer> retorno = grupos.get("impares");

    }

    private Map<Long,Cliente> initClientes(){
        Cliente joao = new Cliente(1L, "Joao", "12496329874");
        Cliente pedro = new Cliente(2L, "Pedro", "11144477785");
        Cliente jose = new Cliente(3L, "Jose", "22255698741");


        Map<Long, Cliente> clientes = new HashMap<>();
        clientes.put(1L, joao); clientes.put(2L,pedro); clientes.put(3L,jose);
        return clientes;
    }

    public List<Cliente> getAll() {
        return clientes.values().stream().toList();
    }

    public Cliente getById(long id) {
        Cliente cliente = clientes.get(id);
        if(cliente == null) throw new ResourceNotFoundException("Cliente não localizado");
        return cliente;
    }

    public void deleteById(long id) {
        clientes.remove(id);
    }

    public void update(long id, Cliente cliente) {
        cliente.setId(id);
        clientes.replace(id,cliente);
    }
    public Long getLastId(){
        return this.lastId;
    }

    public void incrementLastId() {
        this.lastId++;
    }

    public void create(Cliente cliente) {
        //Long id = (long ) this.clientes.values().size() + 1;
        Long id = ++this.lastId;
        cliente.setId(id);
        clientes.put(cliente.getId(), cliente);
    }
}
