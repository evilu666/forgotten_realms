package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DoubleHighBlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ForgottenRealmsMod.MODID);

    public static final RegistryObject<Item> FIRE_AMULET = ITEMS.register("fire_amulet", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> WATER_AMULET = ITEMS.register("water_amulet", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));
    public static final RegistryObject<Item> MING_RING = ITEMS.register("mining_ring", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT)));


    public static void registerItems() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}
