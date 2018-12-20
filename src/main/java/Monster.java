import java.util.*;
public class Monster extends Group {
    static Serpent SERPENT;
    static Scorpion SCORPION;
    //static Minion[] minions;
    static ArrayList<Minion> minions;
    int num_of_minion;
    Monster() {
        num_of_minion = 20;
        SCORPION = new Scorpion();
        SERPENT = new Serpent();
        minions = new ArrayList<>(Arrays.asList(new Minion[num_of_minion]));
        for(int i = 0; i < num_of_minion; i++) {
            minions.set(i, new Minion());
        }
    }
    public void reformate(){
        for(int i = 0; i < num_of_minion; i++){
                minions.get(i).fallBack();
        }
        SERPENT.fallBack();
        SCORPION.fallBack();
    }
    public synchronized void snake(){
        reformate();
        int height = BattleField.getHeight();
        int width = BattleField.getWidth();
        num_of_minion = 6;
        SCORPION.moveTo(BattleField.at(height / 2 , width * 3 / 4));
        for(int i = 0; i < num_of_minion; i++) {
            if (i < num_of_minion / 2) {
                minions.get(i).moveTo(BattleField.at(height / 2 - num_of_minion / 2 + i, width * 3 / 4));
            } else {
                minions.get(i).moveTo(BattleField.at(height / 2 - num_of_minion / 2 + i + 1, width * 3 / 4));
            }
        }
        SERPENT.moveTo(BattleField.at(height/2, width - 1));
    }
    public synchronized void crane(){
        reformate();
        int n = 6;
        num_of_minion = n;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        SCORPION.moveTo(BattleField.at(height/2, width*3/4));
        for(int i = 0; i < n; i++) {
            if (i + 1 <= (n + 1)/2) {
                minions.get(i).moveTo(BattleField.at(height/2 - n/2 + i,width*3/4 - n/2 + i));
            } else {
                minions.get(i).moveTo(BattleField.at(height/2 - n/2 + i + 1,width*3/4 - n/2 - (i - (n+1)/2) + 2));
            }
        }
        SERPENT.moveTo(BattleField.at(height/2,width - 1));
    }
    public synchronized void wildGoose(){
        reformate();
        int n = 6;
        num_of_minion = n;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        SCORPION.moveTo(BattleField.at(height/2, width*3/4));
        for(int i = 0; i < n; i++) {
            if (i < n / 2) {
                minions.get(i).moveTo(BattleField.at(height / 2 - n / 2 + i, width*3 / 4 - n / 2 + n - i - 1));
            } else {
                minions.get(i).moveTo(BattleField.at(height / 2 - n / 2 + i + 1, width*3 / 4 - n / 2 + n - i));
            }
        }
        SERPENT.moveTo(BattleField.at(height/2, width - 1));
    }
    public synchronized void yoke(){
        reformate();
        int n = 6;
        num_of_minion = n;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        SCORPION.moveTo(BattleField.at(height/2, width*3/4));
        for(int i = 0; i < n; i++) {
            if (i < n / 2) {
                minions.get(i).moveTo(BattleField.at(height / 2 - n/2 + i, width * 3 / 4 + i % 2 - 1));
            } else {
                minions.get(i).moveTo(BattleField.at(height / 2 - n/2 + i + 1, width * 3 / 4 + (i + 1) % 2 - 1));
            }
        }
        SERPENT.moveTo(BattleField.at(height/2, width - 1));
    }
    public synchronized void scale(){
        reformate();
        int n = 6;
        num_of_minion = n;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        int col = 3;
        int cur = 0;
        for(int i = 1; i <= col; i++){
            for(int j = 0; j < i; j++){
                minions.get(cur).moveTo(BattleField.at(height/2 -  i + 1 + 2*j, width/2 + col / 2 + i));
                cur++;
            }
        }
        SCORPION.moveTo(BattleField.at(height/2, width/2 + col + 2));
        SERPENT.moveTo(BattleField.at(height/2, width - 1));
    }
    public synchronized void diamond(){
        reformate();
        int n = 8;
        num_of_minion = n;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        int col = 5;
        int cur = 0;
        for(int i = 1; i <= col; i++){
            if(i == 1 || i == col){
                minions.get(cur).moveTo(BattleField.at(height/2, width / 2 + i - 1));
                cur++;
            } else {
                if (i <= col / 2 + 1) {
                    minions.get(cur).moveTo(BattleField.at(height / 2 - i + 1, width / 2 + i - 1));
                    cur++;
                    minions.get(cur).moveTo(BattleField.at(height / 2 + i - 1, width / 2 + i - 1));
                    cur++;
                } else {
                    minions.get(cur).moveTo(BattleField.at(height / 2 - (col - i), width / 2 + i - 1));
                    cur++;
                    minions.get(cur).moveTo(BattleField.at(height / 2 + (col - i), width / 2 + i - 1));
                    cur++;
                }
            }
        }
        SCORPION.moveTo(BattleField.at(height/2, width/2 + col / 2));
        SERPENT.moveTo(BattleField.at(height/2, width - 1));
    }
    public synchronized void crescent() {
        reformate();
        int n = 19;
        num_of_minion = n;
        int col = 3;
        int cur = 0;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        for(int i = 1; i <= col; i++){
            if(i == 1){
                minions.get(cur).moveTo(BattleField.at(height / 2 - 1, width / 2 + 1));
                cur++;
                minions.get(cur).moveTo(BattleField.at(height / 2 , width / 2 + 1));
                cur++;
                minions.get(cur).moveTo(BattleField.at(height / 2 + 1, width / 2 + 1));
                cur++;
            } else {
                minions.get(cur).moveTo(BattleField.at(height / 2 - 1, width / 2 + 1 + i - 1));
                cur++;
                minions.get(cur).moveTo(BattleField.at(height / 2 , width / 2 + 1 + i - 1));
                cur++;
                minions.get(cur).moveTo(BattleField.at(height / 2 + 1, width / 2 + 1 + i - 1));
                cur++;
                for(int j = 0; j < i; j++){
                    minions.get(cur).moveTo(BattleField.at(height/2 - (j + 2), width / 2 + 1 + i - 1 + j + 1));
                    cur++;
                }
                for(int j = 0; j < i; j++){
                    minions.get(cur).moveTo(BattleField.at(height/2 + (j + 2), width / 2 + 1 + i - 1 + j + 1));
                    cur++;
                }
            }
        }
        SCORPION.moveTo(BattleField.at(height/2, width/2));
        SERPENT.moveTo(BattleField.at(height/2, width - 1));
    }
    public synchronized void arrow() {

            reformate();
            int n = 12;
            num_of_minion = n;
            int col = 6;
            int cur = 0;
            int width = BattleField.getWidth();
            int height = BattleField.getHeight();
            for (int i = 0; i < col; i++) {
                minions.get(cur).moveTo(BattleField.at(height / 2, width / 2 + i));
                cur++;
                if (i != 0 && i <= col / 2) {
                    minions.get(cur).moveTo(BattleField.at(height / 2 - i, width / 2 + i));
                    cur++;
                    minions.get(cur).moveTo(BattleField.at(height / 2 + i, width / 2 + i));
                    cur++;
                }
            }
            SCORPION.moveTo(BattleField.at(height / 2, width - 2));
            SERPENT.moveTo(BattleField.at(height / 2, width - 1));
            //BattleField.display(UserInterface.getMyGraphicContext());

    }
}