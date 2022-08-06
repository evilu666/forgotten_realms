package com.evilu.forgottenRealms.networking.packet;

import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;

import com.evilu.forgottenRealms.networking.PacketDispatcher;
import com.evilu.forgottenRealms.registry.RegistryRegistry;
import com.evilu.forgottenRealms.skill.Skill;
import com.evilu.forgottenRealms.skill.SkilledItem;
import com.evilu.forgottenRealms.util.GlobalAccessor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.NetworkEvent.Context;
import net.minecraftforge.server.ServerLifecycleHooks;

/**
 * SkillPickedHandler
 */
public class SkillPickedHandler implements PacketHandler<SkillPickedHandler.SkillPickedPacket> {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SkillPickedPacket implements Packet {

        private UUID playerId;
        private Skill pickedSkill;
        private InteractionHand hand;

        @Override
        public void write(final FriendlyByteBuf buffer) {
            buffer.writeUUID(playerId);
            buffer.writeUtf(pickedSkill.getRegistryName().toString());
            buffer.writeEnum(hand);
        }

        @Override
        public void load(final FriendlyByteBuf buffer) {
            playerId = buffer.readUUID();
            final ResourceLocation skillLocation = ResourceLocation.tryParse(buffer.readUtf());
            pickedSkill = RegistryRegistry.SKILL_REGISTRY.get().getValue(skillLocation);
            hand = buffer.readEnum(InteractionHand.class);
        }
    }

    @Override
    public void handle(final SkillPickedPacket packet, final Supplier<Context> contextSupplier) {
        contextSupplier.get().enqueueWork(() -> {
            final Player player = Objects.requireNonNullElseGet(contextSupplier.get().getSender(), () -> GlobalAccessor.getPlayer(packet.playerId));
            final ItemStack stack = player.getItemInHand(packet.hand);
            if (!stack.isEmpty() && stack.getItem() instanceof SkilledItem item) {
                item.tryPickSkill(packet.pickedSkill, stack);
                if (player.getLevel() instanceof ServerLevel) {
                    sendTo(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), packet);
                }
            }
        });

        contextSupplier.get().setPacketHandled(true);
    }

    @Override
    public SkillPickedPacket newPacket() {
        return new SkillPickedPacket();
    }

    @Override
    public Class<SkillPickedPacket> getPacketClass() {
        return SkillPickedPacket.class;
    }

    public void pickSkill(final Player player, final InteractionHand hand, final Skill skill) {
        sendToServer(SkillPickedPacket.builder()
                .playerId(player.getUUID())
                .pickedSkill(skill)
                .hand(hand)
                .build());
    }

}
