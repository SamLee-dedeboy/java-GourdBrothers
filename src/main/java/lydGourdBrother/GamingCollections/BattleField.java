package lydGourdBrother.GamingCollections;

import lydGourdBrother.Beings.Organism;
import javafx.scene.canvas.GraphicsContext;
import lydGourdBrother.Controller.Constants;
import java.util.*;

public class BattleField {
    private static int height = Constants.m;
    private static int width = Constants.n;
    private static BattleField instance = new BattleField(height, width);

    private volatile static ArrayList<ArrayList<Block>> field;

    private BattleField(int m, int n) {
        field = new ArrayList<>(n);
        for (int i = 0; i < m; i++) {
            field.add(new ArrayList<>());
            for (int j = 0; j < n; j++) {
                field.get(i).add(new Block(i, j));
            }
        }
    }

    public static BattleField getInstance() {
        return instance;
    }

    @SuppressWarnings("uncheked")
    public synchronized static void setBeing(Organism being, int x, int y) {
        field.get(x).get(y).set(being);
    }

    public synchronized static Block at(int x, int y) {
        return field.get(x).get(y);
    }

    public static String tellName() {
        return null;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public synchronized static boolean collide(int x, int y) {
        return (at(x, y).getBeing() != null);
    }

    public static boolean hasEnemy(Organism.enumGroup myGroup, int x, int y, int range) {
        if (range == 1)
            range = 14;
        try {
            for (int i = y + 1; i <= y + range && i < width; i++) {
                if (at(x, i).getBeing() != null) {
                    if (!at(x, i).getBeing().isDead())
                        return (at(x, i).getBeing().group != myGroup);
                }
            }
        } catch (NullPointerException e) {
            return false;
        }
        return false;
    }

    public static void print() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (field.get(i).get(j).getBeing() == null) {
                    System.out.print("[ ] ");
                } else {
                    System.out.print(field.get(i).get(j).isUsingSkill() + " ");
                }
            }
            System.out.print("\n");
        }
    }

    static void resetAllBlock() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                at(i, j).setUsingSkill(false);
                at(i, j).setNull();
            }
        }
        //print();
    }

    public static synchronized void display(GraphicsContext g) {

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((at(i, j)).getBeing() != null) {
                    g.drawImage(at(i, j).getBeing().getImage(),
                            j * Block.size, i * Block.size, Block.size, Block.size);
                }
            }
        }
    }
}

