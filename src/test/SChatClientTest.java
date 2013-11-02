package test;

import com.data.ChatMessage;
import com.data.User;
import com.networking.SChatClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Tests the SChatClient implementation.
 * @author Gary Ye
 */
public class SChatClientTest {
    public static void main(String[] args){
        User me = null;
        SChatClient client = null;
        try{
            me = new User(Integer.parseInt(args[1]), args[0]);
        }catch(Exception e){
            System.err.println("usage: java SChatClientTest <user name> <id>");
            System.exit(1);
        }
        try {
            client = new SChatClient(me);
        } catch (IOException e) {
            System.err.println("Client Server Connection I/O Exception");
            System.exit(1);
        }
        try (BufferedReader in = new BufferedReader(new InputStreamReader(System.in))) {
            String line;
            while((line = in.readLine()) != null){
                String[] messageSplit = line.split(" ");
                try{
                    int id = Integer.parseInt(messageSplit[0]);
                    StringBuilder message = new StringBuilder("");
                    for(int i = 1; i < messageSplit.length; i++){
                        message.append(messageSplit[i]);
                        message.append(" \n".charAt(i + 1 == messageSplit.length ? 1 : 0));
                    }
                    client.sendMessage(new ChatMessage(me, new User(id), message.toString()));
                }catch(Exception e){
                    System.err.println("usage: <id> <message>");
                }
            }
        } catch (IOException e) {
            System.err.println("STDIN I/O Exception");
            System.exit(1);
        }
    }
}
