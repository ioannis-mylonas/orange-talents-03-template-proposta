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

    /**
     * @Deprecated Apenas para uso do Hibernate
     */
    public Proposta() { }

    public Proposta(@NotBlank String documento,
                    @NotBlank @Email String email,
                    @NotBlank String nome,
                    @NotBlank String endereco,
                    @NotNull @DecimalMin(value = "0.00") BigDecimal salario) {

        this.documento = documento;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public Long getId() {
        return id;
    }
}
