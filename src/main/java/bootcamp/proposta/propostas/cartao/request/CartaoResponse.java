package bootcamp.proposta.propostas.cartao.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class CartaoResponse {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final LocalDateTime emitidoEm;

    @JsonProperty
    private final String titular;

    @JsonProperty
    private final List<Bloqueio> bloqueios;

    @JsonProperty
    private final List<Aviso> avisos;

    @JsonProperty
    private final List<Carteira> carteiras;

    @JsonProperty
    private final List<Parcela> parcelas;

    @JsonProperty
    private final int limite;

    @JsonProperty
    private final Renegociacao renegociacao;

    @JsonProperty
    private final Vencimento vencimento;

    @JsonProperty
    private final String idProposta;

    public CartaoResponse(String id, LocalDateTime emitidoEm,
                          String titular, List<Bloqueio> bloqueios,
                          List<Aviso> avisos, List<Carteira> carteiras,
                          List<Parcela> parcelas, int limite,
                          Renegociacao renegociacao, Vencimento vencimento,
                          String idProposta) {

        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    public String getId() {
        return id;
    }
}
