package br.edu.ifsp.bra.aplicacao;

import br.edu.ifsp.bra.dominio.*;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ReservaService {
    private Date dataInicio;
    private Date dataFim;
    private Long carroId;
    private Integer dias;

    private final ValidacaoCNHService validacaoCNHService;
    private final PagamentoService pagamentoService;

    public ReservaService() {
        this.validacaoCNHService = new ValidacaoCNHService();
        this.pagamentoService = new PagamentoService();
    }

    public Boolean verificarDisponibilidade(Date dataFim) {
        return dataFim != null && dataFim.after(new Date());
    }

    public Double calcularValor(Pagamento pagamento) {
        if (this.dias == null || this.dias <= 0) return 0.0;

        double valorBase = this.dias * 100.0;

        double fatorPreco = pagamentoService.aplicarDesconto(pagamento.getFormaPagamento());
        double valorComDesconto = valorBase * fatorPreco;

        pagamento.setValor(valorComDesconto);
        return valorComDesconto;
    }

    public Reserva reservarCarro(Long clienteId, Long carroId, Condutor condutor, Pagamento pagamento) {
        Reserva reserva = new Reserva();
        reserva.setStatus("Aguardando Validação");

        if (condutor == null || !validacaoCNHService.validarCNH(condutor)) {
            reserva.setStatus("Cancelada");
            return reserva;
        }

        reserva.setStatus("Aguardando Pagamento");

        // 2. Processar Pagamento via API (Fluxo de Exceção 6)
        boolean pagamentoAprovado = pagamentoService.processarPagamento(pagamento);
        if (!pagamentoAprovado) {
            reserva.setStatus("Cancelada");
            return reserva;
        }

        // 3. Confirmar a Reserva com sucesso
        reserva.setCondutor(condutor);
        reserva.setPagamento(pagamento);
        reserva.setStatus("Confirmada");

        return reserva;
    }
}