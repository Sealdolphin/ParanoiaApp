package paranoia.visuals.custom;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.Toolkit;

public class ParanoiaSectorFilter extends DocumentFilter {

    @Override
    public void insertString(FilterBypass fb, int offs, String str, AttributeSet a)
        throws BadLocationException {

        if ((fb.getDocument().getLength() + str.length()) <= 3)
            super.insertString(fb, offs, str.toUpperCase(), a);
        else
            Toolkit.getDefaultToolkit().beep();
    }

    @Override
    public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a)
        throws BadLocationException {
        if ((fb.getDocument().getLength() + str.length()
            - length) <= 3)
            super.replace(fb, offs, length, str.toUpperCase(), a);
        else
            Toolkit.getDefaultToolkit().beep();
    }

}
