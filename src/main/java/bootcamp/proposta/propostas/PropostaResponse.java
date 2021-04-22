package bootcamp.proposta.propostas;

import bootcamp.proposta.propostas.cartao.CartaoResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PropostaResponse {
    @JsonProperty
    private final String nome;
    @JsonProperty
    private final EstadoProposta estado;
    @JsonProperty
    private final CartaoResponse cartao;

    public PropostaResponse(Proposta proposta) {
        this.nome = proposta.getNome();
        this.estado = proposta.getEstadoProposta();
        this.cartao = new CartaoResponse(proposta.getCartao());
    }
}
