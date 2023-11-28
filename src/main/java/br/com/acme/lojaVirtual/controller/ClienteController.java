package br.com.acme.lojaVirtual.controller;

import br.com.acme.lojaVirtual.exception.ResourceNotFoundException;
import br.com.acme.lojaVirtual.model.Cliente;
import br.com.acme.lojaVirtual.model.ResponsePayload;
import br.com.acme.lojaVirtual.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;
    @GetMapping
    public List<Cliente> getAll(){
        List<Cliente> clientes = clienteService.getAll();
        return clientes;
    }
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id){
        try{
            Cliente cliente = clienteService.getById(id);
            return ResponseEntity.ok(cliente);
        }catch (ResourceNotFoundException ex){
            ResponsePayload responsePayload = new ResponsePayload(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responsePayload);
        }

    }
    @PostMapping
    public void create(@RequestBody Cliente cliente){
        clienteService.create(cliente);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        clienteService.deleteById(id);
    }
    @PutMapping("/{id}")
    public void update(@PathVariable  Long id, @RequestBody Cliente atualizado){
        clienteService.update(id,atualizado);
    }


}
