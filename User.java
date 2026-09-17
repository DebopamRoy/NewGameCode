import java.util.Scanner;

class User extends Player{
    Scanner sc = new Scanner(System.in);
    private String name = "";

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void chooseCell() {
        System.out.print("Row 1 to 3: ");
        chosenr = sc.nextInt() - 1;
        System.out.print("Column 1 to 3: ");
        chosenc = sc.nextInt() - 1;
    }

}