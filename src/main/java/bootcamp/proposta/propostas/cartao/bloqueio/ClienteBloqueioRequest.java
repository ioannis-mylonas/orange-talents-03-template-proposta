package bootcamp.proposta.propostas.cartao.bloqueio;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClienteBloqueioRequest {
    @JsonProperty
    private final String sistemaResponsavel = "proposta";
}
