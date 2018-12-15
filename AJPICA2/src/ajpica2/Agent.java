/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;
import java.util.concurrent.*;
import java.util.HashMap;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author super
 */
public class Agent 
{
    private static final String defaultIP = "127.0.0.1";
    private static final int defaultPort = 9090;
    
    private ServerSocket serverSocket;
    private String receivedIp;
    private String handle;
    private int receivedPort;
    private final Object lock = new Object();
    
    private final BlockingQueue messageList = new ArrayBlockingQueue(1024);
    private HashMap<String, Portal> portalMap = new HashMap<>();
    private HashMap<String, Agent> agentMap = new HashMap<>();
    
    private HashMap<String, Connection> connectionMap = new HashMap<>();
    
    /**
     * We want this mostly for bug checking, making sure our connections
     * aren't breaking somewhere
     */
    Thread helloThread = new Thread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    while(true)
                    {
                        try
                        {
                            final Socket newClientSocket = serverSocket.accept();
                            
                            final Connection newConnection = new Connection(newClientSocket);
                            System.out.println("Awaiting HELLO Message from a new connection");
                            while(!newConnection.hasMessage())
                            {
                                
                            }
                            final Message recievedMessage = newConnection.recieveMessage();
                            System.out.println("Message recieved: " + recievedMessage.toString());
                            
                            if(!recievedMessage.isHelloMessage())
                                System.err.println("HELLO Message was incomplete, connection is now being dropped");
                            else
                            {
                                final String newConnectionHandle = recievedMessage.getFrom();
                                
                                if(newConnectionHandle != null)
                                {
                                    synchronized(lock)
                                    {
                                        if(connectionMap.get(newConnectionHandle) == null)
                                        {
                                            newConnection.setHandle(newConnectionHandle);
                                            addConnection(newConnection);
                                            newConnection.sendMessage(Message.createHelloAckMessage(handle, newConnectionHandle));
                                        }
                                        else
                                        {
                                            System.err.println("Already connected to this peer: " + newConnectionHandle);
                                        }
                                    }
                                }
                            }
                        }
                        catch(IOException e)
                        {
                            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e );
                        }
                    }
                }
            }
    );
    
    Thread receivalThread = new Thread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    while(true)
                    {
                        synchronized(lock)
                        {
                            for(Connection connection : connectionMap.values())
                            {
                                try
                                {
                                    if(connection.hasMessage())
                                        System.out.println(connection.recieveMessage());
                                }
                                catch (IOException e)
                                {
                                    Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e);
                                }
                            }
                        }
                    }
                }
            }
    );
    
    public Agent(String h, String ri, int rp)
    {
        handle = h;
        receivedIp = ri;
        receivedPort = rp;
    }
    
    
    /**
     * This may need changes, we aren't a P2P chat service
     * so we don't really need to dynamically add Agents/Nodes
     * to our service.
     */
    public void start() throws IOException
    {
        startPeerReceiver();
        receivalThread.start();
    }
    
    private void startPeerReceiver() throws UnknownHostException, IOException 
    {
        if(serverSocket == null) {
            InetAddress bindAddress = InetAddress.getByName(this.receivedIp);
            serverSocket = new ServerSocket(this.receivedPort, 0, bindAddress); 
            helloThread.start();
        }
    }
    
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
    
    private void addConnection(final Connection connection) 
    {
        synchronized(lock) 
        {
            if(connectionMap.containsKey(connection.getHandle()))
            {
               System.err.println("[" + connection.getHandle() + "] is already an established connection."); 
               return;
            }
            connectionMap.put(connection.getHandle(), connection);
        }
    }
    
}
