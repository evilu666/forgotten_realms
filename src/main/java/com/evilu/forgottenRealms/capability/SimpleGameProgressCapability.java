package com.evilu.forgottenRealms.capability;

import com.evilu.forgottenRealms.ForgottenRealmsMod;
import com.evilu.forgottenRealms.model.GameProgressData;
import com.evilu.forgottenRealms.registry.CapabilityRegistry;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.core.Direction;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.NbtOps;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * PlayerGameProgressCapability
 */
@Mod.EventBusSubscriber(modid = ForgottenRealmsMod.MODID, bus = Bus.FORGE)
@RequiredArgsConstructor
public class SimpleGameProgressCapability implements GameProgressCapability {

  @Getter
  private GameProgressData data = GameProgressData.createEmpty();

  public static ICapabilityProvider createProvider() {
    final SimpleGameProgressCapability cap = new SimpleGameProgressCapability();

    return new ICapabilitySerializable<Tag>() {
      @Override
      public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        final Capability<GameProgressCapability> effectCap = CapabilityManager.get(new CapabilityToken<>() {});
        if (cap == effectCap) {
          return LazyOptional.of(() -> cap).cast();
        }

        return LazyOptional.empty();
      }

      @Override
      public Tag serializeNBT() {
        return GameProgressData.CODEC.encodeStart(NbtOps.INSTANCE, cap.data).result().orElseThrow();
      }

      @Override
      public void deserializeNBT(final Tag nbt) {
        GameProgressData.CODEC.parse(NbtOps.INSTANCE, nbt)
          .result()
          .orElseGet(GameProgressData::createEmpty);
      }

    };
  }

		@SubscribeEvent
		public static void attachLivingEntityCaps(final AttachCapabilitiesEvent<Entity> event) {
			if (event.getObject() instanceof Player) {
				event.addCapability(CapabilityRegistry.GAME_PROGRESS_LOCATION, SimpleGameProgressCapability.createProvider());
			}
		}

}
