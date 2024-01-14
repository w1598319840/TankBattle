import java.io.BufferedWriter;
import java.nio.Buffer;

public class Recorder {
    //用于记录相关信息并用IO流实现文件交互
    private static BufferedWriter bw;
    private static String destPath = System.getProperty("user.dir") + "//Data//myRecord.txt";
}
