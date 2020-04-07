package paranoia.services.rnd;

import paranoia.visuals.custom.ParanoiaImage;

public class BonusDutyCard extends ParanoiaCard {

    private ParanoiaImage icon;

    public BonusDutyCard(Integer id){
        super(CardType.BONUS_DUTY, id, -1);
    }

    @Override
    public String toString() {
        return null;
    }
}
