package bootcamp.proposta.propostas.cartao;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Parcela {
    @Id
    private String id;

    private int quantidade;
    private BigDecimal valor;

    /**
     * @Deprecated Para uso do Hibernate
     */
    public Parcela() {}

    public Parcela(String id, int quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }
}
