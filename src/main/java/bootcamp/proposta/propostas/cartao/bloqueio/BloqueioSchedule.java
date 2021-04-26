package bootcamp.proposta.propostas.cartao.bloqueio;

import bootcamp.proposta.propostas.cartao.Cartao;
import bootcamp.proposta.propostas.cartao.CartaoClient;
import bootcamp.proposta.propostas.cartao.CartaoEstado;
import bootcamp.proposta.propostas.cartao.CartaoRepository;
import feign.FeignException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
public class BloqueioSchedule {
    private final CartaoClient client;
    private final CartaoRepository repository;

    public BloqueioSchedule(CartaoClient client, CartaoRepository repository) {
        this.client = client;
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void bloqueia() {
        List<Cartao> cartoes = repository
                .findByEstadoAndBloqueiosNotNull(CartaoEstado.NORMAL);

        for (Cartao cartao : cartoes) {
        }
    }
}
