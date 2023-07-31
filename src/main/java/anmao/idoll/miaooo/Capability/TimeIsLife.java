package anmao.idoll.miaooo.Capability;

import net.minecraft.nbt.CompoundTag;

public class TimeIsLife {
    //private final int Time_Min = 200;
    //private final int Time_Max = 400;
    private int Time = 0;

    public int getTime() {
        return Time;
    }

    public void addTime(){
        this.Time++;
    }
    public void resetTime(){
        this.Time = 0;
    }
    public void copyFrom(TimeIsLife source){
        this.Time = source.Time;
    }
    public void saveNBTData(CompoundTag nbt)
    {
        nbt.putInt("TimeIsLife",Time);
    }
    public void loadNBTData(CompoundTag nbt)
    {
        Time = nbt.getInt("TimeIsLife");
    }
}
