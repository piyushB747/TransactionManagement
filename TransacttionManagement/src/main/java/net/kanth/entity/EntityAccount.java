package net.kanth.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kanth.enums.EnumAccountType;
import net.kanth.enums.EnumCardType;
import net.kanth.utily.AccountEnumConverter;
import net.kanth.utily.CardEnumConverter;


@NamedQueries(
		{
				@NamedQuery(name = "findAccountByAccountNo", 
						query = "Select COUNT(a) from EntityAccount a where a.accountNo =:newAccountNumber", resultClass = Long.class),
				@NamedQuery(name = "findByAccountNo", 
				        query = "SELECT a FROM EntityAccount a where a.accountNo =:newAccountNumber", resultClass = EntityAccount.class

				)
		}
)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="account_tbl")
public class EntityAccount {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_seq_gen")
    @SequenceGenerator(name="account_seq_gen", sequenceName = "account_sequence", allocationSize = 50)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String accountNo;
    
    private String accountName;
    
    private String bankName;
    
    @Convert(converter = CardEnumConverter.class)
    private EnumCardType cardType;
    
    @Convert(converter = AccountEnumConverter.class)
    private EnumAccountType accountType;
    
    private BigDecimal amount; 

    // Fixed: Must match one of the field names in EntityPayment
    @JsonManagedReference
    @OneToMany(mappedBy = "accountTo", cascade = CascadeType.ALL)
    private List<EntityPayment> receivedPayments;

    @JsonManagedReference
    @OneToMany(mappedBy = "accountFrom", cascade = CascadeType.ALL)
    private List<EntityPayment> sentPayments; 
    
    @CreationTimestamp
    private LocalDateTime creation;
    
    @UpdateTimestamp
    private LocalDateTime updation;


}
