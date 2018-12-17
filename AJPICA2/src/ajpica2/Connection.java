package ajpica2;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * @author Joe
 */
public class Connection 
{
    private String handle;
    private final Socket socket;
    private final InputStream socketInputStream;
    private final InputStreamReader socketInputStreamReader;
    private final BufferedReader socketBufferedReader;
    private final PrintWriter printWriter;
    
    Connection(Socket s) throws IOException 
    {
        this((String)null, s);
    }
    
    Connection(String h, Socket s) throws IOException
    {
        handle = h;
        socket = s;
        socketInputStream = socket.getInputStream();
        socketInputStreamReader = new InputStreamReader(socketInputStream);
        socketBufferedReader = new BufferedReader(socketInputStreamReader);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }
    
    public void setHandle(final String h)
    {
        if(handle == null && h != null)
            handle = h;
    }
    
    public String getHandle()
    {
        return handle;
    }
    
    public void sendMessage(Message m)
    {
        printWriter.println(m.toString());
    }
    
    public Message recieveMessage() throws IOException
    {
        return Message.parseMessage(socketBufferedReader.readLine());
    }
    
    public boolean hasMessage() throws IOException 
    {
        return socketInputStream.available() > 0;
    }
    
    public boolean hasIpAddress(final String ip)
    {
        return socket.getInetAddress().getHostAddress().compareTo(ip) == 0;
    }
}