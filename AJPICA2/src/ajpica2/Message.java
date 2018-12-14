/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ajpica2;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author super
 */
public class Message 
{
    static final Pattern Message_Regex_Pattern = Pattern.compile("^From:#([A-Za-z]+)#(,TO:#([A-Za-z]*)#)?,Contains:#(.+)#$");
    
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
            this.to.add(to);
    }
    
    public void append(String appendWith)
    {
        if(appendWith != null && appendWith.length() > 0)
            content = content + appendWith;
    }
    
    public ArrayList<String> getTo() 
    {
        return to;
    }
    
    public String getFrom()
    {
        return from;
    }
    
    public String getContent()
    {
        return content;
    }
    
    @Override
    public String toString()
    {
        return String.format("^From:#([A-Za-z]+)#(,TO:#([A-Za-z]*)#)?,Contains:#(.+)#$",
                from,
                String.join(",", to),
                content);
    }
    
    public static Message parseMessage(String message)
    {
        System.out.println("Message: " + message);
        Message newM = null;
        
        Matcher match = Message_Regex_Pattern.matcher(message);
        
        if(match.matches())
        {
            newM = new Message(match.group(1), match.group(3));
            newM.append(match.group(4));
        }
        return newM;
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
