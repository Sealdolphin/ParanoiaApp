package paranoia.visuals.panels;

import paranoia.core.ParanoiaPlayer;
import paranoia.services.hpdmc.ParanoiaController;
import paranoia.services.technical.command.ChatCommand;
import paranoia.visuals.messages.ParanoiaMessage;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ChatPanel extends JPanel implements ChatCommand.ParanoiaChatListener {

    private final JTextArea chatWindow = new JTextArea(15,30);

    public ChatPanel(ParanoiaPlayer clone, ParanoiaController unit) {
        setLayout(new BorderLayout());

        chatWindow.setEditable(false);
        chatWindow.setWrapStyleWord(true);
        chatWindow.setLineWrap(true);

        JTextField writer = new JTextField(30);
        JButton btnSend = new JButton("SEND");

        btnSend.addActionListener(l -> {
            String sender = clone.getFullName();
            String message = writer.getText();
            String time = DateTimeFormatter.ofPattern("hh:mm:ss").format(LocalTime.now());
            ChatCommand chatMessage = new ChatCommand(sender, message, time, null);
            if(unit.sendCommand(chatMessage))
                digest(sender, message, time);
            writer.setText("");
        });

        writer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnSend.doClick();
                    writer.requestFocus();
                }
            }
        });

        JPanel sendPanel = new JPanel();
        sendPanel.add(writer);
        sendPanel.add(btnSend);

        add(sendPanel, BorderLayout.SOUTH);
        add(chatWindow, BorderLayout.CENTER);
    }

    @Override
    public void digest(String sender, String message, String timestamp) {
        chatWindow.append("[" + timestamp + "] " + sender + ": " + message + "\n");
        if(chatWindow.getLineCount() > 15) {
            try {
                chatWindow.replaceRange("", 0, chatWindow.getLineEndOffset(0));
            } catch (BadLocationException e) {
                e.printStackTrace();
                ParanoiaMessage.error(e);
            }
        }
    }
}
