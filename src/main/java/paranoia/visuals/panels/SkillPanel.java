package paranoia.visuals.panels;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;
import java.util.Map;

public class SkillPanel extends JPanel {

    private static final Font stringFont = new Font("Segoe", Font.ITALIC, 20);

    public SkillPanel(
        Map<String, ParanoiaAttribute> attributeMap
    ) {
        //Set UI
        JLabel lbSkills = new JLabel("Skills");
        lbSkills.setFont(stringFont);
        JLabel lbStats = new JLabel("Stats");
        lbStats.setFont(stringFont);

        JTable statTable = new JTable(new StatModel(attributeMap));
        JTable skillTable = new JTable(new SkillModel(attributeMap));

        statTable.setRowHeight(35);
        skillTable.setRowHeight(35);

        statTable.setDefaultRenderer(Integer.class, new ParanoiaAttributeRenderer());
        statTable.setDefaultRenderer(String.class, new ParanoiaNameRenderer());

        skillTable.setDefaultRenderer(Integer.class, new ParanoiaAttributeRenderer());
        skillTable.setDefaultRenderer(String.class, new ParanoiaNameRenderer());

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        add(lbStats);
        add(statTable);
        add(lbSkills);
        add(skillTable);
    }

    private static class StatModel extends AbstractTableModel {

        private Map<String, ParanoiaAttribute> attributeMap;

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

        private Map<String, ParanoiaAttribute> attributeMap;

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
            if( columnIndex % 2 == 0) return String.class;
            else return Integer.class;
        }
    }

    private static class ParanoiaNameRenderer extends JLabel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setFont(stringFont);
            return this;
        }
    }

    private static class ParanoiaAttributeRenderer extends JLabel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
        ) {
            setText(value.toString());
            setFont(new Font("Segoe", Font.BOLD, 20));
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            if(Integer.parseInt(value.toString()) >= 0){
                setBackground(new Color(217, 119, 119));
                setForeground(new Color(115, 29, 29));
            } else {
                setBackground(new Color(105, 191, 105));
                setForeground(new Color(29, 115, 29));
            }
            return this;
        }


    }
}
