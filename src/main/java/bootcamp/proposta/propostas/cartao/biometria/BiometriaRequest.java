package bootcamp.proposta.propostas.cartao.biometria;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class BiometriaRequest {
    @JsonProperty
    @NotNull
    private byte[] biometria;

    public void setBiometria(byte[] biometria) {
        this.biometria = biometria;
    }

    public Biometria converte() {
        return new Biometria(biometria);
    }
}
