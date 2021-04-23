package bootcamp.proposta.propostas.cartao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartaoRepository extends JpaRepository<Cartao, String> {
    List<Cartao> findByEstadoAndBloqueiosClienteNotNull(CartaoEstado estado);
}
