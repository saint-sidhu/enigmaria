package dev.overlord.slashcommands;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.overlord.entities.TruthNDare;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.awt.*;

public class TruthNDareCommand extends ListenerAdapter {

    private final Dotenv config;

    public TruthNDareCommand() {
        config =Dotenv.configure().load();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String command = event.getName();
        if(command.equals("tod")){
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.cyan);
            eb.setThumbnail(event.getUser().getEffectiveAvatarUrl());
            eb.setAuthor("Requested by : "+ event.getUser().getEffectiveName());
            eb.setDescription("Decide now: Truth, Dare, or risk the unknown with a Paranoid selection " +
                    ". Don't dawdle.");
            eb.setFooter(
                    "Remember, there's no turning back once you've chosen.");

            MessageEmbed embed = eb.build();

            event.getChannel()
                    .sendMessageEmbeds(embed)
                    .addActionRow(Button.primary("truth","Truth"),
                            Button.danger("dare","Dare"),
                            Button.success("paranoia","Paranoid"))
                    .queue();
            event.deferReply().setEphemeral(true).queue();
            event.getHook().sendMessage("Quit staring! Look below, where you belong!").queue();
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        TruthNDareCommand tnd = new TruthNDareCommand();
        if(event.getComponentId().equals("truth")){
            String truth = config.get("TRUTH");

            try {
                String jsonResponse = tnd.performGETRequest(truth);

                //Create ObjectMapper instance
                ObjectMapper objectMapper =new ObjectMapper();

                //Unmarshal JSON Response into java class
                TruthNDare truthNDare= objectMapper.readValue(jsonResponse, TruthNDare.class);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.cyan);
                eb.setThumbnail(event.getUser().getEffectiveAvatarUrl());
                eb.setAuthor("Truth requested by : "+ event.getUser().getEffectiveName());
                eb.setDescription(truthNDare.getQuestion());
                eb.setFooter(
                        "For those insolent enough to demand truth, prepare for the harsh reality.");

                MessageEmbed embed = eb.build();

                event.getChannel()
                        .sendMessageEmbeds(embed)
                        .addActionRow(Button.primary("truth","Truth"),
                                Button.danger("dare","Dare"),
                                Button.success("paranoia","Paranoid"))
                        .queue();
                event.deferReply().setEphemeral(true).queue();
                event.getHook().sendMessage("Quit staring! Get on your knees and look up at me").queue();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else if(event.getComponentId().equals("dare")){
            String dare = config.get("DARE");

            try {
                String jsonResponse = tnd.performGETRequest(dare);

                //Create ObjectMapper instance
                ObjectMapper objectMapper =new ObjectMapper();

                //Unmarshal JSON Response into java class
                TruthNDare truthNDare= objectMapper.readValue(jsonResponse, TruthNDare.class);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.cyan);
                eb.setThumbnail(event.getUser().getEffectiveAvatarUrl());
                eb.setAuthor("Dare requested by : "+ event.getUser().getEffectiveName());
                eb.setDescription(truthNDare.getQuestion());
                eb.setFooter(
                        "Only the bold and fearless need proceed.");

                MessageEmbed embed = eb.build();

                event.getChannel()
                        .sendMessageEmbeds(embed)
                        .addActionRow(Button.primary("truth","Truth"),
                                Button.danger("dare","Dare"),
                                Button.success("paranoia","Paranoid"))
                        .queue();
                event.deferReply().setEphemeral(true).queue();
                event.getHook().sendMessage("Quit staring! Get on your knees and look up at me").queue();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        else if(event.getComponentId().equals("paranoia")) {
            String paranoia = config.get("PARANOIA");

            try {
                String jsonResponse = tnd.performGETRequest(paranoia);

                //Create ObjectMapper instance
                ObjectMapper objectMapper = new ObjectMapper();

                //Unmarshal JSON Response into java class
                TruthNDare truthNDare = objectMapper.readValue(jsonResponse, TruthNDare.class);

                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.cyan);
                eb.setThumbnail(event.getUser().getEffectiveAvatarUrl());
                eb.setAuthor("Paranoia requested by : " + event.getUser().getEffectiveName());
                eb.setDescription(truthNDare.getQuestion());
                eb.setFooter(
                        "Proceed with caution! Paranoia will not tolerate fools lightly.");

                MessageEmbed embed = eb.build();

                event.getChannel()
                        .sendMessageEmbeds(embed)
                        .addActionRow(Button.primary("truth", "Truth"),
                                Button.danger("dare", "Dare"),
                                Button.success("paranoia", "Paranoid"))
                        .queue();
                event.deferReply().setEphemeral(true).queue();
                event.getHook().sendMessage("Quit staring! Get on your knees and look up at me").queue();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }
    public String performGETRequest(String urlString) throws Exception{

        // Create a URL object with the desired endpoint
        URL url = new URL(urlString);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Set up a BufferedReader to read the response from the server
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));

        //Read teh response line by line
        StringBuilder response = new StringBuilder();
        String line;
        while((line=reader.readLine())!= null){
            response.append(line);
        }
        reader.close();

        // Disconnect the connection
        connection.disconnect();

        // Return the response as a String
        return response.toString();
    }
}
