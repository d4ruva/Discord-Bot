package Discord;

import java.util.Collections;

import javax.security.auth.login.LoginException;

import Events.MessageRecieved;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class App extends ListenerAdapter{
    public static void main(String[] args) throws LoginException
    {
        if (args.length < 1) {
            System.out.println("You have to provide a token as first argument!");
            System.exit(1);
        }

        JDA jda = JDABuilder.createLight(args[0], Collections.emptyList())
            .addEventListeners(new App())
            .setActivity(Activity.playing("Type /ping"))
            .build();
        jda.upsertCommand("ping","Calculate the Ping of Bot").queue();

    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event)
    {
        if (!event.getName().equals("ping")) return; // make sure we handle the right command
        long time = System.currentTimeMillis();
        event.reply("Pong!").setEphemeral(true) // reply or acknowledge
             .flatMap(v ->
                 event.getHook().editOriginalFormat("Pong: %d ms", System.currentTimeMillis() - time) // then edit original
             ).queue(); // Queue both reply and edit
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        if (event.isFromType(ChannelType.PRIVATE))
        {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                                    event.getMessage().getContentDisplay());
        }
        else
        {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                        event.getTextChannel().getName(), event.getMember().getEffectiveName(),
                        event.getMessage().getContentDisplay());

            new MessageRecieved(event.getMessage());
        }
    }    
}
