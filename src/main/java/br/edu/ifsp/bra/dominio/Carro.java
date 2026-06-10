package br.edu.ifsp.bra.dominio;

public class Carro {
    private Long id;
    private String modelo;
    private String placa;
    private Boolean disponivel;
    private Double valorDiaria;

    public Carro(Long id, String modelo, String placa, Boolean disponivel, Double valorDiaria) {
        this.id = id;
        this.modelo = modelo;
        this.placa = placa;
        this.disponivel = disponivel;
        this.valorDiaria = valorDiaria;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }
    public Boolean getDisponivel() { return disponivel; }
    public void setDisponivel(Boolean disponivel) { this.disponivel = disponivel; }
    public Double getValorDiaria() { return valorDiaria; }
    public void setValorDiaria(Double valorDiaria) { this.valorDiaria = valorDiaria; }
}