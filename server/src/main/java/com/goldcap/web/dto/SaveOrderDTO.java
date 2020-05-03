package com.goldcap.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.goldcap.model.GoldcapUser;
import com.goldcap.model.Realm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Getter
@Setter
public class SaveOrderDTO {

    private Long id;
    @Min(1)
    @NotNull(message = "Price cant be null")
    private Double price;
    @NotNull(message = "gold amount cant be null")
    @Min(100000)
    private Double goldAmount;
    @NotBlank
    @Size(min = 1 , max = 50)
    private String buyerName;
    @NotNull
    @Min(1)
    private Long realmId;
    private Long goldcapUserId;
    private Date dateRequested;
    private Date dateSubmitted;
    private String UUID;


}
