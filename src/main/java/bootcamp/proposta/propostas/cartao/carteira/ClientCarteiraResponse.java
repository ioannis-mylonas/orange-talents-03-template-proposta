package bootcamp.proposta.propostas.cartao.carteira;

import bootcamp.proposta.propostas.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ClientCarteiraResponse {
    @JsonProperty
    private ClientCarteiraResponseEnum resultado;

    @JsonProperty
    private String id;

    public ClientCarteiraResponseEnum getResultado() {
        return resultado;
    }

    public String getId() {
        return id;
    }

    public AssociacaoCarteira converte(CarteiraEnum carteira, Cartao cartao) {
        return new AssociacaoCarteira(id, carteira, cartao);
    }
}
