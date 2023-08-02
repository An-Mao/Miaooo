package anmao.idoll.miaooo.Item.Custom;

import anmao.idoll.miaooo.Capability.San.Net.SanNTC;
import anmao.idoll.miaooo.Capability.San.SanF;
import anmao.idoll.miaooo.Capability.San.SanPro;
import anmao.idoll.miaooo.Config.Configs;
import anmao.idoll.miaooo.Net.Messages;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DarknessPocketWatch extends Item {
    public DarknessPocketWatch(Properties p_41383_) {
        super(p_41383_);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            player.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                if (Configs.Config_SanMax.get() == 0){
                    serverPlayer.sendSystemMessage(Component.translatable("massage.miaooo.disable_san").withStyle(ChatFormatting.RED));
                    return;
                }
                if (SanF.SanDeath(player, playerSan.getSan())) {
                    if ( interactionHand == InteractionHand.MAIN_HAND){
                        playerSan.addSan(10);
                        //System.out.println("add san");
                    }else {
                        playerSan.subSan(10);
                        //System.out.println("sub san");
                    }
                    //level.playSound(null,serverPlayer.getOnPos(), SoundEvents.PLAYER_LEVELUP, SoundSource.PLAYERS,0.5f,level.random.nextFloat()*0.1f+0.9f);
                    Messages.sendToPlayer(new SanNTC(playerSan.getSan()), serverPlayer);
                }
            });
        }
        return super.use(level, player, interactionHand);
    }
}
