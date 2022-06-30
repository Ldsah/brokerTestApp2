import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver2 implements Runnable, ExceptionListener {
    public void run() {
        try {
            // Создание connectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "admin",
                    "tcp://127.0.0.1:61616"); //Если мы реализуем //brocker на отдельной машине, то адрес должен быть реальным ip той машины
            // Создание подключения
            Connection connection = connectionFactory.createConnection();
            connection.start();
            connection.setExceptionListener(this);
            // Создание сессии
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // Из какой очереди ждать сообщений?
            Destination destination = session.createQueue("ROCKET");
            // Создание приёмщика сообщений
            MessageConsumer consumer = session.createConsumer(destination);
            // Смена только 1000 единиц времени
            while (true) {
                Message message = consumer.receive(1000);
                // Вывод
                if (message instanceof TextMessage) {
                    String text = ((TextMessage) message).getText();
                    System.out.println("Brave your hearts, enemy is near! I see " + text + " rocket!");
                    if ("Quit".equals(text)) {
                        break;
                    }
                } else {
                    System.out.println("Brave your hearts, enemy is near! I see " + message + " rocket!");
                }
            }
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
    @Override
    public synchronized void onException(JMSException ex) {
        System.out.println("JMS Exception occured. Shutting down client.");
    }
}