package com.evilu.forgottenRealms.networking;

import java.util.HashMap;
import java.util.Map;

import java.lang.reflect.InvocationTargetException;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.networking.packet.Packet;
import com.evilu.forgottenRealms.networking.packet.PacketHandler;
import com.evilu.forgottenRealms.networking.packet.SkillPickedHandler;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

/**
 * PacketHandler
 */
public class PacketDispatcher {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(new ResourceLocation(ForgottenRealmsMod.MODID, "main_channel"), () -> PROTOCOL_VERSION, PacketDispatcher.PROTOCOL_VERSION::equals, PacketDispatcher.PROTOCOL_VERSION::equals);

    @SuppressWarnings("rawtypes")
    private static final Map<Class<? extends PacketHandler>, PacketHandler<?>> handlers = new HashMap<>();

    public static void registerHandlers() {
        int id = 0;
        
        final SkillPickedHandler skillPickedHandler = new SkillPickedHandler();
        skillPickedHandler.register(CHANNEL, id++);
        handlers.put(SkillPickedHandler.class, skillPickedHandler);
    }


    @SuppressWarnings("unchecked")
    public static <P extends Packet, T extends PacketHandler<P>> T getHandler(final Class<T> handlerClass) {
        return (T) handlers.get(handlerClass);
    }

}
