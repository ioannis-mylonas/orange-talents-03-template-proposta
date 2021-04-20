package bootcamp.proposta.propostas.events;

import bootcamp.proposta.propostas.Proposta;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class PropostaEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public PropostaEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void propostaCriada(final Proposta proposta) {
        PropostaCriadaEvent event = new PropostaCriadaEvent(this, proposta);
        applicationEventPublisher.publishEvent(event);
    }
}
