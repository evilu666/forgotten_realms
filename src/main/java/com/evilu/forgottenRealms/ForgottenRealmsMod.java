package com.evilu.forgottenRealms;

import com.evilu.forgottenRealms.registry.BlockRegistry;
import com.evilu.forgottenRealms.registry.ChunkGeneratorRegistry;
import com.evilu.forgottenRealms.registry.ItemRegistry;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ForgottenRealmsMod.MODID)
public class ForgottenRealmsMod
{
    public static final String MODID = "forgotten_realms";

    public ForgottenRealmsMod() {

        BlockRegistry.registerBlocks();
        ItemRegistry.registerItems();
        ChunkGeneratorRegistry.registerGenerators();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {}

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {}

    private void processIMC(final InterModProcessEvent event) { }

    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) {}

}
