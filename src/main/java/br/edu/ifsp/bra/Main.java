package br.edu.ifsp.bra;

import br.edu.ifsp.bra.aplicacao.ReservaService;
import br.edu.ifsp.bra.dominio.*;

import java.time.LocalDate;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        System.out.println("=== SIMULADOR DE RESERVA DE CARROS ===");

        // 1. Instanciando os Serviços (Camada de Aplicação)
        ReservaService reservaService = new ReservaService();

        // 2. Criando dados de teste (Camada de Domínio)
        Cliente cliente = new Cliente(1L, "João da Silva", "joao@email.com", "(11) 99999-9999");

        // Condutor com CNH Válida (Validade futura)
        Condutor condutorValido = new Condutor(1L, "João da Silva", "123456789", LocalDate.now().plusYears(3));

        // Carro escolhido
        Carro carro = new Carro(10L, "Duster 4x4", "BRA-2026", true, 120.0);

        // 3. Configurando os parâmetros da simulação de reserva (4 dias)
        // Usando reflexão ou alterando diretamente para testar o cálculo (já que os atributos são privados na Service)
        // Para simplificar a simulação usando o seu modelo, vamos definir o cenário de pagamento:
        Pagamento pagamentoPix = new Pagamento(100L, 0.0, 1, FormaPagamento.PIX_AVISTA);

        System.out.println("\n--- Cenário 1: Tentativa de Reserva com Sucesso (PIX à Vista) ---");

        // Executando o fluxo do caso de uso
        Reserva reservaSucesso = reservaService.reservarCarro(cliente.getId(), carro.getId(), condutorValido, pagamentoPix);

        System.out.println("Status Final da Reserva: " + reservaSucesso.getStatus());
        if ("Confirmada".equals(reservaSucesso.getStatus())) {
            System.out.println("Condutor Confirmado: " + reservaSucesso.getCondutor().getNome());
            System.out.println("Pagamento Aprovado? " + (reservaSucesso.getPagamento().getAprovado() ? "Sim" : "Não"));
        }

        System.out.println("\n--- Cenário 2: Fluxo de Exceção (CNH Vencida) ---");

        // Condutor com CNH vencida ontem
        Condutor condutorVencido = new Condutor(2L, "Pedro Souza", "987654321", LocalDate.now().minusDays(1));
        Pagamento pagamentoCartao = new Pagamento(101L, 0.0, 1, FormaPagamento.CARTAO_AVISTA);

        Reserva reservaFalhaCNH = reservaService.reservarCarro(cliente.getId(), carro.getId(), condutorVencido, pagamentoCartao);
        System.out.println("Status Final da Reserva: " + reservaFalhaCNH.getStatus());

        System.out.println("\n--- Cenário 3: Fluxo de Exceção (Parcelamento Abusivo > 5x) ---");

        // Pagamento tentando parcelar em 6 vezes (O limite estipulado na RN02 é 5)
        Pagamento pagamentoInvalido = new Pagamento(102L, 400.0, 6, FormaPagamento.CARTAO_PARCELADO);

        Reserva reservaFalhaParcelas = reservaService.reservarCarro(cliente.getId(), carro.getId(), condutorValido, pagamentoInvalido);
        System.out.println("Status Final da Reserva: " + reservaFalhaParcelas.getStatus());
        System.out.println("Pagamento foi aprovado? " + pagamentoInvalido.getAprovado());
    }
}