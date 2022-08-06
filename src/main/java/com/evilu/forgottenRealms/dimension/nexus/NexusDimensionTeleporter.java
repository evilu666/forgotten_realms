package com.evilu.forgottenRealms.dimension.nexus;

import java.util.function.Function;

import com.evilu.forgottenRealms.registry.DimensionRegistry;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.ITeleporter;

/**
 * NexusDimensionTeleporter
 */
public class NexusDimensionTeleporter implements ITeleporter {

    public BlockPos SPAWN_POS = new BlockPos(0, 64, 0);

    @Override
    public Entity placeEntity(final Entity entity, final ServerLevel currentWorld, final ServerLevel destWorld, final float yaw, final Function<Boolean, Entity> repositionEntity) {
        final Entity newEnt = repositionEntity.apply(false);
        newEnt.setPos(SPAWN_POS.getX(), SPAWN_POS.getY(), SPAWN_POS.getZ());
        if (newEnt instanceof Player) {
            final Player player = (Player) newEnt;
            player.setSleepingPos(SPAWN_POS.offset(0, 1, 0));
        }
        return newEnt;
    }

    @Override
    public boolean playTeleportSound(final ServerPlayer player, ServerLevel sourceWorld, ServerLevel destWorld) {
        player.setSleepingPos(player.getOnPos().offset(0, 1, 0));
        player.setRespawnPosition(DimensionRegistry.NEXUS, SPAWN_POS, 0, false, true);

        return ITeleporter.super.playTeleportSound(player, sourceWorld, destWorld);
    }

    
}
