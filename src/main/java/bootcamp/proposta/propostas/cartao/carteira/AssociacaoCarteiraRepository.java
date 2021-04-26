package bootcamp.proposta.propostas.cartao.carteira;

import bootcamp.proposta.propostas.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociacaoCarteiraRepository extends JpaRepository<AssociacaoCarteira, Long> {
    boolean existsByCartaoAndCarteira(Cartao cartao, CarteiraEnum carteira);
}
