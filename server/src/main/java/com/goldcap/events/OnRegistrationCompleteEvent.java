package com.goldcap.events;

import com.goldcap.model.GoldcapUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent  extends ApplicationEvent {

    private String appUrl;
    private Locale locale;
    private GoldcapUser user;

    public OnRegistrationCompleteEvent(GoldcapUser user , Locale locale, String appUrl ) {
        super(user);
        this.appUrl = appUrl;
        this.locale = locale;
        this.user = user;
    }
}
