package bootcamp.proposta.propostas.cartao.carteira;

import bootcamp.proposta.propostas.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Carteira {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String idCarteira;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CarteiraEnum carteira;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    /**
     * @Deprecated Para uso do Hibernate
     */
    public Carteira() {}

    public Carteira(@NotBlank String idCarteira, @NotNull CarteiraEnum carteira, @NotNull Cartao cartao) {
        this.idCarteira = idCarteira;
        this.carteira = carteira;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }
}
