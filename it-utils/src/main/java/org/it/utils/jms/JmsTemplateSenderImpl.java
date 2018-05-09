package org.it.utils.jms;

import java.util.logging.Logger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.it.utils.exception.ExceptionUtil;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JmsTemplateSenderImpl implements JmsSender {
	private static Logger log = Logger.getLogger(JmsTemplateSenderImpl.class.getName());

	private JmsTemplate jmsTemplate;

	public JmsTemplateSenderImpl(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	//@Override
	public void send(String queue, final String message) {
		jmsTemplate.convertAndSend(queue, message);
	}

	//@Override
	public void send(String queue, final String message, final String correlationId) {
		jmsTemplate.convertAndSend(queue, message);
	}

	//@Override
	public void send(String queue, String message, final int priority) {
		this.send(queue, message, null, priority);

	}

	//@Override
	public void send(String queue, final String message, final String correlationId, final int priority) {
		try {

			jmsTemplate.send(queue, new MessageCreator() {
				public Message createMessage(Session session) throws JMSException {
					TextMessage textMessage = session.createTextMessage();
					textMessage.setJMSPriority(priority);
					if (correlationId != null && !"".equals(correlationId)){
						textMessage.setJMSCorrelationID(correlationId);
					}
					textMessage.setText(message);
					return textMessage;
				}
			});
		} catch (Exception e) {
			log.info("JmsTemplateContextSenderImpl sendToQueue exception: " + ExceptionUtil.getPrintStackTraceAsString(e));
		}

	}
}
