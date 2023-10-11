package anmao.mc.miaooo.Event;

import anmao.mc.miaooo.Config.C;
import anmao.mc.miaooo.Config.Configs;
import anmao.mc.miaooo.Dat.Dat_;
import anmao.mc.miaooo.Miaooo;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
public class DamageScale {
    @SubscribeEvent
    public static void onHurt(LivingHurtEvent event){
        if (event.getEntity() instanceof Mob mob) {
            //
            if (event.getAmount() > Configs.Config_MinDamage.get() && mob.getMaxHealth() > Configs.Config_MinDamage.get()) {
                float damage = C.maxDamageWithHealth;
                if (event.getSource() == event.getSource().getEntity().damageSources().fellOutOfWorld()) {
                    damage = C.maxDamageWithHealthOut;
                }
                damage = mob.getMaxHealth() * damage;
                if (event.getAmount() > damage) {
                    event.setAmount(damage);
                    mob.addTag(Dat_.Tags_DamageSafe);
                }
            }
        }
    }
}
