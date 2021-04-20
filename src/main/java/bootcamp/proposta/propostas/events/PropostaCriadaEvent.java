package bootcamp.proposta.propostas.events;

import bootcamp.proposta.propostas.Proposta;
import org.springframework.context.ApplicationEvent;

public class PropostaCriadaEvent extends ApplicationEvent {
    private final Proposta proposta;

    public PropostaCriadaEvent(Object source, Proposta proposta) {
        super(source);
        this.proposta = proposta;
    }

    public Proposta getProposta() {
        return proposta;
    }
}
