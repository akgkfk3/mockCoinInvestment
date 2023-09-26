package sesac.mockInvestment.domain;

public enum GenderType {
    m("Male"), f("Female");


    private final String description;

    GenderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}