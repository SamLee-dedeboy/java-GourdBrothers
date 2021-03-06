package lydGourdBrother.GamingCollections;

import lydGourdBrother.Beings.Gourd;
import lydGourdBrother.Beings.Grandpa;
import lydGourdBrother.Controller.Constants;

import java.util.*;
public class Heros extends Group {
    //
    //member variables
    //
    public static ArrayList<Gourd> gourdBrothers;
    private static Heros instance = new Heros();
    private static int n;
    private static Grandpa grandpa = Grandpa.getInstance();
    private static int skillFrequency = 0;
    //
    //member functions
    //
    public synchronized static Heros getInstance() {
        if (instance == null)
            instance = new Heros();
        return instance;
    }
    private Heros() {
        gourdBrothers = new ArrayList<>(Arrays.asList(Gourd.values()));
        grandpa = Grandpa.getInstance();
        n = 7;
    }
    public static void resetRound() {
        grandpa.cheering = false;
        for(Gourd gourd: gourdBrothers) {
                gourd.skill.setFrequency(Constants.initialFrequency);
                gourd.setDead(false);
        }
        grandpa.setDead(false);
        BattleField.resetAllBlock();
        instance.snake();
    }
    public static boolean AllDead() {
        if (grandpa.isDead()) {
            for (int i = 0; i < 7; i++) {
                if (!gourdBrothers.get(i).isDead())
                    return false;
            }
            return true;
        }
        return false;
    }
    public static synchronized void setFrequency(int frequency) {
        skillFrequency = frequency;
        for(int i = 0; i < 7; i++)
            gourdBrothers.get(i).skill.setFrequency(frequency);
    }
    public static int getFrequency() {
        return skillFrequency;
    }
    public static void stopAllThreads() {
        for (int i = 0; i < 7; i++) {
            gourdBrothers.get(i).setDead(true);
        }
        grandpa.setDead(true);
    }

    public void snake() {
        int length = BattleField.getHeight();
        for (int i = 0; i < n; i++) {
            gourdBrothers.get(i).moveTo(BattleField.at(length / 2 - gourdBrothers.size() / 2 + i, length / 4));
        }
        grandpa.moveTo(BattleField.at(length / 2, 0));
    }

    public void crane() {
        int length = BattleField.getWidth();
        for (int i = 0; i < n; i++) {
            if (i + 1 <= (n + 1) / 2) {
                gourdBrothers.get(i).moveTo(BattleField.at(length / 2 - n / 2 + i, length / 4 - n / 2 + i));
            } else {
                gourdBrothers.get(i).moveTo(BattleField.at(length / 2 - n / 2 + i, length / 4 - n / 2 - (i - (n + 1) / 2) + 2));
            }
        }
        grandpa.moveTo(BattleField.at(length / 2, 0));
    }

    public void wildGoose() {
        int length = BattleField.getWidth();
        for (int i = 0; i < n; i++) {
            gourdBrothers.get(i).moveTo(BattleField.at(length / 2 - n / 2 + i, length / 4 - n / 2 + n - i - 1));
        }
    }


    public void yoke() {
        ;
    }

    public void scale() {
        ;
    }

    public void diamond() {
        ;
    }

    public void crescent() {
        ;
    }

    public void arrow() {
        ;
    }

}
