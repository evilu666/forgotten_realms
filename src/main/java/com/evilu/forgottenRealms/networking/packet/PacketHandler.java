package com.evilu.forgottenRealms.networking.packet;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

import com.evilu.forgottenRealms.networking.PacketDispatcher;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * Packet
 */
public interface PacketHandler<P extends Packet> {

    public void handle(final P packet, final Supplier<NetworkEvent.Context> contextSupplier);

    public P newPacket();

    public Class<P> getPacketClass();

    default void register(final SimpleChannel channel, final int id) {
        channel.registerMessage(
            id,
            getPacketClass(),
            P::write,
            buffer -> { 
                final P packet = newPacket();
                packet.load(buffer);
                return packet; 
            },
            this::handle
        );
    }

    default void sendToServer(final P packet) {
        PacketDispatcher.CHANNEL.sendToServer(packet);
    }

    default void sendToPlayer(final ServerPlayer player, final P packet) {
        PacketDispatcher.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player), packet);
    }

    default void sendToChunk(final LevelChunk chunk, final P packet) {
        PacketDispatcher.CHANNEL.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), packet);
    }

    default void sendTo(final PacketTarget target, final P packet) {
        PacketDispatcher.CHANNEL.send(target, packet);
    }
    
}
