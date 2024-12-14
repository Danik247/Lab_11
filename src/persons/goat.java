package persons;

import interfaces.consl;

public class goat extends person {

    public goat(float dexterity, float speed, float stamina, float eloquence) {
        super("Козел", dexterity, speed, stamina, eloquence, 2, 3);
    }
    public goat(float dexterity){
        float speed = (float) (dexterity*1.5);
        float stamina = dexterity *4;
        float eloquence = dexterity *3;
        this(dexterity, speed, stamina, eloquence);
        boringSt();
    }

    @Override
    void boringSt() {
        consl.describe("    "+name + ": А почему мне дали только ловкость?\n    XXXX: А ну тихо, мы начали");
    }
}
