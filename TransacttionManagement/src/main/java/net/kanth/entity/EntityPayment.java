package net.kanth.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.kanth.enums.EnumCardType;
import net.kanth.utily.CardEnumConverter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="payment_tbl")
public class EntityPayment {
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_seq_gen")
    @SequenceGenerator(name="payment_seq_gen", sequenceName = "payment_seq", allocationSize = 60)
    private Long id;
    
    private BigDecimal transactionAmount;
    
    @Convert(converter = CardEnumConverter.class)
    private EnumCardType card;
    
    @CreationTimestamp
    private LocalDateTime dateTime;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER) // Changed to LAZY for better performance
    @JoinColumn(name="id_account_to", referencedColumnName = "id")
    private EntityAccount accountTo;
    
    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_account_from", referencedColumnName = "id")
    private EntityAccount accountFrom;
    
    private Long accountNoFrom;
    
    private Long accountNoTo;

}
