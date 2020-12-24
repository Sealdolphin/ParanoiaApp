package paranoia.visuals.ui;

import daiv.ui.visuals.ParanoiaImage;
import paranoia.services.plc.ResourceManager;
import paranoia.visuals.ComponentName;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

import static paranoia.services.plc.ResourceManager.DEFAULT_FONT;
import static paranoia.services.plc.ResourceManager.FAILED_MISSION;
import static paranoia.services.plc.ResourceManager.ResourceIcon.MISSION;
import static paranoia.services.plc.ResourceManager.ResourceIcon.MISSION_COMPLETED;
import static paranoia.services.plc.ResourceManager.ResourceIcon.MISSION_FAILED;

public class MissionView extends JPanel {

    public MissionView(String title, String description, int id, boolean failed, boolean completed) {
        JTextArea missionText = new JTextArea(title);
        missionText.setName(ComponentName.MISSION.name() + id);
        missionText.setToolTipText(description);
        missionText.setEditable(false);
        missionText.setForeground(failed ? FAILED_MISSION : DEFAULT_FONT);
        missionText.setOpaque(false);
        if(failed) {
            Map<TextAttribute, Boolean> fontAttributes = new HashMap<>();
            fontAttributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
            missionText.setFont(new Font(fontAttributes));
        }

        ParanoiaImage image = new ParanoiaImage(ResourceManager.getResource(getMissionStatus(failed, completed)));
        image.setPreferredSize(new Dimension(32,32));

        setLayout(new FlowLayout(FlowLayout.LEADING));
        setOpaque(false);
        add(image);
        add(missionText);
        setMinimumSize(new Dimension(0,40));
    }

    private ResourceManager.ResourceIcon getMissionStatus(boolean failed, boolean completed) {
        if (failed) {
            return MISSION_FAILED;
        } else if (completed) {
            return MISSION_COMPLETED;
        } else return MISSION;
    }

}
