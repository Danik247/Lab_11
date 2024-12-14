package persons;

import interfaces.consl;

public class man extends person {

    public man(String name, float dexterity, float speed, float stamina, float eloquence) {
        super(name, dexterity, speed, stamina, eloquence, 3, 5);
    }

    public man(String name, float dexterity) {
        float stamina = dexterity * 4;
        float eloquence = dexterity * 1;
        this(name, dexterity, dexterity, stamina, eloquence);
        boringSt();
    }

    @Override
    void boringSt() {
        consl.describe("    " + name + ": только ловкость?!\n    XXXX: А ну тихо, мы начинаем");
    }
}
