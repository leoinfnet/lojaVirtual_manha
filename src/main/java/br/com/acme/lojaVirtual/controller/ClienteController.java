package br.com.acme.lojaVirtual.controller;

import br.com.acme.lojaVirtual.exception.ResourceNotFoundException;
import br.com.acme.lojaVirtual.model.Cliente;
import br.com.acme.lojaVirtual.model.ClientesPayload;
import br.com.acme.lojaVirtual.model.InformacoesPayload;
import br.com.acme.lojaVirtual.model.ResponsePayload;
import br.com.acme.lojaVirtual.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    @Autowired
    ClienteService clienteService;
    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false, defaultValue = "10") int size,
                                 @RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false ) Optional<String> nome){
        try {
            if(page < 1) throw new InvalidParameterException("Parametro page Invalido");
            List<Cliente> clientes = new ArrayList<>();
            if(nome.isPresent()){
                clientes = clienteService.filterByName(nome.get(),size);
            }else{
                clientes = clienteService.getByPage(page, size);
            }

            int totalSize = clientes.size();
            int qtdPaginas = clienteService.getTotalPaginas(size);
            new InformacoesPayload(totalSize,qtdPaginas);
            InformacoesPayload infoPayload = InformacoesPayload.builder()
                    .totalSize(totalSize)
                    .totalPages(qtdPaginas)
                    .build();
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("total-size", String.valueOf(totalSize));
            responseHeaders.set("total-pages", String.valueOf(qtdPaginas));


            ClientesPayload clientesPayload = new ClientesPayload(clientes,infoPayload);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(responseHeaders).body(clientes);
        } catch (InvalidParameterException | IndexOutOfBoundsException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponsePayload("Valor invalido para Page"));
        }
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
    public ResponseEntity<ResponsePayload> create(@RequestBody Cliente cliente){
        clienteService.create(cliente);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponsePayload("Cliente cadastrado com sucesso!"));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponsePayload> delete(@PathVariable Long id){
        try {
            clienteService.deleteById(id);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body((new ResponsePayload("Cliente deletado com sucesso!")));
        } catch (ResourceNotFoundException ex) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponsePayload(ex.getMessage()));
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ResponsePayload> update(@PathVariable  Long id, @RequestBody Cliente atualizado){
        try{
            clienteService.update(id,atualizado);
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ResponsePayload("Cliente alterado com sucesso!"));

        }catch (ResourceNotFoundException ex){
             return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponsePayload(ex.getMessage()));
        }
    }


}
