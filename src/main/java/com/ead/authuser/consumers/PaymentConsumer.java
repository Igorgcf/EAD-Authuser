package com.ead.authuser.consumers;

import com.ead.authuser.dto.PaymentEventDTO;
import com.ead.authuser.enums.PaymentControl;
import com.ead.authuser.services.impl.RoleServiceImpl;
import com.ead.authuser.services.impl.UserServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class PaymentConsumer {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private RoleServiceImpl roleService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${ead.broker.queue.paymentEventQueue.name}", durable = "true"),
            exchange = @Exchange(value = "${ead.broker.exchange.paymentEventExchange}", type = ExchangeTypes.FANOUT, ignoreDeclarationExceptions = "true" )))
    public void listenPaymentEvent(@Payload PaymentEventDTO paymentEventDTO) {

        log.debug("Payment event received: {}", paymentEventDTO);

        userService.updateAfterPayment(paymentEventDTO.getUserId(), PaymentControl.valueOf(paymentEventDTO.getPaymentControl()));

        log.debug("Payment event processed: {}", paymentEventDTO);

    }
}
