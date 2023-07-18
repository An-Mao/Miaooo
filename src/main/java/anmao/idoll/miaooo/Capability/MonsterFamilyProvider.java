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

public class MonsterFamilyProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<MonsterFamily> MONSTER_FAMILY = CapabilityManager.get(new CapabilityToken<MonsterFamily>() {
    });
    private MonsterFamily monsterFamily = null;
    private final LazyOptional<MonsterFamily> optional = LazyOptional.of(this::createMonsterFamily);

    private MonsterFamily createMonsterFamily() {
        if (this.monsterFamily == null){
            this.monsterFamily = new MonsterFamily();
        }
        return this.monsterFamily;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == MONSTER_FAMILY){
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

    }
}
