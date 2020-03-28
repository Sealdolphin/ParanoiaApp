package paranoia.visuals;

import paranoia.core.Clone;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class CerebrealCoretech extends JFrame {

    private List<Clone> troubleShooters;
    private Clone self;
    private GroupLayout layout;

    private JScrollPane troubleShooterPanel;
    private JPanel selfPanel;

    public CerebrealCoretech(Clone self) {
        this(self, new ArrayList<>());
    }

    public CerebrealCoretech(
        Clone self,
        List<Clone> troubleShooters
    ) {
        this.self = self;
        this.troubleShooters = troubleShooters;

        layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        //Setup metadata
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Paranoia");
        //Setup visuals
        getContentPane().setBackground(new Color(59, 59, 59));

        //Assets
        refreshLayout();
        //Assemble
        pack();
        setLocationRelativeTo(null);
    }

    public void setSelf(Clone clone) {
        self = clone;
        JPanel newSelfPanel = clone.getSelfVisual();
        layout.replace(selfPanel, newSelfPanel);
        selfPanel = newSelfPanel;
    }

    public void addClone(Clone clone) {
        troubleShooters.add(clone);
        JScrollPane newScrollPane = createTroubleShooterPanel();
        layout.replace(troubleShooterPanel, newScrollPane);
        troubleShooterPanel = newScrollPane;
    }

    private JScrollPane createTroubleShooterPanel() {
        JPanel tsPanel = new JPanel();
        FlowLayout panelLayout = new FlowLayout();
        panelLayout.setHgap(25);
        panelLayout.setVgap(15);
        tsPanel.setLayout(panelLayout);
        troubleShooters.forEach( clone -> tsPanel.add(clone.getVisual()));
        return new JScrollPane(
            tsPanel,
            ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER,
            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED
        );
    }

    private void refreshLayout() {
        troubleShooterPanel = createTroubleShooterPanel();
        selfPanel = self.getSelfVisual();

        JButton btnExit = new JButton("EXIT");
        //set horizontal
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(
                    layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(
                            troubleShooterPanel,
                            GroupLayout.DEFAULT_SIZE,
                            GroupLayout.DEFAULT_SIZE,
                            Short.MAX_VALUE
                        )
                        .addGroup(
                            layout.createSequentialGroup()
                                .addContainerGap(500, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER,
                                    false
                                    )
                                    .addComponent(
                                        selfPanel,
                                        GroupLayout.PREFERRED_SIZE,
                                        GroupLayout.PREFERRED_SIZE,
                                        Short.MAX_VALUE
                                    )
                                )
                        )
                )
        );

        //set vertical
        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addComponent(
                    troubleShooterPanel,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE,
                    Short.MAX_VALUE
                )
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 300, Short.MAX_VALUE)
                .addComponent(
                    selfPanel,
                    GroupLayout.PREFERRED_SIZE,
                    GroupLayout.PREFERRED_SIZE,
                    Short.MAX_VALUE
                )
        );

    }

}
