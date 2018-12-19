import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import javax.management.relation.RoleUnresolved;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class Gourd extends Organism implements Runnable {

    public enum enum_Gourd {
        RED("老大", "赤", 0), ORANGE("老二", "橙", 1), YELLOW("老三", "黄", 2),
        GREEN("老四", "绿", 3), CYAN("老五", "青", 4), BLUE("老六", "蓝", 5), PURPLE("老七", "紫", 6);
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

    public String tellName() {
        return this.gourd.name;
    }

    public String tellColor() {
        return this.gourd.color;
    }
    public int tellRank() {
        return this.gourd.rank;
    }

    public void report(int src, int dst) {
        System.out.println(this.gourd.name + ": " + src + "->" + dst);
    }
    Gourd(int rank){
        group = enumGroup.HERO;
        image = new Image("file:D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\" + (rank + 1) +".jpg");
        gourd = enum_Gourd.values()[rank];
        skill = new Skill(this);
    }
    public Image getImage() { return this.image; }

    public static Gourd[] values(){
        Gourd[] gourdBrothers = new Gourd[7];
        for(int i = 0; i < 7; i++){
            gourdBrothers[i] = new Gourd(i);
        }
        return gourdBrothers;
    }
    @Override
    public void run() {
        try {
            while(GameController.Gaming) {
                TimeUnit.MILLISECONDS.sleep(1000);
                skill.useSkill(position.getX(), position.getY() + 1);


                //attack();
                // BattleField.display(UserInterface.getMyGraphicContext());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void attack()throws Exception {
        if (BattleField.hasEnemy(group, position.getX(), position.getY(), skill.skillRange)) {
            skill.useSkill(position.getX(), position.getY());
        }
    }
}