package com.evilu.forgottenRealms.server;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.dimension.nexus.NexusDimensionTeleporter;
import com.evilu.forgottenRealms.registry.DimensionRegistry;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * NewPlayerHandler
 */
@Mod.EventBusSubscriber(modid = ForgottenRealmsMod.MODID, bus = Bus.FORGE)
public class NewPlayerHandler {

    private static LevelAccessor world = null;


    @SubscribeEvent
    public void onPlayerLoadsWorld(final WorldEvent.Load event) {
        world = event.getWorld();
    }

    @SubscribeEvent
    public void playerLoggedIn(final PlayerEvent.PlayerLoggedInEvent event) {
        if (event.getPlayer() instanceof ServerPlayer) {
            final ServerPlayer player = (ServerPlayer) event.getPlayer();
            if (world != null && !world.players().contains(player)) {
                final ServerLevelAccessor nexus = player.server.getLevel(DimensionRegistry.NEXUS);
                if (nexus != null) {
                    player.changeDimension(nexus.getLevel(), new NexusDimensionTeleporter());
                }
            }
        }
    }



    
}
