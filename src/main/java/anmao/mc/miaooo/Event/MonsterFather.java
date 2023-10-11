package anmao.mc.miaooo.Event;

import anmao.mc.miaooo.ApiFcn.GetS;
import anmao.mc.miaooo.ApiFcn.IsS;
import anmao.mc.miaooo.ApiFcn.SetS;
import anmao.mc.miaooo.Config.Configs;
import anmao.mc.miaooo.Dat.Dat_;
import anmao.mc.miaooo.Miaooo;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class MonsterFather {
    @Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
    public static class ForgeEvents{
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event)
        {
            if (event.getEntity() instanceof Monster monster)
            {
                // remove all son
                if (monster.getTags().contains(Dat_.Tags_MonsterFather)) {
                    List<Entity> sons = GetS.GetEntityRadiusEntitiesWithTag(monster, Configs.Config_MonsterSonRadius.get(), Dat_.Tags_MonsterSon);
                    for (Entity son:sons){
                        son.remove(Entity.RemovalReason.DISCARDED);
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onJoinLevel(EntityJoinLevelEvent event){
            //------------------------------------------------
            if (!event.getLevel().isClientSide()) {

                //add father spawn
                if (event.getEntity() instanceof Monster monster) {

                    if (IsS.IsSpawnBigMonster()) {
                        List<Entity> entities = GetS.GetEntityRadiusEntities(monster, Configs.Config_MonsterFatherRadius.get());
                        if (entities.size() > 1) {
                            float health = monster.getMaxHealth();
                            double atk = GetS.GetEntityAttribute(monster, Attributes.ATTACK_DAMAGE);
                            for (Entity i : entities) {
                                if (i instanceof LivingEntity living) {
                                    health = health + living.getMaxHealth();
                                    atk = atk + GetS.GetEntityAttribute(living, Attributes.ATTACK_DAMAGE);
                                    living.addTag(Dat_.Tags_MonsterSon);
                                }
                            }
                            SetS.SetMonsterEffect(monster, MobEffects.DAMAGE_BOOST, 999999, entities.size());
                            SetS.SetMonsterEffect(monster, MobEffects.GLOWING, 999999, entities.size());
                            SetS.SetMonsterMaxHealth(monster, health);
                            SetS.SetMonsterMaxAttackDamage(monster, atk);
                            monster.addTag(Dat_.Tags_MonsterFather);
                            monster.heal(health);
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            //Safe Son
            if (event.getEntity() instanceof Monster monster){
                if (!monster.getTags().contains(Dat_.Tags_MonsterFather)) {
                    if (monster.getTags().contains(Dat_.Tags_MonsterSon)) {
                        List<Entity> fathers = GetS.GetEntityRadiusEntitiesWithTag(monster, Configs.Config_MonsterSonRadius.get(), Dat_.Tags_MonsterFather);
                        if (!fathers.isEmpty()) {
                            float sc = Configs.Config_MonsterSonDamageScale.get().floatValue();
                            if (sc == 0.0f) {
                                event.setCanceled(true);
                            }else {
                                event.setAmount(event.getAmount() * sc);
                            }
                        }
                    }
                }
            }
        }
    }
}
