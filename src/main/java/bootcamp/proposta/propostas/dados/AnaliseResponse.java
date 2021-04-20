package bootcamp.proposta.propostas.dados;

import bootcamp.proposta.validators.Documento;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AnaliseResponse {
    @Documento
    @NotBlank
    @JsonProperty
    private final String documento;

    @NotBlank
    @JsonProperty
    private final String nome;

    @NotBlank
    @JsonProperty
    private final String idProposta;

    @NotBlank
    @JsonProperty
    private final ElegibilidadeDados resultadoSolicitacao;

    public AnaliseResponse(@NotBlank String documento, @NotBlank String nome, @NotBlank String idProposta, @NotBlank ElegibilidadeDados resultadoSolicitacao) {
        this.documento = documento;
        this.nome = nome;
        this.idProposta = idProposta;
        this.resultadoSolicitacao = resultadoSolicitacao;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public String getIdProposta() {
        return idProposta;
    }

    public ElegibilidadeDados getResultadoSolicitacao() {
        return resultadoSolicitacao;
    }
}
