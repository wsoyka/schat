import networking.SChatServer;

/**
 * SChat Server tester
 *
 * @author Gary Ye
 * @version 2013/11/29
 */
public class ServerMain {
    public static void main(String[] args) {
        try {
            new SChatServer(Integer.parseInt(args[0]));
        } catch (Exception e) {
            System.err.println("usage: java ServerMain <port>");
        }
    }
}
