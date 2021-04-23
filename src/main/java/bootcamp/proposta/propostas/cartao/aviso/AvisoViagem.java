package bootcamp.proposta.propostas.cartao.aviso;

import bootcamp.proposta.propostas.cartao.Cartao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class AvisoViagem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Cartao cartao;

    @NotBlank
    private String destino;

    @FutureOrPresent
    @NotNull
    private LocalDate termino;

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    @CreationTimestamp
    private LocalDateTime criacao = LocalDateTime.now();

    /**
     * @Deprecated Para uso do Hibernate
     */
    public AvisoViagem() {}

    public AvisoViagem(@NotNull Cartao cartao,
                       @NotBlank String destino,
                       @FutureOrPresent @NotNull LocalDate termino,
                       @NotBlank String ip,
                       @NotBlank String userAgent) {

        this.cartao = cartao;
        this.destino = destino;
        this.termino = termino;
        this.ip = ip;
        this.userAgent = userAgent;
    }
}
