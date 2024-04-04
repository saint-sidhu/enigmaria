package dev.overlord.slashcommands;

import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistrar extends ListenerAdapter {
    //Designing the slashCommand UI of discord basically

    @Override
    public void onGuildJoin(GuildJoinEvent event) {
        //Guild level command for when bot is already running and we add
        //it to a new guild

        List<CommandData> commands = new ArrayList<>();
        commands.add(Commands.slash("tod","Dare to play a game of no lies skinbag?"));
        commands.add(Commands.slash("riddle","Can't waste my time explaining to an idiot"));

        //event.getGuild().updateCommands().addCommands(commands).queue();
    }

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        //Guild level command for when
        // the bot is already present in guild and running as well

        List<CommandData> commands = new ArrayList<>();
        commands.add(Commands.slash("tod","Dare to play a game of no lies skinbag?"));
        commands.add(Commands.slash("riddle","Can't waste my time explaining to an idiot"));

        //event.getGuild().updateCommands().addCommands(commands).queue();
    }

    @Override
    public void onReady(ReadyEvent event) {
        //Global commands

        List<CommandData> commands = new ArrayList<>();
        commands.add(Commands.slash("tod","Dare to play a game of no lies skinbag?"));
        commands.add(Commands.slash("riddle","Can't waste my time explaining to an idiot"));
        event.getJDA().updateCommands().addCommands(commands).queue();
    }
}
