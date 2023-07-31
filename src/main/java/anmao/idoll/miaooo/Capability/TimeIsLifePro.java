package anmao.idoll.miaooo.Capability;

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

public class TimeIsLifePro implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<TimeIsLife> PLAYER_Time = CapabilityManager.get(new CapabilityToken<TimeIsLife>() {
    });
    private TimeIsLife time = null;
    private final LazyOptional<TimeIsLife> optional = LazyOptional.of(this::createTimeIsLife);
    private TimeIsLife createTimeIsLife() {
        if (this.time == null)
        {
            this.time = new TimeIsLife();
        }
        return this.time;
    }
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_Time)
        {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createTimeIsLife().saveNBTData(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createTimeIsLife().loadNBTData(nbt);
    }
}
