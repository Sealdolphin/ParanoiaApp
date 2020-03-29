package paranoia.visuals.rnd;

import paranoia.visuals.custom.ParanoiaImage;

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
}
