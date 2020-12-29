package paranoia.visuals.panels.acpf;

import daiv.networking.command.acpf.request.SkillRequest;
import daiv.networking.command.acpf.response.SkillResponse;
import daiv.ui.AssetManager;
import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.visuals.custom.ParanoiaAttributePanel;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static daiv.ui.LayoutManager.panelOf;

public class ACPFStatPage extends JPanel implements
    ACPFPage,
    ParanoiaAttributePanel.ParanoiaSkillButtonListener,
    SkillResponse.ParanoiaSkillListener,
    ParanoiaListener<ParanoiaAttribute> {

    private final JLabel lbValue = new JLabel("");
    private final JTextArea lbChoose =  new JTextArea(2,5);
    private final JButton btnSend = new JButton("SEND");
    private JPanel statPanel;
    private final List<ParanoiaAttributePanel> attributePanels = new ArrayList<>();
    private boolean lastChoice = false;

    private static final String txtChoose = "Choose a skill with a value of";
    private static final String txtIdle = "Waiting for another player to choose...";
    private static final String txtFinal = "You can click next, Troubleshooter!";

    public ACPFStatPage(ACPFPanel main) {
        setLayout(new BorderLayout());
        btnSend.setEnabled(false);
        btnSend.addActionListener( e -> {
            btnSend.setEnabled(false);
            ParanoiaAttributePanel selected = attributePanels.stream()
                .filter(ParanoiaAttributePanel::isSelected).findAny().orElse(null);
            if(selected == null) return;

            attributePanels.forEach(ParanoiaAttributePanel::reset);
            attributePanels.forEach(ParanoiaAttributePanel::lock);
            lbChoose.setText(txtIdle);

            main.sendResponse(new SkillRequest(selected.getName()));
        });

        statPanel = createStatsTable(ParanoiaAttribute.getDefaultModel());
        add(createInfoPanel(), BorderLayout.EAST);
        add(statPanel, BorderLayout.CENTER);
        add(main.createButtonPanel(this, true, true), BorderLayout.SOUTH);
    }

    @Override
    public boolean validatePage() {
        return lastChoice;
    }

    private JPanel createStatsTable(Collection<ParanoiaAttribute> updatedModel) {
        final int COLS = Stat.values().length;
        JPanel table = new JPanel(new GridLayout(0,COLS,5,0));

        updatedModel.stream().filter(ParanoiaAttribute::isStat).forEach(stat -> {
            ParanoiaAttributePanel statPanel = (ParanoiaAttributePanel) stat.getVisual();
            statPanel.setEnabled(false);
            table.add(statPanel);
        });

        updatedModel.stream().filter(ParanoiaAttribute::isSkill).forEach(skill -> {
            ParanoiaAttributePanel skillPanel = (ParanoiaAttributePanel) skill.getVisual();
            skillPanel.setListener(this);
            skillPanel.lock();
            table.add(skillPanel);
            attributePanels.add(skillPanel);
        });
        return table;
    }

    private JPanel createInfoPanel() {
        lbValue.setFont(AssetManager.getBoldFont(30));
        lbValue.setForeground(new Color(16, 27, 95));

        lbChoose.setText(txtIdle);
        lbChoose.setLineWrap(true);
        lbChoose.setWrapStyleWord(true);
        lbChoose.setOpaque(false);
        lbChoose.setEditable(false);
        lbChoose.setFont(AssetManager.getItalicFont(17));
        lbChoose.setMaximumSize(
            new Dimension(
                lbChoose.getMaximumSize().width,
                getFontMetrics(lbChoose.getFont()).getHeight() * lbChoose.getRows()
            )
        );
        JPanel infoPanel = panelOf(
            new Component[]{
                Box.createVerticalGlue(),
                lbChoose,
                lbValue,
                btnSend,
                Box.createVerticalGlue()
            }, BoxLayout.PAGE_AXIS
        );

        for (Component component : infoPanel.getComponents()) {
            if(component instanceof JComponent){
                ((JComponent) component).setAlignmentX(CENTER_ALIGNMENT);
            }
        }

        return infoPanel;
    }

    @Override
    public void skillChosen(String skill, int value, List<String> disabled) {
        if(skill.isEmpty()) {
            lastChoice = value > 5;
            lbChoose.setText(lastChoice ? txtFinal : txtChoose);
            lbValue.setText(Integer.toString(value));
            if(!lastChoice) {
                attributePanels.forEach(ParanoiaAttributePanel::unlock);
                attributePanels.stream().filter(p -> disabled.contains(p.getName())).forEach(ParanoiaAttributePanel::lock);
            }
        }
    }

    @Override
    public void selectAttribute(ParanoiaAttributePanel panel) {
        attributePanels.forEach(ParanoiaAttributePanel::reset);
        panel.select();
        btnSend.setEnabled(true);
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaAttribute> updatedModel) {
        remove(statPanel);
        attributePanels.clear();
        statPanel = createStatsTable(updatedModel);
        add(statPanel, BorderLayout.CENTER);
        revalidate();
    }
}
