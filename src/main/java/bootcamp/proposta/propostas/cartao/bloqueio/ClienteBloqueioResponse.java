package bootcamp.proposta.propostas.cartao.bloqueio;

import bootcamp.proposta.propostas.cartao.CartaoEstado;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClienteBloqueioResponse {
    @JsonProperty
    private CartaoEstado resultado;

    public void setResultado(CartaoEstado resultado) {
        this.resultado = resultado;
    }

    public CartaoEstado getResultado() {
        return resultado;
    }
}
