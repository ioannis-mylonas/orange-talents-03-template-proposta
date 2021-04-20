package bootcamp.proposta.propostas.cartao;

import bootcamp.proposta.propostas.EstadoProposta;
import bootcamp.proposta.propostas.Proposta;
import bootcamp.proposta.propostas.PropostaRepository;
import bootcamp.proposta.propostas.cartao.request.CartaoResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class AtualizaCartoes {
    private final CartaoClient client;
    private final PropostaRepository propostaRepository;

    public AtualizaCartoes(CartaoClient client, PropostaRepository propostaRepository) {
        this.client = client;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void atualiza() {
        List<Proposta> propostas = propostaRepository
                .findByCartaoIdAndEstadoProposta(null, EstadoProposta.ELEGIVEL);

        for (Proposta proposta : propostas) {
            CartaoResponse response = client.consulta(proposta.getId());
            if (response != null) proposta.adicionaCartao(response.getId());
        }
    }
}