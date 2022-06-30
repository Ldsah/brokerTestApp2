
import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
    public class BrokerApp2 {
        public static void main(String[] args) throws Exception {
            ThreadExecutor(new Sender2(), false);
            ThreadExecutor(new Receiver2(), false);
        }
        public static void ThreadExecutor(Runnable runnable, boolean daemon) {
            Thread brokerThread = new Thread(runnable);
            brokerThread.setDaemon(daemon);
            brokerThread.start();
        }

        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }





    }

