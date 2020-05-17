package paranoia.visuals.messages;

import paranoia.core.Computer;

import javax.swing.JOptionPane;
import java.awt.Component;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ParanoiaError {

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
            .collect(Collectors.joining("\n"));
        String message = error.getLocalizedMessage() + "\n" + stack;
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
}
