package anmao.idoll.miaooo.Net.SendToServer;

import anmao.idoll.miaooo.Capability.TimeIsLifePro;
import anmao.idoll.miaooo.Net.Messages;
import anmao.idoll.miaooo.Net.SendToClient.TimeIsLifeC;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TimeIsLifeS {
    private static final String MASSAGE_ADD_SAN = "massage.miaooo.reset_time";
    public TimeIsLifeS(){}
    public TimeIsLifeS(FriendlyByteBuf buf){}
    public void toBytes(FriendlyByteBuf buf){}
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            ServerPlayer serverPlayer = context.getSender();
            //ServerLevel serverLevel = serverPlayer.getLevel();
                //
            serverPlayer.sendSystemMessage(Component.translatable(MASSAGE_ADD_SAN).withStyle(ChatFormatting.BLUE));

            //serverLevel.playSound(null,serverPlayer.getOnPos(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS,0.5f,serverLevel.random.nextFloat()*0.1f+0.9f);

            serverPlayer.getCapability(TimeIsLifePro.PLAYER_Time).ifPresent(playerSan -> {
                playerSan.resetTime();
                serverPlayer.sendSystemMessage(Component.literal("reset time:"+playerSan.getTime()).withStyle(ChatFormatting.AQUA));

                Messages.sendToPlayer(new TimeIsLifeC(playerSan.getTime()),serverPlayer);
            });
        });
        return true;
    }
}
