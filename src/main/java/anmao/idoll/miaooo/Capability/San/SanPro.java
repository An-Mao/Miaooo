package anmao.idoll.miaooo.Capability.San;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SanPro implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<San> PLAYER_SAN = CapabilityManager.get(new CapabilityToken<San>() {
    });
    private San san = null;
    private final LazyOptional<San> optional = LazyOptional.of(this::createPlayerSan);

    private San createPlayerSan() {
        if (this.san == null)
        {
            this.san = new San();
        }
        return this.san;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_SAN)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerSan().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerSan().loadNBTData(nbt);
    }
}
