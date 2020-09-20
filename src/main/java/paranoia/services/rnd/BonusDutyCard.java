package paranoia.services.rnd;

import daiv.ui.visuals.ParanoiaImage;

import javax.swing.JPanel;

public class BonusDutyCard extends ParanoiaCard {

    private ParanoiaImage icon;

    public BonusDutyCard(Integer id){
        super(CardType.BONUS_DUTY, id, -1);
    }

    @Override
    public String toString() {
        return null;
    }

    @Override
    public JPanel getVisual() {
        return this;
    }
}
