package com.goldcap.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Getter
@Setter
@Proxy(lazy=false)
@Table(name="goldcap_oders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false , unique = true , updatable = false)
    @NotBlank
    private String UUID;

    @Column(nullable = false)
    @Min(1)
    @NotNull(message = "Price cant be null")
    private Double price;

    @Column(nullable = false)
    @NotNull(message = "gold amount cant be null")
    @Min(100000)
    private Double goldAmount;

    @Column
    private Double pricePer100k;


    @Column
    @NotBlank
    @Size(min = 1 , max = 50)
    private String buyerName;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    private Realm realm;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.PERSIST)
    private GoldcapUser goldcapUser;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateRequested;

    @Column
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateSubmitted;

    @PrePersist
    public void onCreate() {
        this.dateSubmitted = new Date();
    }

}
