package models;

public class CategoryEvent extends models.Category {
    private static int incrementId = 0;
    private int id;
    private String message;
    private Address address;
    private Localization localization;

    public static int getIncrementId() {
        return incrementId;
    }

    public static void setIncrementId(int incrementId) {
        CategoryEvent.incrementId = incrementId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Localization getLocalization() {
        return localization;
    }

    public void setLocalization(Localization localization) {
        this.localization = localization;
    }

    public CategoryEvent() {
        super();
        this.id = ++incrementId;
    }
}
