import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.management.PlatformLoggingMXBean;
import java.util.*;

public class BattleField {
    private static int height = Constants.m;
    private static int width = Constants.n;
    private static BattleField instance = null;

    private static ArrayList<ArrayList <Block>> field;
    private BattleField(int m, int n) {
        field = new ArrayList<>(n);
        for(int i = 0; i < m; i++){
            field.add(new ArrayList<>());
           // field.get(i).addAll(Collections.nCopies(n, new Block()));
            for(int j = 0; j < n; j++){
                field.get(i).add(new Block(i,j));
            }
        }

        height = m;
        width = n;

    }
    public static BattleField getInstance() {
        if(instance == null) {
            instance = new BattleField(Constants.m, Constants.n);
        }
        return instance;
    }

    public static Block at(int x, int y) {
        return field.get(x).get(y);
    }

    public static String tellName() { return null; }

    public static int getWidth() { return width; }
    public static int getHeight() {return height; }

    public static boolean collide (int x, int y) {
        return (at(x,y).getBeing() != null);
    }
    public static boolean hasEnemy(Organism.enumGroup myGroup, int x, int y, int range) {
        for(int i = y; i < y + range; i++) {
            if(at(x,i).getBeing()!= null) {
                return (at(x,i).getBeing().group != myGroup);
            }
        }
        return false;
    }
    public static void  removeAll() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                field.get(i).get(j).set(null);
            }
        }
    }

    public static synchronized void display(GraphicsContext g) {

        //g.clearRect(0, 0, Constants.width - Constants.rightWidth, Constants.height);
//            synchronized (getInstance()) {
////                for (int i = 0; i < height; i++) {
////                    for (int j = 0; j < width; j++) {
////                        if (field.get(i).get(j).getBeing() == null) {
////                            System.out.print("[ ] ");
////                        } else {
////                            System.out.print(field.get(i).get(j).getBeing().tellName() + " ");
////                        }
////                    }
////                    System.out.print("\n");
////                }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((at(i, j)).getBeing() != null) {
                    g.drawImage(at(i, j).getBeing().getImage(),
                            j * Block.size, i * Block.size, Block.size, Block.size);
                }
            }
        }
    }

    public void drawTest(GraphicsContext g) {
        g.drawImage(Serpent.getInstance().getImage(), 80,80,80,80);
    }

    public static void main(String[] argc) {
//        Scanner s = new Scanner(System.in);
//        BattleField battleField = new BattleField(10,16);
//        Heros heros = new Heros();
//        Monster monsters = new Monster();
//        heros.snake(battleField);
//       //battleField.display();
//        boolean end = false;
//        while(!end) {
//            battleField.removeAll();
//            heros.snake(battleField);
//
//            System.out.print("请为蛇蝎精阵营输入0-7, 分别代表鹤翼阵, 雁行阵, 冲轭阵, 长蛇阵, 鱼鳞阵, 方円阵, 偃月阵和锋矢阵.\n" +
//                    "输入 exit 退出程序.\n");
//            String a = s.next();
//            switch(a) {
//                case "0":   monsters.crane(battleField); break;
//                case "1":   monsters.wildGoose(battleField); break;
//                case "2":   monsters.yoke(battleField); break;
//                case "3":   monsters.snake(battleField); break;
//                case "4":   monsters.scale(battleField); break;
//                case "5":   monsters.diamond(battleField); break;
//                case "6":   monsters.crescent(battleField); break;
//                case "7":   monsters.arrow(battleField); break;
//                default:    end = true;
//            }
//            //if(!end) battleField.display(g);
//        }
    }
}
