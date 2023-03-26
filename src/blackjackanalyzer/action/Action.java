package blackjackanalyzer.action;

public class Action {
    private String person;
    private String action;

    public Action(String person, String action) {

        this.person = person;
        this.action = action;
    }

    /**
     *
     * @return String
     */
    public String getPerson() {
        return person;
    }

    /**
     *
     * @return String
     */
    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return getPerson() + " " + getAction();
    }
}
