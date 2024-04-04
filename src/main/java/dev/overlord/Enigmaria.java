package dev.overlord;


import dev.overlord.events.MessageEvent;
import dev.overlord.slashcommands.CommandRegistrar;
import dev.overlord.slashcommands.RiddleSlashCommand;
import dev.overlord.slashcommands.TruthNDareCommand;
import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.security.auth.login.LoginException;

public class Enigmaria
{
    private final ShardManager shardManager;

    private final Dotenv config;

    public ShardManager getShardManager(){
        return shardManager;
    }
    public Dotenv getConfig(){
        return config;
    }

    public Enigmaria() throws LoginException{
        config = Dotenv.configure().load();
        String token = config.get("TOKEN");
        DefaultShardManagerBuilder builder = DefaultShardManagerBuilder.createDefault(token);
        builder.setActivity(Activity.customStatus("No Pestering"));
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        builder.enableIntents(
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_VOICE_STATES
        );
        shardManager = builder.build();

        //Register listeners
        shardManager.addEventListener(new MessageEvent());
        shardManager.addEventListener(new CommandRegistrar());
        shardManager.addEventListener(new TruthNDareCommand());
        shardManager.addEventListener(new RiddleSlashCommand());
    }
    public static void main( String[] args )
    {
        try{
            Enigmaria powerOn = new Enigmaria();
        }
        catch (LoginException e){
            System.out.println("Invalid Power On Credentials");
        }
    }
}
