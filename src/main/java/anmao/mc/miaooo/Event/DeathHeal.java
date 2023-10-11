package anmao.mc.miaooo.Event;

import anmao.mc.miaooo.ApiFcn.GetS;
import anmao.mc.miaooo.Config.C;
import anmao.mc.miaooo.Miaooo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
public class DeathHeal {

    @SubscribeEvent
    public static void onDeath(LivingDeathEvent event){
        if (event.getEntity() instanceof Monster monster){
            //怪物死亡时治愈同类型生物
            List<Entity> entities = GetS.GetEntityRadiusEntities(monster, C.healRadius);
            if (entities.size() > 1) {
                float heal_health = monster.getMaxHealth() * C.healScale;
                for (Entity i : entities) {
                    if (i instanceof LivingEntity living) {
                        living.heal(heal_health);
                    }
                }
            }
        }
    }
}
