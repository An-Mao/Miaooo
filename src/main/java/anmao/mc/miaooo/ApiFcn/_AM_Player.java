package anmao.mc.miaooo.ApiFcn;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class _AM_Player extends _AM_Core {
    private final ServerPlayer serverPlayer;
    public _AM_Player(ServerPlayer sP){
        serverPlayer = sP;
    }
    public _AM_Player(LivingEntity livingEntity){
        if (livingEntity instanceof ServerPlayer sP) {
            serverPlayer = sP;
        }else {
            serverPlayer = null;
        }
    }
    public _AM_Player(Player player){
        if (player instanceof ServerPlayer sP) {
            serverPlayer = sP;
        }else {
            serverPlayer = null;
        }
    }
    public float nowHealth(ServerPlayer serverPlayer){
        return serverPlayer.getHealth();
    }
    public float maxHealth(ServerPlayer serverPlayer){
        return serverPlayer.getHealth();
    }
}
