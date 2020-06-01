package paranoia.visuals.messages;

import paranoia.core.Computer;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ParanoiaMessage {

    public static final int ERROR_LINE_LIMIT = 15;

    private static final String[] errorHeaders = {
        "Cerebral CoreTech error",
        "Nearby mutant traitors attacked",
        "The Alpha Complex is in danger",
        "The Computer initiated the !@^#% protocol",
        "Consequences of the !@^&@#% incident, which did NOT happen",
        "You've been entered sector #@&, which does NOT exist"
    };

    public static void error(String errorMsg) {
        error(errorMsg, null);
    }

    public static void error(Throwable error) {
        String stack = Arrays
            .stream(error.getStackTrace())
            .map(StackTraceElement::toString)
            .limit(ERROR_LINE_LIMIT)
            .collect(Collectors.joining("\n"));
        String message = error.getLocalizedMessage() + "\n" + stack;
        if(error.getStackTrace().length > ERROR_LINE_LIMIT)
            message += "...";
        error(message);
    }

    public static void error(String errorMsg, Component parent) {
        JOptionPane.showMessageDialog(
            parent,
            errorMsg,
            Computer.randomItem(errorHeaders),
            JOptionPane.ERROR_MESSAGE
        );
    }

    public static void info(String info) {
        JOptionPane.showMessageDialog(
            null,
            info,
            "A message from the Computer",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static boolean confirm(String question, Component parent) {
        return JOptionPane.showConfirmDialog(
            parent, question, "Confirmation",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    public static String input(String question) {
        return JOptionPane.showInputDialog(null, question, "A question from the Computer", JOptionPane.QUESTION_MESSAGE);
    }
}
