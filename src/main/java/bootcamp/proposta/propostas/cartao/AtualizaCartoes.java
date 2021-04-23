package bootcamp.proposta.propostas.cartao;

import bootcamp.proposta.propostas.EstadoProposta;
import bootcamp.proposta.propostas.Proposta;
import bootcamp.proposta.propostas.PropostaRepository;
import bootcamp.proposta.propostas.cartao.request.CartaoServiceResponse;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class AtualizaCartoes {
    private final CartaoClient client;
    private final PropostaRepository propostaRepository;

    public AtualizaCartoes(CartaoClient client,
                           PropostaRepository propostaRepository) {

        this.client = client;
        this.propostaRepository = propostaRepository;
    }

    private void saveCartao(Proposta proposta, Cartao cartao) {
        proposta.adicionaCartao(cartao);
        propostaRepository.save(proposta);
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void atualiza() {
        List<Proposta> propostas = propostaRepository
                .findByCartaoAndEstadoProposta(null, EstadoProposta.ELEGIVEL);

        for (Proposta proposta : propostas) {
            CartaoServiceResponse response = client.consulta(proposta.getId());
            if (response != null) saveCartao(proposta, response.converte());
        }
    }
}
