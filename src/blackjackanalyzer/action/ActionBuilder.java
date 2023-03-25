package blackjackanalyzer.action;

public class ActionBuilder {
    private String person;
    private String action;

    public ActionBuilder setPerson(String person) {
        this.person = person;
        return this;
    }

    public ActionBuilder setAction(String action) {
        this.action = action;
        return this;
    }

    public Action createAction() {
        return new Action(person, action);
    }
}