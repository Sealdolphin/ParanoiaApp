package paranoia.visuals.panels;

import paranoia.core.cpu.ParanoiaAttribute;
import paranoia.core.cpu.Skill;
import paranoia.core.cpu.Stat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Arrays;
import java.util.Map;

public class SkillPanel extends JPanel {

    public SkillPanel(
        Map<String, ParanoiaAttribute> attributeMap
    ) {
        //Set UI
        JTable attributeTable = new JTable(new SkillModel(attributeMap));
        attributeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        attributeTable.setDefaultRenderer(Integer.class, new SkillValueRenderer());

        //TODO: refarctor to BoxLayout
        setLayout(new BorderLayout());

        add(attributeTable, BorderLayout.CENTER);
    }

    //TODO: need SkillModel and StatModel separately! -> 2 tables + 2 labels
    private static class SkillModel extends AbstractTableModel {

        private Map<String, ParanoiaAttribute> attributeMap;

        SkillModel(Map<String, ParanoiaAttribute> attributeMap) {
            this.attributeMap = attributeMap;
        }

        @Override
        public int getRowCount() {
            return 5;
        }

        @Override
        public int getColumnCount() {
            return 8;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            String key;
            int ordinal = Math.floorDiv(columnIndex , 2);
            Stat stat = Stat.values()[ordinal];

            if(rowIndex == 0)
                key = stat.toString();
            else
                key = Arrays.stream(Skill.values())
                    .filter(skill -> skill.getParent().equals(stat))
                    .toArray()[rowIndex - 1].toString();

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

    private static class SkillValueRenderer extends JLabel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
        ) {
            //TODO: show colored values (negative = red, positive = green)
            setText(value.toString());
            setHorizontalAlignment(CENTER);
            return this;
        }


    }
}
