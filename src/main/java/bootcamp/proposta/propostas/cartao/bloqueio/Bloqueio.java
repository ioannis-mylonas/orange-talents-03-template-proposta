package bootcamp.proposta.propostas.cartao.bloqueio;

import bootcamp.proposta.propostas.cartao.Cartao;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Cartao cartao;

    @CreationTimestamp
    private LocalDateTime data = LocalDateTime.now();

    @NotBlank
    private String ip;

    @NotBlank
    private String userAgent;

    /**
     * @Deprecated Para uso do Hibernate
     */
    public Bloqueio() {}

    public Bloqueio(Cartao cartao, @NotBlank String ip, @NotNull String userAgent) {
        this.cartao = cartao;
        this.ip = ip;
        this.userAgent = userAgent;
    }

    public Long getId() {
        return id;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public String getIp() {
        return ip;
    }

    public String getUserAgent() {
        return userAgent;
    }
}
