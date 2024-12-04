package by.test.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table (name = "wallets1")
public class Wallet {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

//    @Column (name = "id")
    private Integer id;

    @Enumerated(EnumType.STRING)
//    @Column (name = "type")
    private OperationType type;
    private BigDecimal amount;

    public Wallet (OperationType operationType, BigDecimal amount) {
        this.type = operationType;
        this.amount = amount;
    }
}
