package com.closeplanet2.pandasidebarcore.LOOPS;

import com.closeplanet2.pandasidebarcore.CONFIGS.PandaScoreboard;
import com.closeplanet2.pandasidebarcore.PandaSideBarCore;
import com.closeplanet2.pandaspigotcore.LOOPS.LoopValues;
import com.closeplanet2.pandaspigotcore.LOOPS.PandaLoop;
import com.closeplanet2.pandaspigotcore.PandaSpigotCore;
import org.bukkit.Bukkit;

@PandaLoop
public class PandaScoreboardLoop implements LoopValues {
    @Override
    public String ReturnID() { return "PandaScoreboardLoop"; }

    @Override
    public int RegisterLoop() {
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(PandaSpigotCore.INSTANCE, new Runnable() {
            @Override
            public void run() {
                for(var scoreboardID : PandaSideBarCore.pandaScoreboards.keySet())
                    PandaSideBarCore.pandaScoreboards.get(scoreboardID).UpdateScoreboard();
            }
        }, 0L, PandaSideBarCore.pandaScoreboardSettings.refreshRate);
    }
}
