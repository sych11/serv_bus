package org.it.utils.jms;

public interface JmsSender {
	void send(String queue, String message);
	void send(String queue, String message, int priority);
	void send(String queue, String message, String correlationId);
	void send(String queue, String message, String correlationId, int priority);
	
}
