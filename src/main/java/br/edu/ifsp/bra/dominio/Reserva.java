package br.edu.ifsp.bra.dominio;

import java.util.Date;

public class Reserva {
    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private Double valorTotal;
    private String status;

    // Relacionamentos e Navegabilidade explícitos no Diagrama de Classes
    private Cliente cliente;
    private Condutor condutor;
    private Carro carro;
    private Pagamento pagamento;

    public Reserva() {
        this.status = "Criada";
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }
    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }
    public Double getValorTotal() { return valorTotal; }
    public void setValorTotal(Double valorTotal) { this.valorTotal = valorTotal; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Condutor getCondutor() { return condutor; }
    public void setCondutor(Condutor condutor) { this.condutor = condutor; }
    public Carro getCarro() { return carro; }
    public void setCarro(Carro carro) { this.carro = carro; }
    public Pagamento getPagamento() { return pagamento; }
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
}