package paranoia.visuals.panels;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.util.Map;

public class SkillPanel extends JPanel {

    public SkillPanel(
        Map<String, ParanoiaAttribute> attributeMap
    ) {

        JLabel lbStats = new JLabel("Stats");
        JPanel statPanel = new JPanel(new GridLayout(1,4));
        for (Stat stat : Stat.values()) {
            statPanel.add(new JLabel(stat.toString()));
            statPanel.add(new JLabel(attributeMap.get(stat.toString()).toString()));
        }

        JLabel lbSkills = new JLabel("Skills");
        JPanel skillPanel = new JPanel(new GridLayout(4,4));
        for (Skill skill : Skill.values()) {
            skillPanel.add(new JLabel(skill.toString()));
            skillPanel.add(new JLabel(attributeMap.get(skill.toString()).toString()));
        }

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        add(lbStats);
        add(statPanel);
        add(lbSkills);
        add(skillPanel);

    }

}
