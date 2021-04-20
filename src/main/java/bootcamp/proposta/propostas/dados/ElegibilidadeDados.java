package bootcamp.proposta.propostas.dados;

import bootcamp.proposta.propostas.EstadoProposta;

public enum ElegibilidadeDados {
    COM_RESTRICAO(EstadoProposta.NAO_ELEGIVEL),
    SEM_RESTRICAO(EstadoProposta.ELEGIVEL);

    private final EstadoProposta estadoProposta;

    ElegibilidadeDados(EstadoProposta estadoProposta) {
        this.estadoProposta = estadoProposta;
    }

    public EstadoProposta getEstadoProposta() {
        return this.estadoProposta;
    }
}
