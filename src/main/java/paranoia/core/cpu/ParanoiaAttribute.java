package paranoia.core.cpu;

import paranoia.core.ICoreTechPart;
import paranoia.services.plc.AssetManager;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;

public class ParanoiaAttribute implements ICoreTechPart {

    private String name;
    private int value;

    public ParanoiaAttribute(String name) {
        this(name, 0);
    }

    public ParanoiaAttribute(String name, Integer value){
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
    public String getName() {
        return name;
    }

    public static TableCellRenderer createValueRenderer() {
        return new ParanoiaAttributeRenderer();
    }

    public static TableCellRenderer createNameRenderer() {
        return new ParanoiaNameRenderer();
    }

    public static ParanoiaAttribute getSkill(Skill name, int value) {
        return new ParanoiaAttribute(name.toString(), value);
    }

    public static ParanoiaAttribute getStat(Stat name, int value) {
        return new ParanoiaAttribute(name.toString(), value);
    }

    @Override
    public JPanel getVisual() {
        return null;
    }

    private static class ParanoiaAttributeRenderer extends JLabel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
        ) {
            setText(value.toString());
            setFont(AssetManager.getFont(20, true, false));
            setOpaque(true);

            table.getColumnModel().getColumn(column).setPreferredWidth(20);
            setHorizontalAlignment(CENTER);
            if(Integer.parseInt(value.toString()) >= 0){
                setBackground(new Color(105, 191, 105));
                setForeground(new Color(29, 115, 29));
            } else {
                setBackground(new Color(217, 119, 119));
                setForeground(new Color(115, 29, 29));
            }
            return this;
        }
    }

    private static class ParanoiaNameRenderer extends JLabel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText(value.toString());
            setFont(AssetManager.getFont(20, false, true));
            table.getColumnModel().getColumn(column).setPreferredWidth(120);
            return this;
        }
    }
}
