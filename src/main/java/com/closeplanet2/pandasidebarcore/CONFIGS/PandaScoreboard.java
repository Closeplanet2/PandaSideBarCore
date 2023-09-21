package com.closeplanet2.pandasidebarcore.CONFIGS;

import com.closeplanet2.pandaconfigcore.INTERFACE.IgnoreSave;
import com.closeplanet2.pandaconfigcore.INTERFACE.PandaConfig;
import com.closeplanet2.pandasidebarcore.PandaSideBarCore;
import org.apache.maven.model.Build;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;

public class PandaScoreboard implements PandaConfig {

    @Override
    public String CONFIG_ID() { return scoreboardID; }

    @Override
    public Class<?> CLASS_TYPE() { return PandaScoreboard.class; }

    @Override
    public Class<?> MAIN_CLASS() {return PandaSideBarCore.class;}

    @IgnoreSave
    private Scoreboard scoreboard;
    public String scoreboardID = "";
    public HashMap<Integer, ScoreboardLineHolder> lineHolders = new HashMap<>();
    public Integer highestCount = 0;
    @IgnoreSave
    public int currentPosition = -1;

    public void RemovePlayer(Player... players) {
        for(var player : players) player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    public PandaScoreboard(){}
    public PandaScoreboard(String scoreboardID){
        this.scoreboardID = scoreboardID;
        LOAD();
    }
    public PandaScoreboard(String scoreboardID, List<Player> players, HashMap<Integer, ScoreboardLineHolder> lineHolders, Integer highestCount){
        this.scoreboardID = scoreboardID;
        this.lineHolders = lineHolders;
        this.highestCount = highestCount;
        CreateScoreboard();
        for(var player : players) player.setScoreboard(scoreboard);
        PandaSideBarCore.pandaScoreboards.put(scoreboardID, this);
        SAVE();
    }

    private void CreateScoreboard(){
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        lineHolders.get(0).CreateLine(scoreboard, scoreboardID);
    }

    public void UpdateScoreboard(){
        if(scoreboard == null) CreateScoreboard();
        currentPosition += 1;
        currentPosition = currentPosition >= highestCount ? 0 : currentPosition;
        lineHolders.get(currentPosition).UpdateLine(scoreboard);
    }

    public void LoadScoreboard(List<Player> players){
        if(scoreboard == null) CreateScoreboard();
        for(var player : players) player.setScoreboard(scoreboard);
    }

    public static Builder builder(){ return new Builder(); }

    public static class Builder {
        private String scoreboardID = "";
        private HashMap<Integer, ScoreboardLineHolder> lineHolder = new HashMap<>();
        private List<Player> playersToAdd = new ArrayList<>();
        private int highestCount = 0;
        private Builder(){}

        public Builder scoreboardID(String scoreboardID){
            this.scoreboardID = scoreboardID;
            return this;
        }

        public Builder addPlayer(Player... players){
            Collections.addAll(playersToAdd, players);
            return this;
        }

        public Builder addLineHolder(Integer amount, ScoreboardLineHolder data){
            for(var i = 0; i < amount; i++){
                lineHolder.put(highestCount, ScoreboardLineHolder.CLONE_WITH_NEW_KEY(data, highestCount));
                highestCount += 1;
            }
            return this;
        }

        public PandaScoreboard build(boolean override_stored){
            if(override_stored) return new PandaScoreboard(scoreboardID, playersToAdd, lineHolder, highestCount);
            var stored_data = PandaSideBarCore.pandaScoreboards.get(scoreboardID);
            if(stored_data != null){
                stored_data.LoadScoreboard(playersToAdd);
                return stored_data;
            }
            return new PandaScoreboard(scoreboardID, playersToAdd, lineHolder, highestCount);
        }
    }

}
