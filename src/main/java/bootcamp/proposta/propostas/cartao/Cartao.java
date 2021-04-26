package bootcamp.proposta.propostas.cartao;

import bootcamp.proposta.propostas.cartao.aviso.AvisoViagem;
import bootcamp.proposta.propostas.cartao.biometria.Biometria;
import bootcamp.proposta.propostas.cartao.bloqueio.ClienteBloqueio;
import bootcamp.proposta.propostas.cartao.carteira.AssociacaoCarteira;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Cartao {
    @Id
    private String id;
    private LocalDateTime emitidoEm;
    private String titular;
    private int limite;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn
    private List<Bloqueio> bloqueios;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn
    private List<Aviso> avisos;
    @OneToMany(cascade = CascadeType.MERGE)
    @JoinColumn
    private List<Carteira> carteiras;
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

    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private ClienteBloqueio bloqueiosCliente;

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private Set<AvisoViagem> avisosViagem = new HashSet<>();

    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "cartao")
    private Set<AssociacaoCarteira> associacoesCarteira = new HashSet<>();

    @Enumerated(EnumType.STRING)
    private CartaoEstado estado;

    /**
     * @Deprecated Para uso do Hibernate
     */
    public Cartao() {}

    public Cartao(String id, LocalDateTime emitidoEm, String titular,
                  List<Bloqueio> bloqueios, List<Aviso> avisos,
                  List<Carteira> carteiras, List<Parcela> parcelas,
                  int limite, Renegociacao renegociacao, Vencimento vencimento,
                  CartaoEstado estado) {

        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
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

    public ClienteBloqueio getBloqueiosCliente() {
        return bloqueiosCliente;
    }

    public Set<AvisoViagem> getAvisosViagem() {
        return avisosViagem;
    }

    public Set<AssociacaoCarteira> getAssociacoesCarteira() {
        return associacoesCarteira;
    }

    public CartaoEstado getEstado() {
        return estado;
    }

    public void addBiometria(Biometria biometria) {
        biometrias.add(biometria);
    }

    public void addAvisoViagem(AvisoViagem avisoViagem) {
        this.avisosViagem.add(avisoViagem);
    }

    public void bloqueia(ClienteBloqueio bloqueio) {
        this.bloqueiosCliente = bloqueio;
    }

    public void mudaEstado(CartaoEstado estado) {
        this.estado = estado;
    }

    public void addAssociacaoCarteira(AssociacaoCarteira associacao) {
        this.associacoesCarteira.add(associacao);
    }
}
