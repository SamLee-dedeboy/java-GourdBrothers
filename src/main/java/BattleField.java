import java.util.*;

public class BattleField {
    private static int width;
    private static int height;


    private static ArrayList<ArrayList <Block>> field;
    public BattleField(int m, int n) {
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

    public static Block at(int x, int y) {
        return field.get(x).get(y);
    }

    public String tellName() { return null; }

    public static int getWidth() { return width; }
    public static int getHeight() {return height; }

    public static void  removeAll() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                field.get(i).get(j).set(null);
            }
        }
    }

    public static void display() {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                if(field.get(i).get(j).get() == null) {
                    System.out.print("[ ] ");
                } else {
                    System.out.print(field.get(i).get(j).get().tellName() + " ");
                }
            }
            System.out.print("\n");
        }
    }
    public static void main(String[] argc) {
        Scanner s = new Scanner(System.in);
        BattleField battleField = new BattleField(10,16);
        Heros heros = new Heros();
        Monster monsters = new Monster();
        heros.snake(battleField);
       //battleField.display();
        boolean end = false;
        while(!end) {
            battleField.removeAll();
            heros.snake(battleField);

            System.out.print("请为蛇蝎精阵营输入0-7, 分别代表鹤翼阵, 雁行阵, 冲轭阵, 长蛇阵, 鱼鳞阵, 方円阵, 偃月阵和锋矢阵.\n" +
                    "输入 exit 退出程序.\n");
            String a = s.next();
            switch(a) {
                case "0":   monsters.crane(battleField); break;
                case "1":   monsters.wildGoose(battleField); break;
                case "2":   monsters.yoke(battleField); break;
                case "3":   monsters.snake(battleField); break;
                case "4":   monsters.scale(battleField); break;
                case "5":   monsters.diamond(battleField); break;
                case "6":   monsters.crescent(battleField); break;
                case "7":   monsters.arrow(battleField); break;
                default:    end = true;
            }
            if(!end) battleField.display();
        }
    }
}
