import br.edu.ifsp.bra.aplicacao.ReservaService;
import br.edu.ifsp.bra.dominio.Condutor;
import br.edu.ifsp.bra.dominio.FormaPagamento;
import br.edu.ifsp.bra.dominio.Pagamento;
import br.edu.ifsp.bra.dominio.Reserva;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservaServiceTest {

    private ReservaService reservaService;
    private Condutor condutorValido;
    private Pagamento pagamentoPixValido;

    @BeforeEach
    void setUp() {
        this.reservaService = new ReservaService();
        this.condutorValido = new Condutor(1L, "Maria José", "3044491", LocalDate.now().plusYears(2));
        this.pagamentoPixValido = new Pagamento(10L, 360.0, 1, FormaPagamento.PIX_AVISTA);
    }

    @Test
    @DisplayName("CT01 (Caminho GFC C4 / Estado S1) - Fluxo Principal com sucesso via PIX")
    void deveConfirmarReservaFluxoPrincipal() {
        // Executa a reserva passando dados consistentes
        Reserva resultado = reservaService.reservarCarro(1L, 100L, condutorValido, pagamentoPixValido);

        assertNotNull(resultado);
        assertEquals("Confirmada", resultado.getStatus(), "A reserva deveria mudar o estado para 'Confirmada'.");
        assertEquals(condutorValido, resultado.getCondutor());
        assertTrue(resultado.getPagamento().getAprovado());
        assertEquals(360.0, resultado.getPagamento().getValor(), 0.01, "Valor esperado: (4 dias * 100) - 10% desc = 360.0");
    }

    @Test
    @DisplayName("CT06 (Caminho GFC C5 / Estado S2) - Fluxo de Exceção: CNH Vencida cancela a reserva")
    void deveCancelarReservaSeCnhInvalida() {
        // Alterando o condutor para simular CNH vencida
        Condutor condutorVencido = new Condutor(2L, "José Maria", "3044491", LocalDate.now().minusMonths(3));

        Reserva resultado = reservaService.reservarCarro(1L, 100L, condutorVencido, pagamentoPixValido);

        assertNotNull(resultado);
        assertEquals("Cancelada", resultado.getStatus(), "A reserva deve ir para o estado 'Cancelada' se a CNH falhar.");
        assertNull(resultado.getCondutor(), "Não deve associar condutor inválido à reserva final.");
    }

    @Test
    @DisplayName("CT09 (Caminho GFC C7 / Estado S2) - Fluxo de Exceção: Cartão com parcelas abusivas cancela a reserva")
    void deveCancelarReservaSePagamentoNaoAprovado() {
        // Pagamento inválido tentando burlar o limite do cartão (6 parcelas)
        Pagamento pagamentoInvalido = new Pagamento(11L, 0.0, 6, FormaPagamento.CARTAO_PARCELADO);

        Reserva resultado = reservaService.reservarCarro(1L, 100L, condutorValido, pagamentoInvalido);

        assertNotNull(resultado);
        assertEquals("Cancelada", resultado.getStatus(), "A reserva deve ser cancelada caso o pagamento seja rejeitado.");
        assertFalse(pagamentoInvalido.getAprovado());
    }
}
