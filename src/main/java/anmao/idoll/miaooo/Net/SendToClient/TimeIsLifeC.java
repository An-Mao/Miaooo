package anmao.idoll.miaooo.Net.SendToClient;

import anmao.idoll.miaooo.Client.CDTimeIsLife;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TimeIsLifeC {
    private final int time;
    public TimeIsLifeC(int san){
        this.time = san;
    }
    public TimeIsLifeC(FriendlyByteBuf buf){
        this.time = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(time);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            CDTimeIsLife.set(time);
        });
        return true;
    }
}
