package paranoia.core.cpu;

import daiv.ui.AssetManager;
import paranoia.core.ICoreTechPart;
import paranoia.visuals.custom.ParanoiaAttributePanel;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ParanoiaAttribute implements ICoreTechPart {

    private final String name;
    private Integer value;

    public ParanoiaAttribute(String name) {
        this(name, 0);
    }

    public ParanoiaAttribute(String name, Integer value){
        this.value = value;
        this.name = name;
    }

    public static Collection<ParanoiaAttribute> getDefaultModel() {
        List<ParanoiaAttribute> defaultAttributes = new ArrayList<>();
        for(Stat stat: Stat.values())
            defaultAttributes.add(stat.createAttribute(0));
        for(Skill skill: Skill.values())
            defaultAttributes.add(skill.createAttribute(0));
        return defaultAttributes;
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

    @Override
    public JPanel getVisual() {
        return new ParanoiaAttributePanel(name, value);
    }

    @Override
    public boolean equals(Object other) {
        if(other == this) return true;
        if(other == null) return false;
        if(!other.getClass().equals(ParanoiaAttribute.class)) return false;
        ParanoiaAttribute o = (ParanoiaAttribute) other;
        return o.name.equals(name);
    }

    public boolean isStat() {
        return Arrays.stream(Stat.values()).anyMatch(s -> s.toString().equals(name));
    }

    public boolean isSkill() {
        return Arrays.stream(Skill.values()).anyMatch(s -> s.toString().equals(name));
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
