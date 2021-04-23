package bootcamp.proposta.propostas;

import bootcamp.proposta.propostas.cartao.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    boolean existsByDocumento(String documento);
    List<Proposta> findByCartaoAndEstadoProposta(Cartao cartao, EstadoProposta estado);
    List<Proposta> findByEstadoProposta(EstadoProposta estado);
}
