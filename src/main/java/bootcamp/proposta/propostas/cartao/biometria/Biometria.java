package bootcamp.proposta.propostas.cartao.biometria;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @NotNull
    @Column(nullable = false)
    private byte[] biometria;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime criacao = LocalDateTime.now();

    /**
     * @Deprecated Para uso exclusivo do Hibernate
     */
    public Biometria() {}

    public Biometria(byte[] biometria) {
        this.biometria = biometria;
    }

    public Long getId() {
        return id;
    }
}
