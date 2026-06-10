package br.edu.ifsp.bra.dominio;

import java.time.LocalDate;
import java.util.Date;

public class Condutor {
    private Long id;
    private String nome;
    private String numeroCNH;
    private LocalDate validadeCNH;

    public Condutor(Long id, String nome, String numeroCNH, LocalDate validadeCNH) {
        this.id = id;
        this.nome = nome;
        this.numeroCNH = numeroCNH;
        this.validadeCNH = validadeCNH;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getNumeroCNH() { return numeroCNH; }
    public void setNumeroCNH(String numeroCNH) { this.numeroCNH = numeroCNH; }
    public LocalDate getValidadeCNH() { return validadeCNH; }
    public void setValidadeCNH(LocalDate validadeCNH) { this.validadeCNH = validadeCNH; }
}