import javafx.scene.image.Image;
import java.util.concurrent.TimeUnit;

public class Gourd extends Organism implements Runnable {

    public enum enum_Gourd {
        RED("大娃", "RED", 0), ORANGE("二娃", "ORANGE", 1), YELLOW("三娃", "YELLOW", 2),
        GREEN("四娃", "GREEN", 3), CYAN("五娃", "CYAN", 4), BLUE("六娃", "BLUE", 5), PURPLE("七娃", "PURPLE", 6);
        private String name, color;
        private int rank;

        enum_Gourd(String name, String color, int rank) {
            this.rank = rank;
            this.name = name;
            this.color = color;
        }

    }

    //
    //member variables
    //
    public enum_Gourd gourd;

    public String tellColor() {
        return this.gourd.color;
    }

    public int tellRank() {
        return this.gourd.rank;
    }

    public void report(int src, int dst) {
        System.out.println(this.gourd.name + ": " + src + "->" + dst);
    }

    Gourd(int rank) {
        gourd = enum_Gourd.values()[rank];
        name = gourd.name;
        group = enumGroup.HERO;
        image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\" + (rank + 1) + ".jpg");
        skill = new Skill(this);
    }


    public static Gourd[] values() {
        Gourd[] gourdBrothers = new Gourd[7];
        for (int i = 0; i < 7; i++) {
            gourdBrothers[i] = new Gourd(i);
        }
        return gourdBrothers;
    }

    @Override
    public void run() {
        try {
            while (GameController.Gaming) {
                if (!isDead()) {
                    if (BattleField.hasEnemy(enumGroup.HERO, position.getX(), position.getY(), skill.skillRange)) {
                        //System.out.println("incoming!");
                        LogController.writeLog(this.tellName() + " use skill: " + skill.skillName);
                        skill.useSkill(position.getX(), position.getY());
                        if(!GameController.Gaming)
                            break;
                        TimeUnit.MILLISECONDS.sleep(skill.frequency / 5);
                    }


                }

                //attack();
                // BattleField.display(UserInterface.getMyGraphicContext());
            }
           // GameController.cdLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}