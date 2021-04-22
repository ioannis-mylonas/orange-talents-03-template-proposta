package bootcamp.proposta.propostas.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Vencimento {
    @Id
    private String id;

    private int dia;
    private LocalDateTime dataDeCriacao;

    /**
     * @Deprecated Para uso do Hibernate
     */
    public Vencimento() {}

    public Vencimento(String id, int dia, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.dia = dia;
        this.dataDeCriacao = dataDeCriacao;
    }
}
