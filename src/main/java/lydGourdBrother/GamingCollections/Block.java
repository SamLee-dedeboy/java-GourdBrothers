package lydGourdBrother.GamingCollections;

import lydGourdBrother.Beings.Organism;
import lydGourdBrother.Controller.Constants;

public class Block <T extends Organism> {
    public static int size = Constants.Block_length;
    private T being = null;
    private T skillUsingBeing = null;
    private int x, y;
    private boolean usingSkill = false;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setNull() {
        this.being = null;
    }

    public void setUsingSkill(boolean flag) {
        usingSkill = flag;
    }

    public void setUsingSkill(boolean flag, T skillUsingBeing) {
        usingSkill = flag;
        if (flag)
            this.skillUsingBeing = skillUsingBeing;
        else
            this.skillUsingBeing = null;
    }

    public Organism getUsingSkillBeing() {
        return skillUsingBeing;
    }


    public T getBeing() {
        return this.being;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    void set(T being) {
        this.being = being;
    }
    boolean isUsingSkill() {
        return usingSkill;
    }
}
