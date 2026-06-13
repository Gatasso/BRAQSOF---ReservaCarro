package br.edu.ifsp.bra.controller;

import br.edu.ifsp.bra.aplicacao.ReservaService;
import br.edu.ifsp.bra.dominio.Condutor;
import br.edu.ifsp.bra.dominio.Pagamento;
import br.edu.ifsp.bra.dominio.Reserva;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testes/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    public static class RequisicaoReserva {
        public Long clienteId;
        public Long carroId;
        public Condutor condutor;
        public Pagamento pagamento;
    }

    @PostMapping
    public ResponseEntity<Reserva> criarReserva(@RequestBody RequisicaoReserva dto) {
        Reserva reserva = reservaService.reservarCarro(
                dto.clienteId,
                dto.carroId,
                dto.condutor,
                dto.pagamento
        );
        return ResponseEntity.ok(reserva);
    }
}