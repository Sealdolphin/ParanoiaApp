package paranoia.visuals.panels;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;
import paranoia.services.hpdmc.ParanoiaListener;
import paranoia.services.hpdmc.manager.ParanoiaManager;
import paranoia.services.plc.AssetManager;
import paranoia.visuals.ComponentName;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.awt.Font;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SkillPanel extends JPanel implements ParanoiaListener<ParanoiaAttribute> {

    private final JLabel lbSkills = new JLabel("Skills");
    private final JLabel lbStats = new JLabel("Stats");

    public SkillPanel(ParanoiaManager<ParanoiaAttribute> cpu) {
        Font stringFont = AssetManager.getFont(20, false, true);
        //Set UI
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        lbSkills.setFont(stringFont);
        lbStats.setFont(stringFont);
        cpu.addListener(this);
        setName(ComponentName.SKILL_PANEL.name());
    }

    private Map<String, ParanoiaAttribute> createAttributeMap(Collection<ParanoiaAttribute> model) {
        HashMap<String, ParanoiaAttribute> map = new HashMap<>();
        model.forEach( attr -> map.put(attr.getName(), attr));
        return map;
    }

    @Override
    public void updateVisualDataChange(Collection<ParanoiaAttribute> updatedModel) {
        removeAll();
        Map<String, ParanoiaAttribute> attributeMap = createAttributeMap(updatedModel);

        JTable statTable = new JTable(new StatModel(attributeMap));
        statTable.setRowHeight(35);
        statTable.setDefaultRenderer(Integer.class, ParanoiaAttribute.createValueRenderer());
        statTable.setDefaultRenderer(String.class, ParanoiaAttribute.createNameRenderer());
        statTable.setName(lbStats.getText());

        JTable skillTable = new JTable(new SkillModel(attributeMap));
        skillTable.setRowHeight(35);
        skillTable.setDefaultRenderer(Integer.class, ParanoiaAttribute.createValueRenderer());
        skillTable.setDefaultRenderer(String.class, ParanoiaAttribute.createNameRenderer());
        skillTable.setName(lbSkills.getText());

        add(Box.createGlue());
        add(lbStats);
        add(Box.createGlue());
        add(statTable);
        add(Box.createGlue());
        add(lbSkills);
        add(Box.createGlue());
        add(skillTable);
    }

    private static class StatModel extends AbstractTableModel {

        private final Map<String, ParanoiaAttribute> attributeMap;

        StatModel(Map<String, ParanoiaAttribute> attributeMap) {
            this.attributeMap = attributeMap;
        }

        @Override
        public int getRowCount() {
            return 1;
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String key = Stat.values()[Math.floorDiv(columnIndex, 2)].toString();
            int value = attributeMap.get(key).getValue();

            if(columnIndex % 2 == 0) return key;
            else return value;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(columnIndex % 2 == 0) return String.class;
            else return Integer.class;
        }
    }

    private static class SkillModel extends AbstractTableModel {

        private final Map<String, ParanoiaAttribute> attributeMap;

        SkillModel(Map<String, ParanoiaAttribute> attributeMap) {
            this.attributeMap = attributeMap;
        }

        @Override
        public int getRowCount() {
            return 4;
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            int ordinal = Math.floorDiv(columnIndex , 2);
            Stat stat = Stat.values()[ordinal];

            String key = Arrays.stream(Skill.values())
                .filter(skill -> skill.getParent().equals(stat))
                .toArray()[rowIndex].toString();

            Integer value = attributeMap.get(key).getValue();

            if(columnIndex % 2 == 0) return key;
            else return value;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(columnIndex % 2 == 0) return String.class;
            else return Integer.class;
        }
    }
}
