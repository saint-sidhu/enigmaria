package dev.overlord.events;

import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class MessageEvent extends ListenerAdapter {

    public void onMessageReceived(@NotNull MessageReceivedEvent event){
        String messageSent =  event.getMessage().getContentRaw();
        if(messageSent.equalsIgnoreCase("riddle")){
            event.getChannel().sendMessage("RIDDLE IS TO BE ASKED").queue();
        }
        else if(messageSent.equalsIgnoreCase
                ("hi") ){
            if(event.getAuthor().getEffectiveName().equals("Sidhu"))

                event.getChannel().sendMessage("Ah, esteemed ," +
                        event.getMember().getEffectiveName()+"-sama"+
                    " master of enigmas and purveyor of wisdom, I offer you my humble greetings. " +
                        "How may I serve you today?").queue();

            else{
                event.getChannel().sendMessage("Ah, another " +
                        "pest intrudes upon my presence. State your purpose quickly, " +
                        "for my time is valuable, unlike yours.").queue();
            }
        }
        else if(event.isFromType(ChannelType.PRIVATE) && !event.getAuthor().isBot()){
            event.getChannel().sendMessage("Ah, you seek my attention in private. How quaint. " +
                    "Speak your mind quickly, for I have more important matters to attend to.").queue();
        }
        if (event.isFromType(ChannelType.PRIVATE)) {
            System.out.printf("[PM] %s: %s\n", event.getAuthor().getName(),
                    event.getMessage().getContentDisplay());
        } else {
            System.out.printf("[%s][%s] %s: %s\n", event.getGuild().getName(),
                    event.getChannel().getName(), event.getMember().getEffectiveName(),
                    event.getMessage().getContentDisplay());
        }
    }

}
