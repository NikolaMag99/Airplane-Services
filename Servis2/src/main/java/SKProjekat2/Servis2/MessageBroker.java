package SKProjekat2.Servis2;

import org.apache.activemq.broker.BrokerService;

public class MessageBroker {

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        // configure the broker
        broker.addConnector("tcp://localhost:61616");
        broker.start();
    }
}
