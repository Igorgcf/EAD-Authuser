package com.ead.authuser.publishers;

import com.ead.authuser.dto.EventDTO;
import com.ead.authuser.enums.ActionType;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventPublisher {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${ead.broker.exchange.userEvent}")
    private String exchangeUserEvent;

    public void publishEvent(EventDTO dto, ActionType actionType){
        dto.setActionType(actionType.toString());
        rabbitTemplate.convertAndSend(exchangeUserEvent, "", dto);
    }
}
