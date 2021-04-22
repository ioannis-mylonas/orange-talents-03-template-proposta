package bootcamp.proposta.propostas;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PropostaResponse {
    @JsonProperty
    private final String nome;
    @JsonProperty
    private final EstadoProposta estado;
    @JsonProperty
    private final String cartaoId;

    public PropostaResponse(Proposta proposta) {
        this.nome = proposta.getNome();
        this.estado = proposta.getEstadoProposta();
        this.cartaoId = proposta.getCartaoId();
    }
}
