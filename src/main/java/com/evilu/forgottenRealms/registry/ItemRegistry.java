package com.evilu.forgottenRealms.registry;

import java.util.Map;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.item.BaseEffectRingItem;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
    public static final RegistryObject<Item> MING_RING = ITEMS.register("mining_ring", () -> new BaseEffectRingItem(new Item.Properties().tab(CreativeModeTab.TAB_COMBAT), Map.of(
        MobEffects.DIG_SPEED, effectInstance -> new MobEffectInstance(effectInstance.getEffect(), effectInstance.getDuration(), 3)
    )));

    public static void registerItems() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }


}
