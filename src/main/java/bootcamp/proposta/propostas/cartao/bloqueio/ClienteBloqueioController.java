package bootcamp.proposta.propostas.cartao.bloqueio;

import bootcamp.proposta.propostas.cartao.Cartao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

@RestController
public class ClienteBloqueioController {
    private final EntityManager entityManager;

    public ClienteBloqueioController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/api/cartoes/{id}/bloqueio")
    @Transactional
    public ResponseEntity<?> bloqueia(@PathVariable(name = "id") String cartaoId,
                                      @RequestHeader("User-Agent") String userAgent,
                                      HttpServletRequest request) {

        Cartao cartao = entityManager.find(Cartao.class, cartaoId);
        if (cartao == null)
            return ResponseEntity.notFound().build();

        if (cartao.getBloqueiosCliente() != null)
            return ResponseEntity.unprocessableEntity().build();

        ClienteBloqueio bloqueio = new ClienteBloqueio(cartao, request.getRemoteAddr(), userAgent);
        cartao.bloqueia(bloqueio);
        entityManager.merge(cartao);

        return ResponseEntity.ok().build();
    }
}
