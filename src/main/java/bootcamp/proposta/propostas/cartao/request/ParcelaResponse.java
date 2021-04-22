package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.Parcela;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class ParcelaResponse {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final int quantidade;

    @JsonProperty
    private final BigDecimal valor;

    public ParcelaResponse(String id, int quantidade, BigDecimal valor) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
    }

    public Parcela converte() {
        return new Parcela(id, quantidade, valor);
    }
}
