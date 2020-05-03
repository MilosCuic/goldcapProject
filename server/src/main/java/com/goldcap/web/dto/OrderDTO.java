package com.goldcap.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderDTO {

    private Long id;
    private Double price;
    private Double goldAmount;
    private String buyerName;
    private Long realmId;
    private String realmName;
    private Long goldcapUserId;
    private String goldcapUserUsername;
    private String goldcapUserEmail;
    private Date dateRequested;
    private Date dateSubmitted;
    private String UUID;
}
