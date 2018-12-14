/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;
import java.util.concurrent.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author super
 */
public class Portal 
{
    private HashMap agentMap = new HashMap<String, Agent>();
    private final BlockingQueue messageList = new ArrayBlockingQueue(1024);
    
    public void addAgent(String k, Agent a)
    {
        agentMap.put(k, a);
    }
    
    public void removeAgent(String k)
    {
        agentMap.remove(k);
    }
    
    public void recieveMessage()
    {
        
    }
    
    public void sendMessage(Message m, String k)
    {
        
    }
}
