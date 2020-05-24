package paranoia.visuals.panels;

import paranoia.Paranoia;
import paranoia.services.plc.AssetManager;
import paranoia.visuals.custom.ParanoiaImage;
import paranoia.visuals.custom.ParanoiaSectorFilter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.PAGE_END;
import static java.awt.GridBagConstraints.PAGE_START;
import static java.awt.GridBagConstraints.RELATIVE;
import static java.awt.GridBagConstraints.REMAINDER;
import static paranoia.services.plc.LayoutManager.createGrid;

public class ACPFPanel extends JPanel {

    private final CardLayout layout = new CardLayout();
    private final JTextField tfName = new JTextField(10);
    private final JTextField tfGender = new JTextField(10);
    private final JTextField tfSector = new JTextField(3);
    private final JTextField[] personalities = new JTextField[] {
        new JTextField(20),
        new JTextField(20),
        new JTextField(20)
    };
    private final JButton btnPicture = new JButton("Change Picture");
    private final JLabel lbTitle = new JLabel("ALPHA COMPLEX PERSONALITY FORM");
    private final JLabel lbError = new JLabel("This sector does not exist");
    private final Border defaultBorder = tfName.getBorder();
    private final List<JTextField> textFields = new ArrayList<>();

    public ACPFPanel() {
        setLayout(layout);

        //Set label
        lbTitle.setFont(AssetManager.getFont(25, true, true, false));
        AbstractDocument sector = (AbstractDocument) tfSector.getDocument();
        sector.setDocumentFilter(new ParanoiaSectorFilter());
        lbError.setForeground(new Color(255,0,0,0));

        //Gathering text fields
        textFields.add(tfName);
        textFields.add(tfGender);
        textFields.add(tfSector);
        textFields.addAll(Arrays.asList(personalities));

        //Adding pages
        add(createFirstPage());
        add(createSecondPage());
        layout.first(this);
    }

    private JPanel createFirstPage() {
        JPanel firstPage = new JPanel(new BorderLayout());
        firstPage.add(createStandardACPFPanel(), BorderLayout.CENTER);
        firstPage.add(createButtonPanel(false, true), BorderLayout.SOUTH);
        return firstPage;
    }

    private JPanel createStandardACPFPanel() {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(Paranoia.getParanoiaResource("clones/clone0.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ParanoiaImage profile = new ParanoiaImage(image, true);
        profile.setPreferredSize(new Dimension(200, 200));

        JPanel standardPanel = new JPanel();
        standardPanel.setLayout(new GridBagLayout());
        standardPanel.add(lbTitle, createGrid(15, 15, 15, 15).at(0, 0, REMAINDER, 1).anchor(PAGE_START).get());
        standardPanel.add(new JLabel("Name:"), createGrid(15,15,2,2).at(0, RELATIVE).get());
        standardPanel.add(new JLabel("Gender:"), createGrid(2, 15, 2, 2).at(0, RELATIVE).get());
        standardPanel.add(new JLabel("Home Sector:"), createGrid(2, 15, 2, 2).at(0, RELATIVE).get());
        standardPanel.add(new JLabel("Personality:"), createGrid(15, 2, 10, 2).at(0, RELATIVE, 2, 1).anchor(CENTER).get());
        standardPanel.add(tfName, createGrid(15, 2, 2,2).at(1, 1, 2 ,1).get());
        standardPanel.add(tfGender, createGrid().at(1, 2, 2 ,1).get());
        standardPanel.add(tfSector, createGrid().at(1, 3).get());
        standardPanel.add(lbError, createGrid().at(2, 3).get());
        for (int i = 0; i < personalities.length; i++) {
            standardPanel.add(personalities[i], createGrid(2, 15, 2, 2).at(0, 5 + i, 3 ,1).get());
        }
        standardPanel.add(profile, createGrid(15, 15, 2, 15).at(3,1, 1, 7).anchor(CENTER).get());
        standardPanel.add(btnPicture, createGrid().at(3, RELATIVE, 1, 1).anchor(PAGE_END).get());

        return standardPanel;
    }

    private JPanel createSecondPage() {
        JPanel secondPage = new JPanel(new BorderLayout());
        secondPage.add(new JLabel("SECOND"), BorderLayout.CENTER);
        secondPage.add(createButtonPanel(true, false), BorderLayout.SOUTH);
        return secondPage;
    }

    private JPanel createButtonPanel(boolean hasPrev, boolean hasNext) {
        JPanel panelButtons = new JPanel(new FlowLayout());
        if(hasPrev) panelButtons.add(createPrevButton());
        panelButtons.add(Box.createHorizontalGlue());
        if(hasNext) panelButtons.add(createNextButton());
        return panelButtons;
    }

    private JButton createPrevButton() {
        JButton btnPrev = new JButton("Previous");
        btnPrev.addActionListener(e -> layout.previous(this));
        return btnPrev;
    }

    private JButton createNextButton() {
        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(e -> {
            if(validatePage())
                layout.next(this);
        });
        return btnNext;
    }

    private boolean validatePage() {
        if(tfSector.getText().equals("THA")) {
            hasEmptyTF();
            tfSector.setBorder(BorderFactory.createLineBorder(Color.RED));
            lbError.setForeground(new Color(255,0,0,255));
            return false;
        } else {
            tfSector.setBorder(defaultBorder);
            lbError.setForeground(new Color(255,0,0,0));
            return hasEmptyTF();
        }
    }

    private boolean hasEmptyTF() {
        boolean valid = true;
        for (JTextField field : textFields) {
            if (field.getText().isEmpty()) {
                valid = false;
                field.setBorder(BorderFactory.createLineBorder(Color.RED));
            } else {
                field.setBorder(defaultBorder);
            }
        }
        return valid;
    }

}
