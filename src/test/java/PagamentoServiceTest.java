import br.edu.ifsp.bra.aplicacao.PagamentoService;
import br.edu.ifsp.bra.dominio.FormaPagamento;
import br.edu.ifsp.bra.dominio.Pagamento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PagamentoServiceTest {

    private PagamentoService pagamentoService;

    @BeforeEach
    void setUp() {
        this.pagamentoService = new PagamentoService();
    }

    @Test
    @DisplayName("CT01/CT22 - Deve aplicar multiplicador de 10% de desconto para Pix à Vista")
    void deveAplicarDescontoPix() {
        Double fatorPreco = pagamentoService.aplicarDesconto(FormaPagamento.PIX_AVISTA);
        assertEquals(0.90, fatorPreco, 0.001, "O fator do PIX deve ser 0.90 (10% de desconto).");
    }

    @Test
    @DisplayName("CT02/CT18 - Deve aplicar multiplicador de 5% de desconto para Cartão à Vista (Limite de 1 parcela)")
    void deveAplicarDescontoCartaoAvista() {
        Double fatorPreco = pagamentoService.aplicarDesconto(FormaPagamento.CARTAO_AVISTA);
        assertEquals(0.95, fatorPreco, 0.001, "O fator do Cartão à Vista deve ser 0.95 (5% de desconto).");
    }

    @Test
    @DisplayName("CT03/CT19 - Não deve aplicar desconto para Cartão Parcelado (fator 1.0) no limite de 5x")
    void deveManterPrecoNormalParaParceladoNoLimite() {
        Double fatorPreco = pagamentoService.aplicarDesconto(FormaPagamento.CARTAO_PARCELADO);
        assertEquals(1.0, fatorPreco, 0.001, "Cartão parcelado não deve sofrer alteração no valor base.");

        Pagamento pag5x = new Pagamento(1L, 500.0, 5, FormaPagamento.CARTAO_PARCELADO);
        assertTrue(pagamentoService.processarPagamento(pag5x), "Parcelamento em 5x deve ser aceito.");
        assertTrue(pag5x.getAprovado());
    }

    @Test
    @DisplayName("CT09 - Deve rejeitar e reprovar pagamentos com parcelas acima do limite permitido (> 5x)")
    void deveRejeitarParcelamentoAcimaDoLimite() {
        // Tentativa de parcelar em 6 vezes (Invalida a RN02)
        Pagamento pag6x = new Pagamento(2L, 600.0, 6, FormaPagamento.CARTAO_PARCELADO);

        assertFalse(pagamentoService.processarPagamento(pag6x), "O sistema deve rejeitar parcelamento maior que 5x.");
        assertFalse(pag6x.getAprovado(), "O status aprovado do pagamento deve ser falso.");
    }
}