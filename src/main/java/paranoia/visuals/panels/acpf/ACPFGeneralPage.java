package paranoia.visuals.panels.acpf;

import daiv.networking.command.ParanoiaCommand;
import daiv.networking.command.acpf.request.DefineRequest;
import daiv.ui.AssetManager;
import daiv.ui.custom.ParanoiaMessage;
import daiv.ui.visuals.ParanoiaImage;
import daiv.ui.visuals.ParanoiaImageFilter;
import paranoia.visuals.custom.ParanoiaSectorFilter;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.JTextComponent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static daiv.Computer.getParanoiaResource;
import static daiv.ui.LayoutManager.createGrid;
import static java.awt.GridBagConstraints.CENTER;
import static java.awt.GridBagConstraints.LINE_START;
import static java.awt.GridBagConstraints.PAGE_END;
import static java.awt.GridBagConstraints.PAGE_START;
import static java.awt.GridBagConstraints.RELATIVE;
import static java.awt.GridBagConstraints.REMAINDER;

public class ACPFGeneralPage extends JPanel implements ACPFPage {

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
    private final ParanoiaImage profilePicture;
    private String profilePath;
    private final ACPFPanel main;

    public ACPFGeneralPage(ACPFPanel main) {
        setLayout(new BorderLayout());

        setUpComponents();
        this.main = main;

        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(profilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        profilePicture = new ParanoiaImage(image, true);
        profilePicture.setPreferredSize(new Dimension(200, 200));

        Font generalFont = AssetManager.getFont(18);
        textFields.forEach(tf -> tf.setFont(generalFont));

        JPanel standardPanel = new JPanel();
        standardPanel.setLayout(new GridBagLayout());
        standardPanel.add(lbTitle, createGrid(15, 15, 15, 15).at(0, 0, REMAINDER, 1).anchor(PAGE_START).get());
        standardPanel.add(new JLabel("Name:"), createGrid(15,15,2,2).at(0, RELATIVE).anchor(LINE_START).get());
        standardPanel.add(new JLabel("Gender:"), createGrid(2, 15, 2, 2).at(0, RELATIVE).anchor(LINE_START).get());
        standardPanel.add(new JLabel("Home Sector:"), createGrid(2, 15, 2, 2).at(0, RELATIVE).anchor(LINE_START).get());
        standardPanel.add(new JLabel("Personality:"), createGrid(15, 2, 10, 2).at(0, RELATIVE, 2, 1).anchor(CENTER).get());
        standardPanel.add(tfName, createGrid(15, 2, 2,2).at(1, 1, 2 ,1).get());
        standardPanel.add(tfGender, createGrid().at(1, 2, 2 ,1).get());
        standardPanel.add(tfSector, createGrid().at(1, 3).get());
        standardPanel.add(lbError, createGrid().at(2, 3).get());
        for (int i = 0; i < personalities.length; i++) {
            standardPanel.add(personalities[i], createGrid(2, 15, 2, 2).at(0, 5 + i, 3 ,1).get());
        }
        standardPanel.add(profilePicture, createGrid(15, 15, 2, 15).at(3,1, 1, 7).anchor(CENTER).get());
        standardPanel.add(btnPicture, createGrid().at(3, RELATIVE, 1, 1).anchor(PAGE_END).get());


        add(standardPanel, BorderLayout.CENTER);
        add(main.createButtonPanel(this,false, true), BorderLayout.SOUTH);

    }

    private void setUpComponents() {
        //Set label
        lbTitle.setFont(AssetManager.getFont(25, true, true, false));
        AbstractDocument sector = (AbstractDocument) tfSector.getDocument();
        sector.setDocumentFilter(new ParanoiaSectorFilter());
        lbError.setForeground(new Color(255,0,0,0));

        //Custom profile picture
        try {
            profilePath = getParanoiaResource("clones/clone0.png");
        } catch (IOException e) {
            ParanoiaMessage.error(e);
        }
        btnPicture.addActionListener( e -> profilePath = chooseProfilePicture(profilePath));

        //Gathering text fields
        textFields.add(tfName);
        textFields.add(tfGender);
        textFields.add(tfSector);
        textFields.addAll(Arrays.asList(personalities));
    }

    private String chooseProfilePicture(String profilePath) {
        String newPath = profilePath;
        JFileChooser chooser = new JFileChooser(profilePath);
        chooser.addChoosableFileFilter(new ParanoiaImageFilter());
        chooser.setAcceptAllFileFilterUsed(false);
        int response = chooser.showOpenDialog(this);
        if(response == JFileChooser.APPROVE_OPTION) {
            newPath = chooser.getSelectedFile().getAbsolutePath();
        }
        try {
            profilePicture.changeImage(ImageIO.read(new File(newPath)));
        } catch (IOException e) {
            ParanoiaMessage.error(e);
        }

        return newPath;
    }

    @Override
    public boolean validatePage() {
        if(tfSector.getText().equals("THA") || tfSector.getText().length() < 3) {
            hasEmptyTF();
            tfSector.setBorder(BorderFactory.createLineBorder(Color.RED));
            lbError.setForeground(new Color(255,0,0,255));
            return false;
        } else {
            tfSector.setBorder(defaultBorder);
            lbError.setForeground(new Color(255,0,0,0));
            if(hasEmptyTF()) {
                String[] personalitiesText = Arrays.stream(personalities)
                    .map(JTextComponent::getText).toArray(String[]::new);
                main.sendResponse(
                    new DefineRequest(
                        tfName.getText(),
                        tfSector.getText(),
                        tfGender.getText(),
                        personalitiesText,
                        ParanoiaCommand.parseImage(profilePicture.getImage())
                    )
                );
                return true;
            } else return false;
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
