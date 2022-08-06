package com.evilu.forgottenRealms.effect;

import com.ldtteam.blockui.Color;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

/**
 * AirWalkEffect
 */
public class AirWalkEffect extends MobEffect {

	protected AirWalkEffect() {
		super(MobEffectCategory.BENEFICIAL, Color.rgbaToInt(0, 0, 0, 0xFF));
	}

  @Override
  public void applyEffectTick(final LivingEntity entity, final int amplifier) {
      final int range = Math.max(1, amplifier + 1);

      if (!entity.isOnGround()) {
          final BlockPos basePos = entity.getOnPos();

          for (int x = -range; x <= range; ++x) {
              for (int z = -range; z <= range; ++z) {
                  final BlockPos pos = basePos.offset(x, -1, z);
                  if (entity.level.getBlockState(pos).isFaceSturdy(entity.level, pos, Direction.UP)) {
                      entity.resetFallDistance();
                      entity.setDeltaMovement(entity.getDeltaMovement().with(Axis.Y, 0));
                  }
              }
          }

      }
  }

    
}
