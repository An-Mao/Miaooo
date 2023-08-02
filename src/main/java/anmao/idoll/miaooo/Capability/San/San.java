package anmao.idoll.miaooo.Capability.San;

import anmao.idoll.miaooo.Config.Configs;
import net.minecraft.nbt.CompoundTag;

public class San {
    private final int SAN_MIN =  Math.min(Configs.Config_SanMin.get(), 0);
    private final int SAN_MAX = Math.min(Configs.Config_SanMax.get(), 999);
    private int san = SAN_MAX;

    public int getSan() {
        return san;
    }

    public void addSan(int add) {
        if (SAN_MAX == 0){return;}
        this.san = Math.min(san+add,SAN_MAX);
    }

    public void subSan(int sub) {
        if (SAN_MAX == 0){return;}
        this.san = Math.max(san-sub,SAN_MIN);
    }
    public void copyFrom(San source){
        this.san = source.san;
    }
    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putInt("SAN",san);
    }
    public void loadNBTData(CompoundTag nbt)
    {
        san = nbt.getInt("SAN");
    }
}
