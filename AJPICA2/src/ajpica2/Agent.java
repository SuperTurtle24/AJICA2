/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;
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
    private ServerSocket serverSocket;
    private String Ip;
    private String handle;
    private int port;
    private final Object lock = new Object();
    
    private HashMap<String, Portal> portalMap = new HashMap<>();
    private HashMap<String, Agent> agentMap = new HashMap<>();
    
    private HashMap<String, Connection> connectionMap = new HashMap<>();
    
    /**
     * We want this mostly for bug checking, making sure our connections
     * aren't breaking somewhere
     */
    Thread acceptThread = new Thread(
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
    
    public Agent(String h, String i, int p)
    {
        handle = h;
        Ip = i;
        port = p;
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
        if(serverSocket == null) 
        {
            InetAddress bindAddress = InetAddress.getByName(this.Ip);
            serverSocket = new ServerSocket(this.port, 0, bindAddress); 
            acceptThread.start();
        }
    }
    
    public void connectTo(final String ip, final int port)
    {
        if(isAlreadyConnected(ip))
        {
            System.err.println("Already Connected to " + ip);
            return;
        }
        Thread helloThread = new Thread(
                new Runnable()
                {
                    @Override
                    public void run()
                    {
                        InetAddress bindAddress;
                        try
                        {
                            System.out.println("Attempting to estabalish Connection");
                            bindAddress = InetAddress.getByName(ip);
                            Socket newSocket = new Socket(bindAddress, port);
                            Connection partialConnection = new Connection(newSocket);
                            partialConnection.sendMessage(Message.createHelloMessage(handle));
                            
                            while(!partialConnection.hasMessage())
                            {
                                /*
                                Does nothing currently, we need to make it have a timeout
                                */
                            }
                            Message ackMessage = partialConnection.recieveMessage();
                            
                            if(ackMessage.isHelloAckMessage())
                            {
                                partialConnection.setHandle(ackMessage.getFrom());
                                addConnection(partialConnection);
                            }
                        }
                        catch(UnknownHostException e)
                        {
                            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e);
                        }
                        catch(IOException e)
                        {
                            Logger.getLogger(Agent.class.getName()).log(Level.SEVERE, null, e);
                        }
                    }
                }
        );
        
        helloThread.start();
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
    
    public void sendMessage(Message m)
    {
        synchronized(lock)
        {
            final List<String> receivers = m.getTo();
            
            for(String receiver : receivers)
            {
                Connection connection = connectionMap.get(receiver);
                if(connection != null)
                    connection.sendMessage(m);
                else
                    System.err.println(receiver + " is an unknown agent");
            }
        }
    }
    
    private synchronized boolean isAlreadyConnected(final String ipAddress) 
    {
        for(Connection c : connectionMap.values()) 
        {
            if(c.hasIpAddress(ipAddress)) 
                return true;
        }
        return false;
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
    
    public synchronized List<String> getConnectionHandles() {
        List<String> handleList = new ArrayList<>();
        connectionMap.
             values().
             stream().
             forEach
                ((connection) -> { handleList.add(connection.getHandle()); }
                );
        
        Collections.sort(handleList);
        
        return Collections.unmodifiableList(handleList);
    }
    
    public synchronized int connectionCount()
    {
        return connectionMap.size();
    }
    
    public synchronized boolean hasConnection()
    {
        return connectionCount() > 0;
    }
    
    public String getHandle() 
    { 
        return handle; 
    }
    
    public int getPort()
    {
        return port;
    }
}
