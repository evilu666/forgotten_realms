package com.evilu.forgottenRealms.capability;

import com.evilu.forgottenRealms.model.Reference;
import com.mojang.serialization.Codec;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.minecraft.core.Direction;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

/**
 * AbstractDataCapability
 */
@RequiredArgsConstructor
public abstract class AbstractDataCapability<D, C> {

    @Getter
    @Setter(AccessLevel.PRIVATE)
    private D data = createEmpty();

    private final Codec<D> codec;

    protected abstract D createEmpty();

    @Getter
    private final Reference<D> dataReference = Reference.fromSupplier(this::getData);

    public void read(final Tag tag) {
        codec.parse(NbtOps.INSTANCE, tag).result().ifPresent(this::setData);
    }

    public Tag write() {
        return codec.encodeStart(NbtOps.INSTANCE, data).result().orElseThrow();
    }

    public static <T extends AbstractDataCapability<?, C>, C> ICapabilitySerializable<Tag> createCapabilityProvider(final Capability<C> capability, final T instance) {
        return new ICapabilitySerializable<>() {

            @Override
            public <R> LazyOptional<R> getCapability(final Capability<R> cap, final Direction side) {
                if (cap == capability) {
                  return LazyOptional.of(() -> instance).cast();
                }

                return LazyOptional.empty();
            }

            @Override
            public Tag serializeNBT() {
                return instance.write();
            }

            @Override
            public void deserializeNBT(final Tag nbt) {
                instance.read(nbt);
            }

        };
    }

}
