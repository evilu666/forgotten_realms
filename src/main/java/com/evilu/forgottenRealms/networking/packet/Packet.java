package com.evilu.forgottenRealms.networking.packet;

import net.minecraft.network.FriendlyByteBuf;

/**
 * Packet
 */
public interface Packet {

    public void write(final FriendlyByteBuf buffer);

    public void load(final FriendlyByteBuf buffer);
}
