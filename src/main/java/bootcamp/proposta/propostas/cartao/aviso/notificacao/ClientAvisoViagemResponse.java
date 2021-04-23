package bootcamp.proposta.propostas.cartao.aviso.notificacao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientAvisoViagemResponse {
    @JsonProperty
    private String resultado;

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
