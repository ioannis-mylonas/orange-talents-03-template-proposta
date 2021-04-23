package bootcamp.proposta.propostas.cartao.request;

import bootcamp.proposta.propostas.cartao.*;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CartaoServiceResponse {
    @JsonProperty
    private final String id;

    @JsonProperty
    private final LocalDateTime emitidoEm;

    @JsonProperty
    private final String titular;

    @JsonProperty
    private final List<BloqueioResponse> bloqueios;

    @JsonProperty
    private final List<AvisoResponse> avisos;

    @JsonProperty
    private final List<CarteiraResponse> carteiras;

    @JsonProperty
    private final List<ParcelaResponse> parcelas;

    @JsonProperty
    private final int limite;

    @JsonProperty
    private final RenegociacaoResponse renegociacao;

    @JsonProperty
    private final VencimentoResponse vencimento;

    @JsonProperty
    private final String idProposta;

    public CartaoServiceResponse(String id, LocalDateTime emitidoEm,
                                 String titular, List<BloqueioResponse> bloqueios,
                                 List<AvisoResponse> avisos, List<CarteiraResponse> carteiras,
                                 List<ParcelaResponse> parcelas, int limite,
                                 RenegociacaoResponse renegociacao, VencimentoResponse vencimento,
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

    public Cartao converte() {
        List<Bloqueio> bloqueios = this.bloqueios == null ?
                null : this.bloqueios.stream()
                .map(BloqueioResponse::converte)
                .collect(Collectors.toList());

        List<Aviso> avisos = this.avisos == null ?
                null : this.avisos.stream()
                .map(AvisoResponse::converte)
                .collect(Collectors.toList());

        List<Carteira> carteiras = this.carteiras == null ?
                null : this.carteiras.stream()
                .map(CarteiraResponse::converte)
                .collect(Collectors.toList());

        List<Parcela> parcelas = this.parcelas == null ?
                null : this.parcelas.stream()
                .map(ParcelaResponse::converte)
                .collect(Collectors.toList());

        Renegociacao renegociacao = this.renegociacao == null ?
                null : this.renegociacao.converte();

        Vencimento vencimento = this.vencimento == null ?
                null : this.vencimento.converte();

        return new Cartao(id, emitidoEm, titular,
                bloqueios, avisos, carteiras, parcelas,
                limite, renegociacao, vencimento, CartaoEstado.NORMAL);
    }
}
