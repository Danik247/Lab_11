package persons;

import enums.Effect;
import helpfulClasses.Consol;
import interfaces.Move;
import rec.Place;
import helpfulClasses.Crowd;
import rec.Statistics;

import java.util.ArrayList;

public abstract class Person implements Move {

    protected float stamina;//выносливость
    protected float speed;//скорость
    protected float dexterity;//ловкость
    protected float eloquence;//красноречие
    protected String name;//имя
    protected float weight;//вес
    protected float size;//размер
    protected Effect effect;//статус
    ArrayList<Place> lastplaces = new ArrayList<>();
    Place start = new Place("Старт", 0, false, false, false);

    public Person(String name, float dexterity, float speed, float stamina, float eloquence, float size, float weight) {
        this.dexterity = dexterity;
        this.speed = speed;
        this.stamina = stamina;
        this.eloquence = eloquence;
        this.name = name;
        this.weight = weight;
        this.size = size;
        Consol.hello(name, dexterity, speed, stamina, eloquence, size, weight);
        lastplaces.add(start);
    }

    abstract void boringStart();

    public Statistics getStats() {
        if (effect == Effect.IS_ABOVE | effect == Effect.IS_HIDED) {
            return new Statistics(speed, stamina, (float) (dexterity * 0.7), (float) (eloquence * 1.5), weight, size);
        }
        if (effect == Effect.IS_DROPPED) {
            return new Statistics((float) (speed * 0.5), stamina, (float) (dexterity * 0.5), eloquence, weight, size);
        }
        if (effect == Effect.IS_COUGHTED | effect == Effect.IS_CONFUSED) {
            return new Statistics((float) (speed * 0.1), stamina, dexterity, eloquence, weight, size);
        }
        return new Statistics(speed, stamina, dexterity, eloquence, weight, size);
    }

    public void rushCrowd(Crowd crowd){
        boolean success = checkit(60 - (crowd.count - (this.stamina + this.dexterity) * 3));
        if (getStats().stamina() >= (int) crowd.count/3){
            if (success){
                Consol.describe("ты прополз через толпу из "+ crowd.count+ " " + crowd.content.name + "-ов");
                changeSt((int) crowd.count/3);
            }
            else {
                Consol.describe("тебя вытолкнули обратно, попробуй снова");
                rushCrowd(crowd);
            }
        }
        else {
            Consol.describe("ты все-таки решил оценить свои силы и");
            rest((int) crowd.count/3);
            rushCrowd(crowd);
        }
    }

    public void escape(Place place) {
        boolean success = checkit(60 - (place.hard() * 12 - (this.stamina + this.dexterity) * 3));
        if (getStats().stamina() >= place.hard()) {
            if (place.isDown()) {
                escapeFromHole(place, success);
            } else if (place.isUp()) {
                returnDown(place, success);
            } else {
                standartExit(place);
            }
        } else {
            notEnothStaminaToExit(place);
        }
    }

    private void notEnothStaminaToExit(Place place) {
        Consol.describe("Что-то ты устал, попробуй отдохнуть");
        rest(place.hard() * 3);
        escape(place);
    }

    private void standartExit(Place place) {
        Consol.describe("ты выбрался оттуда...");
        changeSt((float) place.hard() * -1);
        effect = null;
    }

    private void returnDown(Place place, boolean success) {
        if (success) {
            Consol.describe("Ты все-таки спустился, но потратил " + place.hard() / 2 + " выноосливости");
            changeSt((float) place.hard() / -2);
            effect = null;
        } else {
            Consol.describe("Хаха... Ты грохнулся вниз и потратил " + place.hard() + " выносливости");
            changeSt((float) place.hard() * -1);
        }
    }

    private void escapeFromHole(Place place, boolean success) {
        if (success) {
            Consol.describe("Ты все-таки вылез из " + place.name() + ", но потратил " + place.hard() + " выносливости");
            changeSt(place.hard() * -1);
            effect = null;
        } else {
            Consol.describe("Эээ... Ты уныло соскользнул в самый низ и потратил " + place.hard() / 2 + " выносливости");
            changeSt((float) place.hard() / -2);
            escape(place);
        }
    }

    @Override
    public void changeLoc(Place place) {
        boolean success = checkit(60 - (place.hard() * 12 - (this.stamina + this.dexterity) * 4));
        if (place.hard() < stamina) {
            if (place.isUp()) {
                getUp(place, success);
            } else if (place.isDown()) {
                getDown(place, success);
            } else if (place.isHide()) {
                getHide(place, success);
            } else {
                getNormal(place);
            }
            lastplaces.add(place);
        } else {
            Consol.describe("Что-то ты устал, попробуй отдохнуть");
            rest(place.hard() * 3);
            changeLoc(place);
        }
    }

    private void getNormal(Place place) {
        Consol.describe(name + " пришел к такому месту как " + place.name() + " почти не потратив выносливости");
        changeSt((float) place.hard() / -10);
    }

    private void getHide(Place place, boolean success) {
        if (success) {
            Consol.describe("Ты решил спрятаться \nПоздравляю тебя не видно, теперь ты " + place.name());
            effect = Effect.IS_HIDED;
        } else {
            Consol.describe("Ты запутался, молодец");
            effect = Effect.IS_COUGHTED;
        }
    }

    private void getDown(Place place, boolean success) {
        if (success) {
            Consol.describe("ты аккуратно спустился в " + place.name() + ", хоть и потратил " + place.hard() + " выносливости");
            changeSt((float) place.hard() / -1);
        } else {
            Consol.describe("Ты полетел в самый низ, зато потратил всего " + place.hard() + " выносливости");
            changeSt((float) place.hard() / -2);
        }
        lastplaces.add(place);
        effect = Effect.IS_DROPPED;
    }

    private void getUp(Place place, boolean success) {
        if (success) {
            Consol.describe("Ты залез на " + place.name() + ", но потратил " + place.hard() + " выноосливости");
            changeSt((float) place.hard() / -2);
            lastplaces.add(place);
            effect = Effect.IS_ABOVE;
        } else {
            Consol.describe("Эээ... Ты уныло соскользнул в самый низ, еще и потратил " + place.hard() + " выносливости");
            changeSt((float) place.hard() / -1);
            changeLoc(place);
        }
    }

    public boolean dodge(Person attacker){
        boolean success = checkit(50 + (this.stamina + this.dexterity) * 2);
        Consol.describe(success ? "увернулся" : attacker.name + " попал в тебя");
        return success;
    }

    public void rest(float timeHour) {
        changeSt(timeHour * 2);
        Consol.describe("Ты отдыхал " + timeHour + " часов, и восстановил " + timeHour * 2 + " выносливости");
    }

    private void changeSt(float increment) {
        this.stamina += increment;
        if (this.stamina < 0) {
            this.stamina = 0;
        }
        Consol.describe("Теперь у тебя " + stamina + " выносливости");
    }

    @Override
    public String toString() {
        return "person{" +
                "name='" + name + '\'' +
                ", dexterity=" + dexterity +
                ", eloquence=" + eloquence +
                ", ...}";
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != this.getClass()) {
            return false;
        }
        Person guest = (Person) object;
        return (name != null && name.equals(guest.name))
                && (dexterity == guest.dexterity) && (stamina == guest.stamina)
                && (speed == guest.speed) && (size == guest.size)
                && (eloquence == guest.eloquence) && (weight == guest.weight);
    }

    @Override
    public int hashCode() {
        int total = 52;
        total = total * 52 + (name == null ? 0 : name.hashCode());
        total = total * 52 + (int) speed;
        total = total * 52 + (int) stamina;
        total = total * 52 + (int) size;
        total = total * 52 + (int) dexterity;
        total = total * 52 + (int) eloquence;
        total = total * 52 + (int) weight;
        return total;
    }

    private boolean checkit(float chance) {
        return (chance / 100 > Math.random());
    }
}
