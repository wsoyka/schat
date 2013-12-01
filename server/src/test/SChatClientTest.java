package test;

import crypto.CryptoConstants;
import crypto.Cryptography;
import data.SQLiteManager;
import data.User;
import data.contents.ChatContent;
import networking.SChatClient;
import networking.SChatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Tests the SChatClient implementation.
 *
 * @author Gary Ye
 */
public class SChatClientTest {
    public static User getMyUser(String username){
        byte[] seed = new byte[2];
        seed[0] = 101;
        seed[1] = 102;
        return new User(username, Cryptography.gen_asymm_key(seed), Cryptography.gen_symm_key(seed));
    }
    public static User getServerUser(){
        byte[] seed = new byte[2];
        seed[0] = 100;
        seed[1] = 101;

        return new User(SChatServer.SERVER_ID, Cryptography.gen_asymm_key(seed), Cryptography.gen_symm_key(seed));
    }

    public static void main(String[] args) {
        // System.err.println("usage: java test.SChatClientTest <id> (<host name> <port>)");
        String username = args[0];
        String hostName = SChatServer.SERVER_NAME;
        int portNumber = SChatServer.PORT_ADDRESS;
        User me = getMyUser(username);

        if (args.length == 3) {
            hostName = args[1];
            portNumber = Integer.parseInt(args[2]);
        }

        SChatClient client = null;
        try {
            client = new SChatClient(hostName, portNumber, me);
        } catch (IOException e) {
            System.err.println("Could not connect to the server successfully.");
            e.printStackTrace();
            System.exit(1);
        }
        System.out.println("Logged in?");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while ((line = in.readLine()) != null) {
                String[] messageSplit = line.split(" ");
                try {
                    String receiverId = messageSplit[0];
                    StringBuilder message = new StringBuilder("");
                    for (int i = 1; i < messageSplit.length; i++) {
                        message.append(messageSplit[i]);
                        message.append(" \n".charAt(i + 1 == messageSplit.length ? 1 : 0));
                    }

                    client.sendMessage(new ChatContent(message.toString()), receiverId);
                } catch (Exception e) {
                    System.err.println("usage: <receiver id> <message>");
                }
            }
            in.close();
        } catch (IOException e) {
            System.err.println("STDIN I/O Exception");
            System.exit(1);
        }
    }
}
