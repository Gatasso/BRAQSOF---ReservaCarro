package br.edu.ifsp.bra.aplicacao;

import br.edu.ifsp.bra.dominio.FormaPagamento;
import br.edu.ifsp.bra.dominio.Pagamento;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    private Double valor;

    public Double aplicarDesconto(FormaPagamento formaPagamento) {
        if (formaPagamento == null) {
            return 1.0; // Sem alteração
        }

        switch (formaPagamento) {
            case PIX_AVISTA:
                return 0.90; // 10% de desconto
            case CARTAO_AVISTA:
                return 0.95; // 5% de desconto
            case CARTAO_PARCELADO:
            default:
                return 1.0;  // Preço normal
        }
    }

    public Boolean processarPagamento(Pagamento pagamento) {
        if (pagamento == null || pagamento.getValor() == null || pagamento.getValor() <= 0) {
            return false;
        }

        // RN02: Não é permitido parcelar em mais de 5 vezes
        if (pagamento.getFormaPagamento() == FormaPagamento.CARTAO_PARCELADO) {
            if (pagamento.getParcelas() == null || pagamento.getParcelas() < 1 || pagamento.getParcelas() > 5) {
                pagamento.setAprovado(false);
                return false;
            }
        } else {
            pagamento.setParcelas(1);
        }

        pagamento.setAprovado(true);
        return true;
    }
}