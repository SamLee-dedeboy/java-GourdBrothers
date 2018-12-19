import java.util.*;
public class Heros extends Group {
    private int n;
    //private Gourd[] gourdBrothers;
    public ArrayList<Gourd> gourdBrothers;
    private Grandpa grandpa;
    Heros(){
        gourdBrothers = new ArrayList<>(Arrays.asList(Gourd.values()));
        grandpa = Grandpa.getInstance();
        n = 7;
    }

    public void snake() {
        int length = BattleField.getHeight();
        for(int i = 0; i < n; i++){
            gourdBrothers.get(i).moveTo(BattleField.at(length/2 - gourdBrothers.size()/2 + i,length/4));
        }
        grandpa.moveTo(BattleField.at(length/2,0));
    }

    public void crane() {
        int length = BattleField.getWidth();
        for(int i = 0; i < n; i++) {
            if (i + 1 <= (n + 1)/2) {
                gourdBrothers.get(i).moveTo(BattleField.at(length/2 - n/2 + i,length/4 - n/2 + i));
            } else {
                gourdBrothers.get(i).moveTo(BattleField.at(length/2 - n/2 + i,length/4 - n/2 - (i - (n+1)/2) + 2));
            }
        }
        grandpa.moveTo(BattleField.at(length/2,0));
    }

    public void wildGoose(BattleField battleField){
        int length = battleField.getWidth();
        for(int i = 0; i < n; i++) {
            gourdBrothers.get(i).moveTo(battleField.at(length/2 - n/2 + i, length/4 - n/2 + n - i - 1));
        }
    }


    public void yoke(BattleField battleField){
        ;
    }
    public void scale(BattleField battleField){
        ;
    }
    public void diamond(BattleField battleField){
        ;
    }
    public void crescent(BattleField battleField){
        ;
    }
    public void arrow(BattleField battleField){
        for(int i = 0; i < n; i++){
;           ;
        }
    }

}
