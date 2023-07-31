package anmao.idoll.miaooo.Net.SendToServer;

import anmao.idoll.miaooo.Capability.TimeIsLifePro;
import anmao.idoll.miaooo.Net.Messages;
import anmao.idoll.miaooo.Net.SendToClient.TimeIsLifeC;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TimeIsLifeS {
    private static final String MASSAGE_Reset_Time = "massage.miaooo.reset_time";
    public TimeIsLifeS(){}
    public TimeIsLifeS(FriendlyByteBuf buf){}
    public void toBytes(FriendlyByteBuf buf){}
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            ServerPlayer serverPlayer = context.getSender();
            if (serverPlayer != null) {
                serverPlayer.getCapability(TimeIsLifePro.PLAYER_Time).ifPresent(playerSan -> {
                    playerSan.resetTime();
                    Messages.sendToPlayer(new TimeIsLifeC(playerSan.getTime()),serverPlayer);
                });
            }
        });
        return true;
    }
}
