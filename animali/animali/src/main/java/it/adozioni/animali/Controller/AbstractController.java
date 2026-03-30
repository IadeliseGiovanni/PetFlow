package it.adozioni.animali.Controller;

import it.adozioni.animali.Service.ServiceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public abstract class AbstractController <DTO>{

    @Autowired
    private ServiceDTO<DTO> service;

    @GetMapping("/getall")
    public Iterable<DTO> getAll(){
        return service.getAll();
    }

    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Integer id) {
        service.delete(id);
    }


    @PutMapping("/update")
    public DTO update(@RequestBody DTO dto){
        service.update(dto);
        return dto;
    }

    @PostMapping("/insert")
    public DTO insert (@RequestBody DTO dto) {
        service.insert(dto);
        return dto;
    }

    @GetMapping("/read")
    public DTO read(@RequestParam("id") Integer id) {
        return service.read(id);
    }

}
