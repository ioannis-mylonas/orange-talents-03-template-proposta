package bootcamp.proposta.propostas.cartao;

import bootcamp.proposta.propostas.cartao.aviso.Aviso;
import bootcamp.proposta.propostas.cartao.biometria.Biometria;
import bootcamp.proposta.propostas.cartao.bloqueio.Bloqueio;
import bootcamp.proposta.propostas.cartao.carteira.Carteira;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Cartao {
    @Id
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private int limite;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private List<Bloqueio> bloqueios = new ArrayList<>();
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private List<Aviso> avisos = new ArrayList<>();
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private List<Carteira> carteiras = new ArrayList<>();
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn
    private List<Parcela> parcelas;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private Renegociacao renegociacao;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn
    private Vencimento vencimento;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn
    private Set<Biometria> biometrias = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private CartaoEstado estado;

    /**
     * @Deprecated Para uso do Hibernate
     */
    public Cartao() {}

    public Cartao(String id, LocalDateTime emitidoEm, String titular, List<Parcela> parcelas,
                  int limite, Renegociacao renegociacao, Vencimento vencimento,
                  CartaoEstado estado) {

        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.estado = estado;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<Bloqueio> getBloqueios() {
        return bloqueios;
    }

    public List<Aviso> getAvisos() {
        return avisos;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public int getLimite() {
        return limite;
    }

    public Renegociacao getRenegociacao() {
        return renegociacao;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }

    public Set<Biometria> getBiometrias() {
        return biometrias;
    }

    public CartaoEstado getEstado() {
        return estado;
    }

    public void addBiometria(Biometria biometria) {
        biometrias.add(biometria);
    }

    public void addAviso(Aviso aviso) {
        this.avisos.add(aviso);
    }

    public void addAllAviso(Collection<Aviso> avisos) {
        this.avisos.addAll(avisos);
    }

    public void bloqueia(Bloqueio bloqueio) {
        this.bloqueios.add(bloqueio);
    }

    public void AddAllBloqueio(Collection<Bloqueio> bloqueios) {
        this.bloqueios.addAll(bloqueios);
    }

    public void mudaEstado(CartaoEstado estado) {
        this.estado = estado;
    }

    public void addCarteira(Carteira carteira) {
        this.carteiras.add(carteira);
    }

    public void addAllCarteira(Collection<Carteira> carteiras) {
        this.carteiras.addAll(carteiras);
    }
}
