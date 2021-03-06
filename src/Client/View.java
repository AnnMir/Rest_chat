package Client;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;

public class View {
    private JTextArea chatJTextArea;
    private JTextArea messageJTextArea;
    private JTextArea userIdJTextArea;
    private JLabel clientTokenLabel;
    private JButton sendButton;
    private JButton getAllUsersButton;
    private JButton getOneUserButton;
    private JButton exitButton;
    private JButton enterButton;
    private Client mainClient;
    private GridBagConstraints c;
    private JFrame mainframe;

    View(final Client client) {
        mainClient = client;
        mainframe = new JFrame("Rest_Chat");

        mainframe.setSize(new Dimension(600, 400));
        mainframe.getContentPane().setBackground(Color.CYAN);
        mainframe.setVisible(true);
        mainframe.setLayout(new GridBagLayout());
        mainframe.setLocationRelativeTo(null);
        mainframe.setResizable(false);
        c = new GridBagConstraints();

        initJLabel();
        initChatJTextArea();
        initMessageJTextArea();
        initSendButton();
        initGetAllUsersButton();
        initUserIdJTextArea();
        initGetOneUserButton();
        initExitButton();
        initEnterButton();

        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainframe.setVisible(true);
    }

    private void initJLabel() {
        clientTokenLabel = new JLabel();

        c.fill = GridBagConstraints.BOTH;
        c.gridheight = 1;
        c.gridwidth = GridBagConstraints.REMAINDER;
        c.gridx = 0;
        c.gridy = 0;
        c.ipadx = 0;
        c.ipady = 0;
        c.weightx = 1.0;
        c.weighty = 0.0;
        mainframe.add(clientTokenLabel, c);
    }

    private void initChatJTextArea() {
        chatJTextArea = new JTextArea("");
        chatJTextArea.setEditable(false);
        chatJTextArea.setLineWrap(true);
        chatJTextArea.setFont(new Font("Arial", Font.ITALIC, 15));
        chatJTextArea.setFocusable(false);
        chatJTextArea.setBorder(BorderFactory.createMatteBorder(2, 2, 1, 2, Color.PINK));
        c.weighty = 5.0;
        c.gridx = 1;
        c.gridy = 1;
        mainframe.add(new JScrollPane(chatJTextArea), c);
    }

    private void initMessageJTextArea() {
        messageJTextArea = new JTextArea("");
        messageJTextArea.setFont(new Font("Arial", Font.ITALIC, 15));
        messageJTextArea.setLineWrap(true);
        messageJTextArea.requestFocus();
        messageJTextArea.setBorder(BorderFactory.createMatteBorder(1, 2, 2, 2, Color.PINK));
        c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 2;
        mainframe.add(new JScrollPane(messageJTextArea), c);
    }



    private void initSendButton() {
        sendButton = new JButton("Send");
        sendButton.setFont(new Font("Arial", Font.ITALIC, 15));
        sendButton.setFocusable(false);
        sendButton.setEnabled(false);
        sendButton.addActionListener(e -> {
            String message = messageJTextArea.getText();
            if ("".equals(message)) {
                JOptionPane.showMessageDialog(null, "Enter your message");
            } else {
                mainClient.postMessage(message);
                messageJTextArea.setText("");
            }
        });
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 3;
        c.ipadx = 1;
        mainframe.add(sendButton, c);
    }

    private void initGetAllUsersButton() {
        getAllUsersButton = new JButton("Get users");
        getAllUsersButton.setFont(new Font("Arial", Font.ITALIC, 15));
        getAllUsersButton.setFocusable(false);
        getAllUsersButton.setEnabled(false);

        getAllUsersButton.addActionListener(e -> mainClient.getAllUsers());
        c.weightx = 0.5;
        c.gridx = 1;
        c.gridy = 4;
        mainframe.add(getAllUsersButton, c);
    }

    private void initUserIdJTextArea() {
        userIdJTextArea = new JTextArea("");
        userIdJTextArea.setFont(new Font("Arial", Font.ITALIC, 15));
        userIdJTextArea.setLineWrap(true);
        userIdJTextArea.requestFocus();
        userIdJTextArea.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.PINK));
        userIdJTextArea.setEditable(false);
        c.weighty = 0.3;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 5;
        mainframe.add(new JScrollPane(userIdJTextArea), c);
    }

    private void initGetOneUserButton() {
        getOneUserButton = new JButton("Get user");
        getOneUserButton.setFont(new Font("Arial", Font.ITALIC, 15));
        getOneUserButton.setFocusable(false);
        getOneUserButton.setEnabled(false);
        getOneUserButton.addActionListener(e -> {
            try {
                int id = Integer.valueOf(userIdJTextArea.getText());
                mainClient.getOneUser(id);
                userIdJTextArea.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter int");
                userIdJTextArea.setText("");
            }
        });
        c.weighty = 0.3;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 5;
        mainframe.add(getOneUserButton, c);
    }

    private void initExitButton() {
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Arial", Font.ITALIC, 15));
        exitButton.setFocusable(false);
        exitButton.setEnabled(false);
        exitButton.addActionListener(e -> mainClient.postLogout());
        c.weighty = 0.3;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 6;
        mainframe.add(exitButton, c);
    }

    private void initEnterButton() {
        enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> {
            if (!messageJTextArea.getText().equals("")) {
                mainClient.postLogin(messageJTextArea.getText());
                messageJTextArea.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "Enter your login");
            }
        });

        enterButton.setFont(new Font("Arial", Font.ITALIC, 15));
        enterButton.setFocusable(false);
        c.weighty = 0.3;
        c.weightx = 0.5;
        c.gridwidth = 1;
        c.gridx = 1;
        c.gridy = 6;
        mainframe.add(enterButton, c);
    }

    public void updateChat(String name) {
        chatJTextArea.append(name);
        chatJTextArea.append("\n");
        chatJTextArea.setCaretPosition(chatJTextArea.getDocument().getLength());
    }

    public void showUsers(String json) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");
            Iterator<JSONObject> iterator = jsonArray.iterator();
            chatJTextArea.append("\nAll users:\n");
            chatJTextArea.setCaretPosition(chatJTextArea.getDocument().getLength());
            while (iterator.hasNext()) {
                chatJTextArea.append("" + iterator.next());
                chatJTextArea.append("\n");
                chatJTextArea.setCaretPosition(chatJTextArea.getDocument().getLength());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void showMessages(String json) {
        JSONParser parser = new JSONParser();
        int countOfMessages = mainClient.getLastMessageId();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(json);
            JSONArray jsonArray = (JSONArray) jsonObject.get("messages");
            if (jsonArray.size() == 0) {
                return;
            }
            Iterator<JSONObject> iterator = jsonArray.iterator();
            chatJTextArea.append("\nAll messages: \n");
            chatJTextArea.setCaretPosition(chatJTextArea.getDocument().getLength());
            while (iterator.hasNext()) {
                countOfMessages++;
                chatJTextArea.append("" + iterator.next());
                chatJTextArea.append("\n");
                chatJTextArea.setCaretPosition(chatJTextArea.getDocument().getLength());
            }
            mainClient.setLastMessageId(countOfMessages);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void successLogin(final String token) {
        mainClient.setClientToken(token);
        clientTokenLabel.setText("");
        enterButton.setEnabled(false);
        exitButton.setEnabled(true);
        getAllUsersButton.setEnabled(true);
        getOneUserButton.setEnabled(true);
        userIdJTextArea.setEditable(true);
        sendButton.setEnabled(true);
    }

    public void successLogout() {
        mainClient.setLastMessageId(0);
        mainClient.setClientToken("none");
        mainClient.closeSocket();
        enterButton.setEnabled(true);
        exitButton.setEnabled(false);
        getAllUsersButton.setEnabled(false);
        getOneUserButton.setEnabled(false);
        userIdJTextArea.setEditable(false);
        sendButton.setEnabled(false);
    }
}

