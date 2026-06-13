package br.edu.ifsp.bra.controller;

import br.edu.ifsp.bra.aplicacao.PagamentoService;
import br.edu.ifsp.bra.dominio.Pagamento;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testes/pagamentos")
public class PagamentoController {

    private final PagamentoService pagamentoService;

    public PagamentoController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @PostMapping("/processar")
    public ResponseEntity<Pagamento> processarPagamento(
            @RequestBody Pagamento pagamento) {

        // Executa a regra de negócio do seu service passados o valor e a estrutura
        pagamentoService.processarPagamento(pagamento);
        return ResponseEntity.ok(pagamento);
    }
}