package Events;

import net.dv8tion.jda.api.entities.Message;

public class MessageRecieved {
    public MessageRecieved(Message msg){
        
        // System.out.println("Hello mfrom command runner !");
        if (!msg.getAuthor().isBot()){
            
            switch (msg.getContentDisplay()) {
                case "Hello":
                    msg.reply("Hello").queue();
                    break;
                
                    case "Ping":
                        msg.reply("Pong!").queue();
                        break;
            
                default:
                    break;
            }
        }
    }
}
