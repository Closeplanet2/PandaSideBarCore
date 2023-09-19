package com.closeplanet2.pandasidebarcore.CONFIGS;

import com.closeplanet2.pandaconfigcore.INTERFACE.PandaConfig;
import com.closeplanet2.pandasidebarcore.PandaSideBarCore;

public class PandaScoreboardSettings implements PandaConfig {
    @Override
    public String CONFIG_ID() {return "PandaScoreboardSettings";}

    @Override
    public Class<?> CLASS_TYPE() {return null;}

    @Override
    public Class<?> MAIN_CLASS() {return PandaSideBarCore.class;}

    public long refreshRate = 1;
}
