package anmao.idoll.miaooo.Capability.San.Net;

import anmao.idoll.miaooo.Capability.San.SanPro;
import anmao.idoll.miaooo.Net.Messages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SanNTS {
    private static final String MASSAGE_ADD_SAN = "massage.miaooo.add_san";
    public SanNTS(){}
    public SanNTS(FriendlyByteBuf buf){}
    public void toBytes(FriendlyByteBuf buf){}
    public boolean handle(Supplier<NetworkEvent.Context> supplier){
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(()->{
            ServerPlayer serverPlayer = context.getSender();
            ServerLevel serverLevel = serverPlayer.getLevel();
            if (isChangeSanItemInHand(serverPlayer)){
                //
                serverPlayer.sendSystemMessage(Component.translatable(MASSAGE_ADD_SAN).withStyle(ChatFormatting.BLUE));

                serverLevel.playSound(null,serverPlayer.getOnPos(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS,0.5f,serverLevel.random.nextFloat()*0.1f+0.9f);

                serverPlayer.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                    playerSan.addSan(1);
                    //serverPlayer.sendSystemMessage(Component.literal("now san:"+playerSan.getSan()).withStyle(ChatFormatting.AQUA));
                    Messages.sendToPlayer(new SanNTC(playerSan.getSan()),serverPlayer);
                });
            }else {
                serverPlayer.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                    //serverPlayer.sendSystemMessage(Component.literal("now san:"+playerSan.getSan()).withStyle(ChatFormatting.AQUA));
                    Messages.sendToPlayer(new SanNTC(playerSan.getSan()),serverPlayer);
                });

            }
        });
        return true;
    }

    private boolean isChangeSanItemInHand(ServerPlayer serverPlayer) {
        //
        //return serverPlayer.getMainHandItem().getItem() == ModItems.CORE_CHAOS.get();
        return false;
    }
}
