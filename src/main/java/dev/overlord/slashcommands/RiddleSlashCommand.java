package dev.overlord.slashcommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.overlord.entities.RiddlesAPI;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class RiddleSlashCommand extends ListenerAdapter {

    private final Dotenv config;

    public Dotenv getConfig(){
        return config;
    }
    public RiddleSlashCommand(){
        config=Dotenv.configure().load();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equals("riddle")){
            EmbedBuilder embedBuilder = getEmbedBuilder(event);
            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage("Stay in the shadows of ignorance," +
                    " like a cowering cur, while the riddles reign supreme.").queue();

            event.getChannel()
                    .sendMessageEmbeds(embedBuilder.build())
                    .addActionRow(Button.danger("riddle","RIDDLE ME"),
                            Button.secondary("no","FEARFUL RETREAT")).queue();
        }

    }

    @NotNull
    private static EmbedBuilder getEmbedBuilder(SlashCommandInteractionEvent event) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setTitle("Requested by : "+ event.getUser().getEffectiveName());
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription("Enter the Riddle Realm at your peril. " +
                "Only the sharp-witted shall triumph; the rest shall crumble in defeat.");
        embedBuilder.setThumbnail(event.getUser().getEffectiveAvatarUrl());
        embedBuilder.setFooter(
                "For those who dare to unlock the secrets of the mind.");
        return embedBuilder;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        RiddleSlashCommand riddleSlashCommand = new RiddleSlashCommand();

        if(event.getComponentId().equals("riddle")){
            try{
                String riddles = config.get("RIDDLES");
                event.deferReply().setEphemeral(true).queue();
                event.getHook().sendMessage("Ughh, an idiot again").queue();

                String jsonResponse = riddleSlashCommand.performGETRequest(riddles);
                ObjectMapper objectMapper =new ObjectMapper();
                RiddlesAPI riddlesAPI = objectMapper.readValue(jsonResponse, RiddlesAPI.class);

                EmbedBuilder embedBuilder = getEmbedBuilder(event, riddlesAPI);

                event.getChannel().sendMessageEmbeds(embedBuilder.build()).queue();
            }catch (Exception e){
                throw new RuntimeException(e);
            }
        }
        else if(event.getComponentId().equals("no")){

            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage("Ughh, an idiot again").queue();

            event.getChannel()
                    .sendMessage("Pathetic. Running away like a coward?")
                    .addActionRow(Button.primary("yes","MOM HELP"),
                            Button.danger("riddle","CONQUER THE FEAR?")).queue();
        }
        else if(event.getComponentId().equals("yes")){

            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage("Ughh, an idiot again").queue();

            EmbedBuilder eb = getEmbedBuilder(event);
            event.getChannel()
                    .sendMessageEmbeds(eb.build()).queue();
        }
    }

    @NotNull
    private static EmbedBuilder getEmbedBuilder(ButtonInteractionEvent event) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setThumbnail(event.getUser().getEffectiveAvatarUrl());
        eb.setTitle("MOM CAN YOU PLEASE COME HERE FOR A BIT?");
        eb.setDescription("I think I shat myself , Mom. Can you please help me!");
        eb.setColor(Color.magenta);
        eb.setImage("https://imgs.search.brave.com/dQZorJ6O7yTQYT3RUFcAo-iJbEVWaHM7B_BddJWD5oE/rs:fit:860:0:0/g:ce/aHR0cHM6Ly9tZWRp/YS5pc3RvY2twaG90/by5jb20vaWQvMTgz/MjE1NzQ5L3Bob3Rv/L3Vwc2V0LWxpdHRs/ZS1ib3kuanBnP3M9/NjEyeDYxMiZ3PTAm/az0yMCZjPXdhOW42/ampmQlVsZ2NOSlVZ/WEtheldxaUlBbVR2/eUt3c3psVHZIbm1o/QWc9");
        eb.setFooter("Death by sword. Death by broken bones. Death by crushing. \n" +
                "There's not much difference, right? You die at the end.");
        return eb;
    }

    @NotNull
    private static EmbedBuilder getEmbedBuilder(ButtonInteractionEvent event, RiddlesAPI riddlesAPI) {
        String answer = riddlesAPI.getAnswer();
        String answerSpoiler = "||"+answer+"||";

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED);
        embedBuilder.setTitle("Requested By : "+ event.getUser().getEffectiveName());
        embedBuilder.setDescription("\n"+ riddlesAPI.getRiddle()+"\n\n\n"
                +"The answer to the riddle is: "+answerSpoiler);
        embedBuilder.setFooter(
                "\n\n\nFor those who dare to unlock the secrets of the mind.");
        return embedBuilder;
    }

    public String performGETRequest(String stringUrl) throws Exception {

        URL url = new URL(stringUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        String line;
        StringBuilder response = new StringBuilder();

        while((line =reader.readLine())!=null){
            response.append(line);
        }

        reader.close();
        connection.disconnect();

        return response.toString();
    }
}
