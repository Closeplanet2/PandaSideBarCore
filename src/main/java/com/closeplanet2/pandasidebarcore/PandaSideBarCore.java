package com.closeplanet2.pandasidebarcore;

import com.closeplanet2.pandaconfigcore.API.ConfigAPI;
import com.closeplanet2.pandasidebarcore.CONFIGS.PandaScoreboard;
import com.closeplanet2.pandasidebarcore.CONFIGS.PandaScoreboardSettings;
import com.closeplanet2.pandaspigotcore.JAVA_CLASS.JavaClassAPI;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.HashMap;

public class PandaSideBarCore extends JavaPlugin {
    public static PandaSideBarCore instance;
    public static PandaScoreboardSettings pandaScoreboardSettings;
    public static HashMap<String, PandaScoreboard> pandaScoreboards = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        pandaScoreboardSettings = new PandaScoreboardSettings();
        pandaScoreboardSettings.LOAD();
        RegisterClasses();
        LoadAllConfigs();
    }

    private void RegisterClasses(){
        try {JavaClassAPI.Register(this, "com.closeplanet2.pandasidebarcore");}
        catch (Exception e) {throw new RuntimeException(e);}
    }

    private void LoadAllConfigs(){
        for(var className : ConfigAPI.RETURN_ALL_CONFIG_NAMES(new PandaScoreboard())){
            var pandaScoreboard = new PandaScoreboard(className);
            pandaScoreboards.put(className, pandaScoreboard);
        }
    }
}
