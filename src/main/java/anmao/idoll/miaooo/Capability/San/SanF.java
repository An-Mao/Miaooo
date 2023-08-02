package anmao.idoll.miaooo.Capability.San;

import anmao.idoll.miaooo.Config.Configs;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;

public class SanF {
    public static boolean SanDeath(Player player, int san){
        if (san <=Configs.Config_SanLowD.get() ){
            player.hurt(DamageSource.OUT_OF_WORLD,player.getMaxHealth());
            return false;
        }
        if (san <= Configs.Config_SanLow.get()){
            int lvl = 1;
            if (san <= Configs.Config_SanLowA.get()){
                lvl ++;
                if (san <= Configs.Config_SanLowB.get()){
                    lvl++;
                    if (san <= Configs.Config_SanLowC.get()){
                        lvl++;
                    }
                }
            }
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,200,lvl));
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING,200,lvl));
            player.addEffect(new MobEffectInstance(MobEffects.UNLUCK,200,lvl));
            //player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,200,lvl));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP,200,lvl));
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER,200,lvl));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION,200,lvl));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED,200,lvl));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,200,lvl));
        }
        return true;
    }
}
