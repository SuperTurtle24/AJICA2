/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;
import java.util.concurrent.*;
import java.util.HashMap;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author super
 */
public class Agent 
{
    private static final String defaultIP = "127.0.0.1";
    private static final int defaultPort = 9090;
    
    private ServerSocket serverSocket;
    private String recievedIp;
    private String handle;
    private int recievedPort;
    
    private final BlockingQueue messageList = new ArrayBlockingQueue(1024);
    private HashMap portalMap = new HashMap<String, Portal>();
    private HashMap agentMap = new HashMap<String, Agent>();
    
    public void registerPortal(String k, Portal p)
    {
        portalMap.put(k, p);
    }
    
    public void unregisterPortal(String k)
    {
        portalMap.remove(k);
    }
    
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
