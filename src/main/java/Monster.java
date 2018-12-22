
import java.util.*;
public class Monster extends Group {
    private static Monster instance = new Monster();
    public static Serpent SERPENT = Serpent.getInstance();
    public static Scorpion SCORPION = Scorpion.getInstance();
    //static Minion[] minions;
    static ArrayList<Minion> minions;
    public static int numOfMinion;
    private static int initialNumOfMinion = 20;
    private static int numOfAliveMinion = initialNumOfMinion;

    //
    //Singleton
    //
    public synchronized static Monster getInstance() {
        if (instance == null)
            instance = new Monster();
        return instance;
    }

    private Monster() {
        numOfMinion = 0;
        minions = new ArrayList<>(Arrays.asList(new Minion[initialNumOfMinion]));
        for (int i = 0; i < initialNumOfMinion; i++) {
            minions.set(i, new Minion());
        }
    }

    public static void stopAllThreads() {
        SCORPION.setDead(true);
        SERPENT.setDead(true);
        for (int i = 0; i < numOfMinion; i++)
            minions.get(i).setDead(true);
    }

    public static void SetMinionDead() {
        numOfAliveMinion--;
    }

    public static boolean AllDead() {
        return SCORPION.isDead() && numOfAliveMinion <= 0;
    }

    public static void resetRound() {
        SERPENT.fallBack();
        SCORPION.fallBack();
        for (int i = 0; i < numOfMinion; i++) {
            minions.get(i).fallBack();
        }
    }
    //
    //formation related functions
    //
    public void reformate() {
        SERPENT.setDead(false);
        SERPENT.healthPoint = Constants.initialHealthPoint;
        SCORPION.setDead(false);
        SCORPION.healthPoint = Constants.initialHealthPoint;
        for (int i = 0; i < numOfMinion; i++) {
            minions.get(i).setDead(false);
            SERPENT.healthPoint = Constants.initialHealthPoint;
            minions.get(i).fallBack();
        }
        SERPENT.fallBack();
        SCORPION.fallBack();
    }
    public synchronized void snake() {
        reformate();
        int height = BattleField.getHeight();
        int width = BattleField.getWidth();
        numOfMinion = 6;
        numOfAliveMinion = numOfMinion;
        SCORPION.moveTo(BattleField.at(height / 2, width * 3 / 4));
        for (int i = 0; i < numOfMinion; i++) {
            if (i < numOfMinion / 2) {
                minions.get(i).moveTo(BattleField.at(height / 2 - numOfMinion / 2 + i, width * 3 / 4));
            } else {
                minions.get(i).moveTo(BattleField.at(height / 2 - numOfMinion / 2 + i + 1, width * 3 / 4));
            }
        }
        SERPENT.moveTo(BattleField.at(height / 2, width - 1));
    }

    public synchronized void crane() {
        reformate();
        int n = 6;
        numOfMinion = n;
        numOfAliveMinion = numOfMinion;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        SCORPION.moveTo(BattleField.at(height / 2, width * 3 / 4 + 1));
        for (int i = 0; i < n; i++) {
            if (i + 1 <= (n + 1) / 2) {
                minions.get(i).moveTo(BattleField.at(height / 2 - n / 2 + i, width * 3 / 4 - n / 2 + i + 1));
            } else {
                minions.get(i).moveTo(BattleField.at(height / 2 - n / 2 + i + 1, width * 3 / 4 - n / 2 - (i - (n + 1) / 2) + 3));
            }
        }
        SERPENT.moveTo(BattleField.at(height / 2, width - 1));
    }

    public synchronized void wildGoose() {
        reformate();
        int n = 6;
        numOfMinion = n;
        numOfAliveMinion = numOfMinion;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        SCORPION.moveTo(BattleField.at(height / 2, width * 3 / 4));
        for (int i = 0; i < n; i++) {
            if (i < n / 2) {
                minions.get(i).moveTo(BattleField.at(height / 2 - n / 2 + i, width * 3 / 4 - n / 2 + n - i - 1));
            } else {
                minions.get(i).moveTo(BattleField.at(height / 2 - n / 2 + i + 1, width * 3 / 4 - n / 2 + n - i));
            }
        }
        SERPENT.moveTo(BattleField.at(height / 2, width - 1));
    }

    public synchronized void yoke() {
        reformate();
        int n = 6;
        numOfMinion = n;
        numOfAliveMinion = numOfMinion;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        SCORPION.moveTo(BattleField.at(height / 2, width * 3 / 4 + 1));
        for (int i = 0; i < n; i++) {
            if (i < n / 2) {
                minions.get(i).moveTo(BattleField.at(height / 2 - n / 2 + i, width * 3 / 4 + i % 2 ));
            } else {
                minions.get(i).moveTo(BattleField.at(height / 2 - n / 2 + i + 1, width * 3 / 4 + (i + 1) % 2));
            }
        }
        SERPENT.moveTo(BattleField.at(height / 2, width - 1));
    }

    public synchronized void scale() {
        reformate();
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        int col = 4;
        int cur = 0;
        for (int i = 1; i <= col; i++) {
            for (int j = 0; j < i; j++) {
                if (i == 3 && j == 1) continue;
                minions.get(cur).moveTo(BattleField.at(height / 2 - i + 1 + 2 * j, width / 2 + col / 2 + i - 1));
                cur++;
            }
        }
        SCORPION.moveTo(BattleField.at(height / 2, width / 2 + col));
        SERPENT.moveTo(BattleField.at(height / 2, width - 1));
        numOfMinion = cur;
        numOfAliveMinion = numOfMinion;
    }

    public synchronized void diamond() {
        reformate();
        int n = 8;
        numOfMinion = n;
        numOfAliveMinion = numOfMinion;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        int col = 5;
        int cur = 0;
        for (int i = 1; i <= col; i++) {
            if (i == 1 || i == col) {
                minions.get(cur).moveTo(BattleField.at(height / 2, width / 2 + i - 1));
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
        SCORPION.moveTo(BattleField.at(height / 2, width / 2 + col / 2));
        SERPENT.moveTo(BattleField.at(height / 2, width - 1));
        numOfMinion = cur;
        numOfAliveMinion = numOfMinion;
    }

    public synchronized void crescent() {
        reformate();
        int col = 3;
        int cur = 0;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        for (int i = 1; i <= col; i++) {
            if (i == 1) {
                minions.get(cur).moveTo(BattleField.at(height / 2 - 1, width / 2 + 1));
                cur++;
//                minions.get(cur).moveTo(BattleField.at(height / 2, width / 2 + 1));
//                cur++;
                minions.get(cur).moveTo(BattleField.at(height / 2 + 1, width / 2 + 1));
                cur++;
            } else {
                if (i % 2 == 0) {
                    if(i!=2) {

                        minions.get(cur).moveTo(BattleField.at(height / 2, width / 2 + 1 + i - 1));
                        cur++;
                    }
                } else {
                    minions.get(cur).moveTo(BattleField.at(height / 2 - 1, width / 2 + 1 + i - 1));
                    cur++;
                    minions.get(cur).moveTo(BattleField.at(height / 2 + 1, width / 2 + 1 + i - 1));
                    cur++;
                }

                if (i % 2 == 1)
                    for (int j = 0; j < i - 1; j++) {
                        minions.get(cur).moveTo(BattleField.at(height / 2 - (j + 2), width / 2 + 1 + i - 1 + j + 1));
                        cur++;
                    }
                else
                    for (int j = 0; j < i; j++) {
                        minions.get(cur).moveTo(BattleField.at(height / 2 + (j + 2), width / 2 + 2 + i - 1 + j + 1));
                        cur++;
                    }
            }
        }
        SCORPION.moveTo(BattleField.at(height / 2, width / 2 + 2));
        SERPENT.moveTo(BattleField.at(height / 2, width - 1));
        numOfMinion = cur;
        numOfAliveMinion = numOfMinion;
    }

    public synchronized void arrow() {
        reformate();
        int col = 6;
        int cur = 0;
        int width = BattleField.getWidth();
        int height = BattleField.getHeight();
        for (int i = 0; i < col - 2; i++) {
            if(i%2 == 0) {
                minions.get(cur).moveTo(BattleField.at(height / 2, width / 2 + i));
                cur++;
            }
            if (i != 0) {
                minions.get(cur).moveTo(BattleField.at(height / 2 - i, width / 2 + i));
                cur++;
                minions.get(cur).moveTo(BattleField.at(height / 2 + i, width / 2 + i));
                cur++;
            }
        }
        SCORPION.moveTo(BattleField.at(height / 2, width - 3));
        SERPENT.moveTo(BattleField.at(height / 2, width - 1));
        numOfMinion = cur;
        numOfAliveMinion = numOfMinion;
    }
}