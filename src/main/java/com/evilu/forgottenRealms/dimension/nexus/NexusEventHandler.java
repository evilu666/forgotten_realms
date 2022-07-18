package com.evilu.forgottenRealms.dimension.nexus;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.registry.DimensionRegistry;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * NexusEventHandler
 */
@Mod.EventBusSubscriber(modid = ForgottenRealmsMod.MODID, bus = Bus.FORGE)
public class NexusEventHandler {

    @SubscribeEvent
    public static void handleBlockModification(final BlockEvent.BlockToolModificationEvent event) {
        if (isInNexus(event.getWorld())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handleBlockBreaking(final BlockEvent.BreakEvent event) {
        if (isInNexus(event.getWorld())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handlePlacing(final BlockEvent.EntityPlaceEvent event) {
        if (isInNexus(event.getWorld())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handlePlacing(final BlockEvent.EntityMultiPlaceEvent event) {
        if (isInNexus(event.getWorld())) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void handleFarmlandTrampling(final BlockEvent.FarmlandTrampleEvent event) {
        if (isInNexus(event.getWorld())) {
            event.setCanceled(true);
        }
    }

    private static boolean isInNexus(final LevelAccessor levelAccessor) {
        if (levelAccessor instanceof Level) {
            final Level level = (Level) levelAccessor;
            return DimensionRegistry.NEXUS.equals(level.dimension());
        }

        return false;
    }

    
}
