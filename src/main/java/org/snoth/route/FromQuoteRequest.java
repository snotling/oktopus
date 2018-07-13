package org.snoth.route;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class FromQuoteRequest {

    private final JmsTemplate jmsTemplate;

    public FromQuoteRequest(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = "${queue.quote.in}")
    public void onMessage(String message) {

        jmsTemplate.convertAndSend("", message);

    }

}
