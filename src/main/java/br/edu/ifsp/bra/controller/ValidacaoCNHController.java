package br.edu.ifsp.bra.controller;

import br.edu.ifsp.bra.aplicacao.ValidacaoCNHService;
import br.edu.ifsp.bra.dominio.Condutor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testes/cnh")
public class ValidacaoCNHController {

    private final ValidacaoCNHService validacaoCNHService;

    public ValidacaoCNHController(ValidacaoCNHService validacaoCNHService) {
        this.validacaoCNHService = validacaoCNHService;
    }

    @PostMapping("/validar")
    public ResponseEntity<Boolean> validarCNH(@RequestBody Condutor condutor) {
        boolean ehValida = validacaoCNHService.validarCNH(condutor);
        return ResponseEntity.ok(ehValida);
    }
}