package com.example.antidraconicevolutiongrinder;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraftforge.common.MinecraftForge;

@Mod(
        modid = AntiDraconicEvolutionGrinder.MODID,
        version = AntiDraconicEvolutionGrinder.VERSION,
        acceptableRemoteVersions = "*"
)
public class AntiDraconicEvolutionGrinder {
    public static final String MODID = "antidraconicevolutiongrinder";
    public static final String VERSION = "1.0";

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        DeathMessageFilter filter = new DeathMessageFilter();
        MinecraftForge.EVENT_BUS.register(filter);       // for ServerChatEvent etc.
        FMLCommonHandler.instance().bus().register(filter); // for FMLNetworkEvent connection hook
    }
}
