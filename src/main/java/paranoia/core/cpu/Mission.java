package paranoia.core.cpu;

import paranoia.core.ICoreTechPart;
import paranoia.services.hpdmc.ResourceManager;
import paranoia.visuals.custom.ParanoiaImage;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import static paranoia.services.hpdmc.ResourceManager.ResourceIcon.MISSION;
import static paranoia.services.hpdmc.ResourceManager.ResourceIcon.MISSION_COMPLETED;
import static paranoia.services.hpdmc.ResourceManager.ResourceIcon.MISSION_FAILED;

public class Mission implements ICoreTechPart {

    public enum MissionPriority {
        REQUIRED,
        OPTIONAL
    }

    private Boolean failed;
    private Boolean completed;
    private MissionPriority priority;

    private String title;
    private String description;

    public Mission(
        String title,
        String description
    ) {
        this(title, description, MissionPriority.REQUIRED);
    }

    public Mission(
        String title,
        String description,
        MissionPriority priority
    ) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        completed = false;
        failed = false;
    }

    public Boolean isCompleted() {
        return completed && !failed;
    }

    public void complete() {
        completed = true;
    }

    public void fail() {
        failed = true;
    }

    public ResourceManager.ResourceIcon getMissionStatus() {
        if (failed) {
            return MISSION_FAILED;
        } else if (completed) {
            return MISSION_COMPLETED;
        } else return MISSION;
    }

    public MissionPriority getPriority() {
        return priority;
    }

    @Override
    public JPanel getVisual() {
        JTextArea missionText = new JTextArea(title);
        missionText.setToolTipText(description);
        missionText.setEditable(false);
        missionText.setForeground(
            failed ? new Color(185,0,0) : new Color(0,0,0)
        );
        missionText.setOpaque(false);
        if(failed) {
            Map<TextAttribute, Boolean> fontAttributes = new HashMap<>();
            fontAttributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            missionText.setFont(new Font(fontAttributes));
        }

        ParanoiaImage image = new ParanoiaImage(ResourceManager.getResource(getMissionStatus()));
        image.setPreferredSize(new Dimension(32,32));

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEADING));
        panel.setOpaque(false);
        panel.add(image);
        panel.add(missionText);
        panel.setMinimumSize(new Dimension(0,40));
        return panel;
    }
}
