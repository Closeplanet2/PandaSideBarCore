package com.closeplanet2.pandasidebarcore.CONFIGS;

import com.closeplanet2.pandaconfigcore.INTERFACE.PandaClass;
import org.bukkit.scoreboard.*;

public class ScoreboardLineData implements PandaClass {
    @Override
    public String CLASS_ID() { return lineNumber.toString(); }

    @Override
    public Class<?> CLASS_TYPE() {return ScoreboardLineData.class;}

    public Integer lineNumber = 0;
    public String teamName = "";
    public String text = "";

    public ScoreboardLineData(){}
    public ScoreboardLineData(String teamName, String text){
        this.teamName = teamName;
        this.text = text;
    }

    public void CreateLine(Scoreboard scoreboard, int teamCount){
        var team = scoreboard.registerNewTeam(teamName);
        StringBuilder teamText = new StringBuilder();
        teamText.append("§r".repeat(Math.max(0, teamCount)));
        team.addEntry(teamText.toString());
        team.setPrefix(text);
        scoreboard.getObjective(DisplaySlot.SIDEBAR).getScore(teamText.toString()).setScore(lineNumber);
    }

    public void UpdateLine(Scoreboard scoreboard){
        var team = scoreboard.getTeam(teamName);
        if(team == null) return;
        team.setPrefix(text);
    }
}
