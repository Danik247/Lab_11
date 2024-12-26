package persons;

import enums.Effect;
import helpfulClasses.Consol;
import interfaces.Moveable;
import rec.Place;
import helpfulClasses.Crowd;
import rec.Statistics;

import java.util.ArrayList;

public abstract class Person implements Moveable {
    protected Statistics allStats;
    protected String name;//имя
    protected float stamina;
    protected Effect effect;//статус
    ArrayList<Place> lastplaces = new ArrayList<>();
    Place start = new Place("Старт", 0, false, false, false);

    public Person(String name, float dexterity, float speed, float stamina, float eloquence, float size, float weight) {
        this.allStats = new Statistics(speed, dexterity, eloquence, weight, size);
        this.name = name;
        this.stamina = stamina;
        Consol.hello(name, this);
        lastplaces.add(start);
    }

    abstract void boringStart();

    public Statistics getStatistics() {
        return allStats;
    }
    @Override
    public String toString() {
        return "person{" +
                "name='" + name + '\'' +
                ", allStats="+allStats.toString()+"}";
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
                && (getStatistics().equals(((Person) object).getStatistics()));
    }

    @Override
    public int hashCode() {
        int total = 52;
        total = total * 52 + (name == null ? 0 : name.hashCode());
        total = total * 52 + getStatistics().hashCode();
        return total;
    }

    public void rushCrowd(Crowd crowd) {
        boolean success = checkit(60 - (crowd.count - (stamina + allStats.dexterity()) * 3));
        if (stamina >= (float) crowd.count / 3) {
            if (success) {
                Consol.describe("ты прополз через толпу из " + crowd.count + " " + crowd.content.name + "-ов");
                changeSt((float) crowd.count / 3);
            } else {
                Consol.describe("тебя вытолкнули обратно, попробуй снова");
                rushCrowd(crowd);
            }
        } else {
            Consol.describe("ты все-таки решил оценить свои силы и");
            rest((float) crowd.count / 3);
            rushCrowd(crowd);
        }
    }

    @Override
    public void changeLocation(Place place) {
        boolean success = checkit(60 - (place.hard() * 12 - (stamina + allStats.dexterity()) * 4));
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
            changeLocation(place);
        }
    }

    @Override
    public void escapeLocation(Place place) {
        boolean success = checkit(60 - (place.hard() * 12 - (stamina + allStats.dexterity()) * 3));
        if (stamina >= place.hard()) {
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

    public boolean dodge(Person attacker) {
        boolean success = checkit(50 + (stamina + allStats.dexterity()) * 2);
        Consol.describe(success ? "увернулся" : attacker.name + " попал в тебя");
        return success;
    }

    public void rest(float timeHour) {
        changeSt(timeHour * 2);
        Consol.describe("Ты отдыхал " + timeHour + " часов, и восстановил " + timeHour * 2 + " выносливости");
    }

    private void notEnothStaminaToExit(Place place) {
        Consol.describe("Что-то ты устал, попробуй отдохнуть");
        rest(place.hard() * 3);
        escapeLocation(place);
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
            escapeLocation(place);
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
            changeLocation(place);
        }
    }

    private void changeSt(float increment) {
        stamina += increment;
        if (stamina < 0) {
            stamina = 0;
        }
        Consol.describe("Теперь у тебя " + stamina + " выносливости");
    }

    private boolean checkit(float chance) {
        return (chance / 100 > Math.random());
    }
}
