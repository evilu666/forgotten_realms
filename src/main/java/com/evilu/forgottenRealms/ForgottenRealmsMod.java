package com.evilu.forgottenRealms;

import com.evilu.forgottenRealms.networking.PacketDispatcher;
import com.evilu.forgottenRealms.registry.*;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod(ForgottenRealmsMod.MODID)
public class ForgottenRealmsMod
{
    public static final String MODID = "forgotten_realms";

    public ForgottenRealmsMod() {

        RegistryRegistry.registerRegistries();

        BlockRegistry.registerBlocks();
        SkillRegistry.registerSkills();
        ItemRegistry.registerItems();
        BiomeRegistry.registerBiomes();
        BiomeSourceRegistry.registerBiomeSources();
        StructureFeatureRegistry.registerStructureFeatures();
        ChunkGeneratorRegistry.registerGenerators();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        MinecraftForge.EVENT_BUS.register(this);

        PacketDispatcher.registerHandlers();
    }

    private void setup(final FMLCommonSetupEvent event) {}

    private void clientSetup(final FMLClientSetupEvent event) {
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {
        // Set default curios slot amounts
        event.enqueueWork(() -> InterModComms.sendTo("curios", SlotTypeMessage.REGISTER_TYPE, SlotTypePreset.RING.getMessageBuilder()::build));
    }

    private void processIMC(final InterModProcessEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(final ServerStartingEvent event) {}

}
