package bootcamp.proposta.propostas;

import bootcamp.proposta.validators.Documento;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Entity
public class Proposta {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Documento
    @Column(nullable = false)
    private String documento;
    @NotBlank
    @Email
    @Column(nullable = false)
    private String email;
    @NotBlank
    @Column(nullable = false)
    private String nome;
    @NotBlank
    @Column(nullable = false)
    private String endereco;
    @NotNull
    @DecimalMin(value = "0.00")
    @Column(nullable = false)
    private BigDecimal salario;
    @Enumerated(EnumType.STRING)
    private EstadoProposta estadoProposta;
    private String cartaoId;

    /**
     * @Deprecated Apenas para uso do Hibernate
     */
    public Proposta() { }

    public Proposta(@NotBlank String documento,
                    @NotBlank @Email String email,
                    @NotBlank String nome,
                    @NotBlank String endereco,
                    @NotNull @DecimalMin(value = "0.00") BigDecimal salario,
                    EstadoProposta estadoProposta) {

        this.documento = formataDocumento(documento);
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.estadoProposta = estadoProposta;
    }

    public Long getId() {
        return id;
    }

    public String getDocumento() {
        return documento;
    }

    public String getNome() {
        return nome;
    }

    public EstadoProposta getEstadoProposta() {
        return estadoProposta;
    }

    public void atualiza(EstadoProposta estadoProposta) {
        this.estadoProposta = estadoProposta;
    }

    public void adicionaCartao(String cartaoId) {
        this.cartaoId = cartaoId;
    }

    public static String formataDocumento(String documento) {
        if (documento == null) return null;
        return documento.replaceAll("[./-]", "");
    }
}
