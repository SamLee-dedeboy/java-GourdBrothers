import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import sun.rmi.runtime.Log;

import java.io.*;
import java.util.ArrayList;

public class LogController {
    private static final String prefix = "D:\\IDEA-projects\\GourdBrothers\\src\\main\\resources\\";
    private static final String logName = "log";
    private static final String suffix = ".txt";
    private static final String cnfgName = "config.txt";
    private static BufferedWriter log;
    private static int logNum;
    public static LogController instance = new LogController();
    private static ArrayList<String> buf = new ArrayList<>();
    private LogController() {
        try {
            BufferedReader cnfgFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(prefix + cnfgName)));
            String str;
            str = cnfgFileReader.readLine();

            logNum = Integer.parseInt(str);
            cnfgFileReader.close();

            logNum++;
            BufferedWriter cnfgFileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(prefix + cnfgName)));
            cnfgFileWriter.write(((Integer)logNum).toString());
            cnfgFileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LogController getInstance() {
        return instance;
    }
    public static void openNewRoundLog(int roundNum) {
        try {
            log = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(prefix + logName + logNum + "-Round" + roundNum + suffix)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static synchronized void writeLog(String record) {
        try {
            log.write(record);
            log.newLine();
            buf.add(record);
        }
        catch(IOException e) {
            openNewRoundLog(GameController.getCurRound());
            try {
                System.out.println("write log");
                for(String s : buf) {
                    log.write(s);
                    log.newLine();
                }
                log.write(record);
                log.close();
            }
            catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    public static void saveLog() {
        try {
            log.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        LogController.writeLog("testing");
        LogController.saveLog();
    }
}
