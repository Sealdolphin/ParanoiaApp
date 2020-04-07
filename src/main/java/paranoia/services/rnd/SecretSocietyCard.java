package paranoia.services.rnd;

import paranoia.visuals.custom.ParanoiaImage;

import javax.swing.JPanel;

public class SecretSocietyCard extends ParanoiaCard {

    private ParanoiaImage icon;
    private String ideology;

    public SecretSocietyCard(Integer id){
        super(CardType.SECRET_SOCIETY, id, -1);
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
