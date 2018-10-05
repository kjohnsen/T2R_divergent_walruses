package modelclasses;

public class GameName {
    private String name;

    public GameName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameName gameName = (GameName) o;
        return this.name.equals(gameName.name);
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
}
