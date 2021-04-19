package bootcamp.proposta.propostas.health;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class PropostaCounter {
    private final Counter propostaCounter;

    public PropostaCounter(MeterRegistry meterRegistry) {
        this.propostaCounter = Counter.builder("propostas")
                .tag("method", "POST")
                .description("Número de propostas criadas.")
                .register(meterRegistry);
    }

    public void incrementProposta() {
        propostaCounter.increment();
    }
}
