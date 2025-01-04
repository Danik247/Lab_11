package persons;

import helpfulClasses.Console;

public class Goat extends Person {

    public Goat(float dexterity, float speed, float stamina, float eloquence) {
        super("Козел", dexterity, speed, stamina, eloquence, 2, 3);
    }

    public Goat(float dexterity){
        this(dexterity, (float) (dexterity*1.5), dexterity *4, dexterity *3);
        boringStart();
    }

    @Override
    void boringStart() {
        Console.describe("    "+name + ": А почему мне дали только ловкость?\n    XXXX: Тихо, мы начали");
    }
}
