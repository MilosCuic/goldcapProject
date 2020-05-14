package com.goldcap.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VerificationToken {

    private static final int EXPIRATION = 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne(targetEntity = GoldcapUser.class , fetch = FetchType.EAGER)
    @JoinColumn(nullable = false , name = "user_id")
    private GoldcapUser user;

    private Date expiryDate = calculateExpiryDate(EXPIRATION);

    private Date calculateExpiryDate(int expiryTimeInMinutes){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Timestamp(calendar.getTime().getTime()));
        calendar.add(Calendar.MINUTE , expiryTimeInMinutes);
        return new Date(calendar.getTime().getTime());
    }

    public VerificationToken(String token , GoldcapUser user){
        this.token = token;
        this.user = user;
    }

}
