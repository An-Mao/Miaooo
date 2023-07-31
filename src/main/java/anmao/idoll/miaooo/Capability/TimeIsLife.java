package anmao.idoll.miaooo.Capability;

import net.minecraft.nbt.CompoundTag;

public class TimeIsLife {
    //private final int Time_Min = 200;
    private final int Time_Max = 1000000000;
    private int Time = 0;

    public int getTime() {
        return Time;
    }

    public void addTime(){
        this.Time = Math.min(Time + 1,Time_Max);
    }
    public void resetTime(){
        this.Time = 0;
    }
    public void copyFrom(TimeIsLife source){
        this.Time = 0;
    }
    public void saveNBTData(CompoundTag nbt)
    {

        nbt.putInt("TimeIsLife",0);
    }
    public void loadNBTData(CompoundTag nbt)
    {

        //Time = nbt.getInt("TimeIsLife");
        Time = 0;
    }
}
