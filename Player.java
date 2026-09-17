abstract class Player {
    static int chosenr;
    static int chosenc;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void chooseCell();
}