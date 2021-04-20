package bootcamp.proposta.propostas.dados;

import bootcamp.proposta.propostas.Proposta;
import bootcamp.proposta.validators.Documento;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public class AnaliseRequest {
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

    public AnaliseRequest(Proposta proposta) {
        this.documento = proposta.getDocumento();
        this.nome = proposta.getNome();
        this.idProposta = proposta.getId().toString();
    }
}
