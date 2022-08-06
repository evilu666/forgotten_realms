package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.block.DimensionPortalBlock;
import com.evilu.forgottenRealms.block.IndestructibleBlock;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class BlockRegistry {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ForgottenRealmsMod.MODID);

    public static final RegistryObject<Block> OVERWORLD_PORTAL = BLOCKS.register("overworld_portal", () -> new DimensionPortalBlock(MaterialColor.COLOR_BROWN, Level.OVERWORLD));
    public static final RegistryObject<Block> NETHER_PORTAL = BLOCKS.register("nether_portal", () -> new DimensionPortalBlock(MaterialColor.COLOR_RED, Level.NETHER));
    public static final RegistryObject<Block> END_PORTAL = BLOCKS.register("end_portal", () -> new DimensionPortalBlock(MaterialColor.COLOR_BLACK, Level.END));

    public static final RegistryObject<Block> INDESTRUCTIBLE = BLOCKS.register("indestructible", () -> new IndestructibleBlock());

    public static void registerBlocks() {
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
