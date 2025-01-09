package dk.easv.pmc.be;

public class Category {
    private int id;
    private String name;

    // Constructor med ID
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    // Constructor uden ID
    public Category(String name) {
        this.name = name;
    }

    // Getters og setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
