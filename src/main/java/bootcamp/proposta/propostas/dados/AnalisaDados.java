package bootcamp.proposta.propostas.dados;

import bootcamp.proposta.propostas.Proposta;
import bootcamp.proposta.propostas.events.PropostaCriadaEvent;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AnalisaDados implements ApplicationListener<PropostaCriadaEvent> {
    private final AnaliseClient client;
    private final Tracer tracer;

    public AnalisaDados(AnaliseClient client, Tracer tracer) {
        this.client = client;
        this.tracer = tracer;
    }

    @Override
    @Transactional
    public void onApplicationEvent(PropostaCriadaEvent event) {
        Proposta proposta = event.getProposta();

        Span span = tracer.activeSpan();
        span.setTag("proposta", proposta.getId());
        span.log("Consultando proposta...");

        ElegibilidadeDados elegibilidade;
        AnaliseRequest request = new AnaliseRequest(proposta);

        try {
            AnaliseResponse response = client.consulta(request);
            elegibilidade = response.getResultadoSolicitacao();
        } catch(FeignException.UnprocessableEntity ex) {
            elegibilidade = ElegibilidadeDados.COM_RESTRICAO;
        }

        proposta.atualiza(elegibilidade.getEstadoProposta());

        span.log("Consulta de proposta finalizada.");
    }
}
