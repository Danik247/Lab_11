package persons;

import helpfulClasses.Consol;

public class Man extends Person {

    @Override
    void boringStart() {
        Consol.describe("    " + name + ": только ловкость?!\n    XXXX: А ну тихо, мы начинаем");
    }

    public Man(String name, float dexterity, float speed, float stamina, float eloquence) {
        super(name, dexterity, speed, stamina, eloquence, 3, 5);
    }

    public Man(String name, float dexterity) {
        float stamina = dexterity * 4;
        float eloquence = dexterity * 1;
        this(name, dexterity, dexterity, stamina, eloquence);
        boringStart();
    }
}
