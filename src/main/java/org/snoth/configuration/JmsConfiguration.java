package org.snoth.configuration;

import org.apache.camel.component.jms.JmsComponent;
import org.snoth.route.FromQuoteRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.MessageListenerContainer;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfiguration{

    public static final String MY_OKTOPUS = "MyOktopus";

    @Bean
    public JmsComponent initComponent() {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setAcceptMessagesWhileStopping(false);
        jmsComponent.setConnectionFactory(connectionFactory());
        return jmsComponent;
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        return null; //TODO MQ coneection factory initialization mqseries, activeMQ , ...
    }

    /*
     * Message listener container, used for invoking messageReceiver.onMessage on message reception.
     */
    @Bean
    public MessageListenerContainer iniRouteFromHub(){
        return initContainerListener("QUEUE_OR_TOPIC_TO_LISTEN"); //TODO
    }

    /*
     * Used for Sending Messages.
     */
    @Bean
    public JmsTemplate jmsTemplate(){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        template.setPubSubDomain(true); // false for a Queue, true for a Topic
        template.setDefaultDestinationName(MY_OKTOPUS);
        return template;
    }

    private MessageListenerContainer initContainerListener(String queue) {
        return initContainerListener(queue, false);
    }

    private MessageListenerContainer initContainerListener(String queue, boolean isTopic) {
        DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setDestinationName("QUEUE_OR_TOPIC_TO_LISTEN");
        container.setSubscriptionDurable(isTopic); //true for topis
        container.setClientId(MY_OKTOPUS);
        container.setMessageListener(null); //TODO
        container.setPubSubDomain(true); // false for a Queue, true for a Topic
        return container;
    }

}
