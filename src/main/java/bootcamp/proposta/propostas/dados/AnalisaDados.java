package bootcamp.proposta.propostas.dados;

import bootcamp.proposta.propostas.EstadoProposta;
import bootcamp.proposta.propostas.Proposta;
import bootcamp.proposta.propostas.PropostaRepository;
import feign.FeignException;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class AnalisaDados {
    private final AnaliseClient client;
    private final PropostaRepository propostaRepository;

    public AnalisaDados(AnaliseClient client,
                        PropostaRepository propostaRepository) {

        this.client = client;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void analisa() {
        List<Proposta> propostas = propostaRepository.findByEstadoProposta(EstadoProposta.ANALISE);

        for (Proposta proposta : propostas) {

            ElegibilidadeDados elegibilidade;
            AnaliseRequest request = new AnaliseRequest(proposta);

            try {
                AnaliseResponse response = client.consulta(request);
                elegibilidade = response.getResultadoSolicitacao();
            } catch (FeignException.UnprocessableEntity ex) {
                elegibilidade = ElegibilidadeDados.COM_RESTRICAO;
            }

            proposta.atualiza(elegibilidade.getEstadoProposta());
        }
    }
}
