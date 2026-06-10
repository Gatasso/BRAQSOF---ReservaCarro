package br.edu.ifsp.bra.dominio;

public class Pagamento {
    private Long id;
    private Double valor;
    private Integer parcelas;
    private Boolean aprovado;
    private FormaPagamento formaPagamento; // Associação do diagrama

    public Pagamento(Long id, Double valor, Integer parcelas, FormaPagamento formaPagamento) {
        this.id = id;
        this.valor = valor;
        this.parcelas = parcelas;
        this.formaPagamento = formaPagamento;
        this.aprovado = false;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Double getValor() { return valor; }
    public void setValor(Double valor) { this.valor = valor; }
    public Integer getParcelas() { return parcelas; }
    public void setParcelas(Integer parcelas) { this.parcelas = parcelas; }
    public Boolean getAprovado() { return aprovado; }
    public void setAprovado(Boolean aprovado) { this.aprovado = aprovado; }
    public FormaPagamento getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(FormaPagamento formaPagamento) { this.formaPagamento = formaPagamento; }
}
