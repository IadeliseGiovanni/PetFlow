
package it.adozioni.animali.Controller;

import it.adozioni.animali.Dto.AdottanteDto;
import it.adozioni.animali.Service.AdottanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Adottante")
@CrossOrigin(origins = "http://localhost:4200")
public class AdottanteController extends AbstractController<AdottanteDto> {

    @Autowired
    private AdottanteService service;

    @GetMapping("/findByCognome")
    public List<AdottanteDto> findByCognome(String cognome) {
        return service.findByCognome(cognome);
    }

}