package bootcamp.proposta.propostas.cartao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartaoResponse {
    @JsonProperty
    private String id;

    public CartaoResponse(Cartao cartao) {
        if (cartao == null) return;
        this.id = cartao.getId();
    }
}
