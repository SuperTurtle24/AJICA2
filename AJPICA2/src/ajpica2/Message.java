/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author super
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
    
    public void append(String appendWith) 
    {
        if(appendWith != null && appendWith.length() > 0) 
            content = content + appendWith;
        
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
    
    @Override
    public String toString() 
    {
        return String.format("FROM:#%s#,TO:#%s#,CONTENT:#%s#",
                from,
                String.join(",", to),
                content);
    }
    
    static final Pattern MESSAGE_REGEX_PATTERN = Pattern.compile("^FROM:#([A-Za-z]+)#(,TO:#([A-Za-z]*)#)?,CONTENT:#(.+)#$");

    public static Message parseMessage(String rawMessage) 
    {
        Message newMessage = null;
        Matcher matcher = MESSAGE_REGEX_PATTERN.matcher(rawMessage);
        if(matcher.matches()) 
        {
            newMessage = new Message(matcher.group(1),matcher.group(3));
            newMessage.append(matcher.group(4));
        }
        return newMessage;
    }
    
    public static Message createHelloMessage(final String from) 
    {
        Message helloMessage = new Message(from);
        helloMessage.content = "HELLO";
        return helloMessage;
    }
    
    public static Message createHelloAckMessage(final String from, final String to) 
    {
        Message helloAckMessage = new Message(from, to);
        helloAckMessage.content = "HELLOACK";
        return helloAckMessage;       
    }
}
