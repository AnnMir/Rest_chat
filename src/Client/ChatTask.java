package Client;

import java.util.TimerTask;

public class ChatTask extends TimerTask {
    private Client client;

    ChatTask(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        if (client.isLogin()) {
            client.getMessages(String.valueOf(client.getLastMessageId()));
        }
    }
}
