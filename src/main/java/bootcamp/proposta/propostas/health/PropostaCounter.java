package bootcamp.proposta.propostas.health;

import bootcamp.proposta.propostas.events.PropostaCriadaEvent;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PropostaCounter implements ApplicationListener<PropostaCriadaEvent> {
    private final Counter propostaCounter;

    public PropostaCounter(MeterRegistry meterRegistry) {
        this.propostaCounter = Counter.builder("propostas")
                .tag("method", "POST")
                .description("Número de propostas criadas.")
                .register(meterRegistry);
    }

    public void onApplicationEvent(PropostaCriadaEvent event) {
        propostaCounter.increment();
    }
}