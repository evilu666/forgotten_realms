package com.evilu.forgottenRealms.registry;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.blockEntity.IndestructibleBlockEntity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * BlockEntityTypeRegistry
 */
public class BlockEntityTypeRegistry {

        private static final DeferredRegister<BlockEntityType<?>> TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ForgottenRealmsMod.MODID);

        public static final RegistryObject<BlockEntityType<IndestructibleBlockEntity>> INDESTRUCTIBLE = TYPES.register("indestructible", () -> BlockEntityType.Builder.of(IndestructibleBlockEntity::new, BlockRegistry.INDESTRUCTIBLE.get()).build(null));

        public static void registerBlockEntityTypes() {
                TYPES.register(FMLJavaModLoadingContext.get().getModEventBus());
        }

}
