package anmao.idoll.miaooo.Capability.San.Net;

import anmao.idoll.miaooo.Capability.San.Client.ClientSanData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SanNTC {
    private final int san;
    public SanNTC(int san){
        this.san = san;
    }
    public SanNTC(FriendlyByteBuf buf){
        this.san = buf.readInt();
    }
    public void toBytes(FriendlyByteBuf buf){
        buf.writeInt(san);
    }
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            ClientSanData.set(san);
        });
        return true;
    }
}
