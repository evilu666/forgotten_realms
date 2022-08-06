package com.evilu.forgottenRealms.util;

import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * GlobalAccessor
 */
public class GlobalAccessor {

    public static Player getPlayer(final UUID id) {
        return DistExecutor.unsafeRunForDist(
            () -> () -> Minecraft.getInstance().level.getPlayerByUUID(id),
            () -> () -> ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayer(id)
        );
    }
    
}
