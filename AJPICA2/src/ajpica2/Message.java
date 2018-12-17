package ajpica2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @author Joe
 */
public class Message 
{    
    private final String from;
    private final ArrayList<String> to;
    private String content = "";
    
    public Message(String from) 
    {
        this(from, null);
    }
    
    public Message(String from, String to) 
    {
        this.from = from;
        this.to = new ArrayList<>();
        if(to != null && to.length() > 0)
        {
            this.to.add(to);
        }
    }
    
    /**
     * Used to add the contents to a Message
     * @param appendWith The content section of a message
     */
    public void append(String appendWith) 
    {
        if(appendWith != null && appendWith.length() > 0) 
            content = content + appendWith;
        
    }
    
    
    @Override
    public String toString() 
    {
        return String.format("FROM:#%s#,TO:#%s#,CONTENT:#%s#",
                from,
                String.join(",", to),
                content);
    }
    
    static final Pattern MESSAGE_REGEX_PATTERN = Pattern.compile("^FROM:#([A-Za-z]+)#(,TO:#([A-Za-z]*)#)?,CONTENT:#(.+)#$");

    /**
     * Turns a string into a message object
     * @param message, the contents of the message
     * @return a ready to use message
     */
    public static Message parseMessage(String message) 
    {
        Message newMessage = null;
        Matcher matcher = MESSAGE_REGEX_PATTERN.matcher(message);
        if(matcher.matches())
        {
            newMessage = new Message(matcher.group(1),matcher.group(3));
            newMessage.append(matcher.group(4));
        }
        return newMessage;
    }
    
    /**
     * Used to form a connection between 2 Agents/Portals
     * @param from, the Agent/Portal sending the message
     */
    public static Message createHelloMessage(final String from) 
    {
        Message helloMessage = new Message(from);
        helloMessage.content = "HELLO";
        return helloMessage;
    }
    
    /**
     * Should be sent after receiving a HELLO
     * Message, the final act to sealing a bond between
     * 2 Agents/Portals
     * @param from, who is sending the message
     * @param to, who sent them the HELLO
     * @return, a Message to be sent back
     */
    public static Message createHelloAckMessage(final String from, final String to) 
    {
        Message helloAckMessage = new Message(from, to);
        helloAckMessage.content = "HELLOACK";
        return helloAckMessage;       
    }
    
    public List<String> getTo() 
    {
        return Collections.unmodifiableList(to); 
    }
    
    public String getFrom() 
    { 
        return from; 
    }
    
    public String getContent() 
    { 
        return content; 
    }
    
    public boolean isBroadcast() 
    { 
        return to.isEmpty(); 
    }
    
    public boolean isHelloMessage() 
    { 
        return content.compareTo("HELLO") == 0; 
    }
    
    public boolean isHelloAckMessage() 
    { 
        return content.compareTo("HELLOACK") == 0; 
    }
}
