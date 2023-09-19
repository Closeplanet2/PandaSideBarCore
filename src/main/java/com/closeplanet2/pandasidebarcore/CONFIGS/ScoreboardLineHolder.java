package com.closeplanet2.pandasidebarcore.CONFIGS;

import com.closeplanet2.pandaconfigcore.INTERFACE.PandaClass;
import org.apache.maven.model.Build;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;

import java.util.HashMap;

public class ScoreboardLineHolder implements PandaClass {

    @Override
    public String CLASS_ID() {return key.toString();}

    @Override
    public Class<?> CLASS_TYPE() { return ScoreboardLineHolder.class; }

    public static ScoreboardLineHolder CLONE_WITH_NEW_KEY(ScoreboardLineHolder old, Integer newKey){
        var holder = new ScoreboardLineHolder(old.scoreboardTitle, old.lines);
        holder.key = newKey;
        return holder;
    }

    public Integer key = 0;
    public HashMap<Integer, ScoreboardLineData> lines = new HashMap<>();
    public String scoreboardTitle = "";

    public ScoreboardLineHolder(){}
    public ScoreboardLineHolder(String scoreboardTitle, HashMap<Integer, ScoreboardLineData> lines){
        this.lines = lines;
        this.scoreboardTitle = scoreboardTitle;
    }

    public void CreateLine(Scoreboard scoreboard, String scoreboardID){
        var objective = scoreboard.registerNewObjective(scoreboardID, Criteria.DUMMY, scoreboardTitle);
        objective.setDisplayName(scoreboardTitle);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        var teamCount = 0;
        for(var key : lines.keySet()){
            lines.get(key).lineNumber = key;
            lines.get(key).CreateLine(scoreboard, teamCount);
            teamCount++;
        }
    }

    public void UpdateLine(Scoreboard scoreboard){
        scoreboard.getObjective(DisplaySlot.SIDEBAR).setDisplayName(scoreboardTitle);
        for(var key : lines.keySet()) lines.get(key).UpdateLine(scoreboard);
    }

    public static Builder builder(){ return new Builder(); }

    public static class Builder {
        private HashMap<Integer, ScoreboardLineData> lines = new HashMap<>();
        private String scoreboardTitle = "";

        public Builder scoreboardTitle(String scoreboardTitle){
            this.scoreboardTitle = scoreboardTitle;
            return this;
        }

        public Builder addLine(Integer pos, ScoreboardLineData line){
            lines.put(pos, line);
            return this;
        }

        public ScoreboardLineHolder build(){return new ScoreboardLineHolder(scoreboardTitle, lines);}
    }
}
