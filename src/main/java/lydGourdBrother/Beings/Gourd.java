package lydGourdBrother.Beings;

import lydGourdBrother.Controller.GameController;
import lydGourdBrother.Controller.LogController;
import javafx.scene.image.Image;
import lydGourdBrother.GamingCollections.BattleField;
import lydGourdBrother.Skills.Skill;

import java.util.concurrent.TimeUnit;

public class Gourd extends Organism implements Runnable {

    public enum enum_Gourd {
        RED("Brother-1", "RED", 0), ORANGE("Brother-2", "ORANGE", 1), YELLOW("Brother-3", "YELLOW", 2),
        GREEN("Brother-4", "GREEN", 3), CYAN("Brother-5", "CYAN", 4), BLUE("Brother-6", "BLUE", 5), PURPLE("Brother-7", "PURPLE", 6);
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

    private Gourd(int rank) {
        gourd = enum_Gourd.values()[rank];
        name = gourd.name;
        group = enumGroup.HERO;
        image = new Image(this.getClass().getClassLoader().getResource(("GourdBrother" + (rank +  1) +".jpg")).toString());
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
                } else
                    break;

                //attack();
                // lydGourdBrother.GamingCollections.BattleField.display(lydGourdBrother.Controller.Main.getMyGraphicContext());
            }
           // lydGourdBrother.Controller.GameController.cdLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //System.out.println(tellName() + " end");
    }
}