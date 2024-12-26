package persons;

import helpfulClasses.Consol;

public class Goat extends Person {

    @Override
    void boringStart() {
        Consol.describe("    "+name + ": А почему мне дали только ловкость?\n    XXXX: Тихо, мы начали");
    }

    public Goat(float dexterity, float speed, float stamina, float eloquence) {
        super("Козел", dexterity, speed, stamina, eloquence, 2, 3);
    }

    public Goat(float dexterity){
        float speed = (float) (dexterity*1.5);
        float stamina = dexterity *4;
        float eloquence = dexterity *3;
        this(dexterity, speed, stamina, eloquence);
        boringStart();
    }


}
