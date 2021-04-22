package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.Renegociacao;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class RenegociacaoResponse {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final int quantidade;

    @JsonProperty
    private final BigDecimal valor;

    @JsonProperty
    private final LocalDateTime dataDeCriacao;

    public RenegociacaoResponse(String id, int quantidade, BigDecimal valor, LocalDateTime dataDeCriacao) {
        this.id = id;
        this.quantidade = quantidade;
        this.valor = valor;
        this.dataDeCriacao = dataDeCriacao;
    }

    public Renegociacao converte() {
        return new Renegociacao(id, quantidade, valor, dataDeCriacao);
    }
}
