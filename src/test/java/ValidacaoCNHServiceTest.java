import br.edu.ifsp.bra.aplicacao.ValidacaoCNHService;
import br.edu.ifsp.bra.dominio.Condutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidacaoCNHServiceTest {

    private ValidacaoCNHService cnhService;

    @BeforeEach
    void setUp() {
        this.cnhService = new ValidacaoCNHService();
    }

    @Test
    @DisplayName("CT01/CT16 - Deve aceitar CNH válida e no limite exato do último dia de vencimento")
    void deveAceitarCnhValidaNoLimite() {
        // CNH vencendo exatamente hoje (Valor Limite)
        Condutor condutorLimite = new Condutor(1L, "João", "123456789", LocalDate.now());
        assertTrue(cnhService.validarCNH(condutorLimite), "A CNH no último dia de validade deve ser considerada válida.");
    }

    @Test
    @DisplayName("CT06/CT17 - Deve rejeitar CNH vencida (inclusive há exatamente 1 dia)")
    void deveRejeitarCnhVencidaHaUmDia() {
        // CNH vencida ontem (Valor Limite)
        Condutor condutorVencidoOntem = new Condutor(2L, "Pedro", "987654321", LocalDate.now().minusDays(1));
        assertFalse(cnhService.validarCNH(condutorVencidoOntem), "CNH vencida há 1 dia deve ser rejeitada.");
    }

    @Test
    @DisplayName("CT20 - Deve retornar falso (rejeitar) quando o condutor ou CNH for nulo (Partição de Equivalência)")
    void deveRejeitarCnhNula() {
        Condutor condutorNulo = null;
        Condutor condutorSemCnh = new Condutor(3L, "Carlos", null, LocalDate.now().plusYears(1));

        assertFalse(cnhService.validarCNH(condutorNulo), "Condutor nulo deve retornar falso.");
        assertFalse(cnhService.validarCNH(condutorSemCnh), "Condutor sem número de CNH deve retornar falso.");
    }
}