package br.edu.ifsp.bra.aplicacao;

import br.edu.ifsp.bra.dominio.Condutor;

import java.time.LocalDate;

public class ValidacaoCNHService {
    // RN01: Valida se a CNH está na validade
    public boolean validarCNH(Condutor condutor) {
        if (condutor == null || condutor.getNumeroCNH() == null) {
            return false;
        }
        return !condutor.getValidadeCNH().isBefore(LocalDate.now());
    }
}
