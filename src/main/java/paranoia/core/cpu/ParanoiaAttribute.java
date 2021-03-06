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

    private final String name;
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
    public String getName() { return name; }

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

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        if(other == null) return false;
        if(!other.getClass().equals(ParanoiaAttribute.class)) return false;
        ParanoiaAttribute o = (ParanoiaAttribute) other;
        return o.name.equals(name);
    }

    private static class ParanoiaAttributeRenderer extends JLabel implements TableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column
        ) {
            setText(value.toString());
            setFont(AssetManager.getBoldFont(20));
            setOpaque(true);

            table.getColumnModel().getColumn(column).setPreferredWidth(
                getFontMetrics(getFont()).stringWidth("000")
            );
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
            setFont(AssetManager.getItalicFont(20));
            table.getColumnModel().getColumn(column).setPreferredWidth(
                getFontMetrics(getFont()).stringWidth("Alpha Complex")
            );
            return this;
        }
    }
}
