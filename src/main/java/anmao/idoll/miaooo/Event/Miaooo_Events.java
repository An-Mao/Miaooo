package anmao.idoll.miaooo.Event;

import anmao.idoll.miaooo.ApiFcn.GetS;
import anmao.idoll.miaooo.ApiFcn.IsS;
import anmao.idoll.miaooo.ApiFcn.SetS;
import anmao.idoll.miaooo.Dat.Dat_;
import anmao.idoll.miaooo.Miaooo;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

public class Miaooo_Events {
    @Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
    public static class ForgeEvents{

        @SubscribeEvent
        public static void onSpawned(LivingSpawnEvent.AllowDespawn event){
            //
        }
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event)
        {
            if (event.getEntity() instanceof Monster monster)
            {
                // remove all son
                if (monster.getTags().contains(Dat_.Tags_MonsterFather)) {
                    List<Entity> sons = GetS.GetEntityRadiusEntitiesWithTag(monster, 20, Dat_.Tags_MonsterSon);
                    for (Entity son:sons){
                        son.remove(Entity.RemovalReason.DISCARDED);
                    }
                }

                //heal monster
                List<Entity> entities = GetS.GetEntityRadiusEntities(monster, 6);
                if (entities.size() > 1) {
                    float heal_health = monster.getMaxHealth() * 0.1f;
                    for (Entity i : entities) {
                        if (i instanceof LivingEntity living) {
                            living.heal(heal_health);
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onJoinLevel(EntityJoinLevelEvent event){
            if (event.getEntity() instanceof Mob mob){
                if(!mob.getTags().contains(Dat_.Tags_ChanceDrop)) {
                    mob.addTag(Dat_.Tags_ChanceDrop);
                    mob.setDropChance(EquipmentSlot.CHEST, 0.0f);
                    mob.setDropChance(EquipmentSlot.FEET, 0.0f);
                    mob.setDropChance(EquipmentSlot.HEAD, 0.0f);
                    mob.setDropChance(EquipmentSlot.LEGS, 0.0f);
                    mob.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
                    mob.setDropChance(EquipmentSlot.OFFHAND, 0.0f);
                }
            }

            //add father spawn
            if (event.getEntity() instanceof Monster monster){
                if (IsS.IsSpawnBigMonster(100)) {
                    List<Entity> entities = GetS.GetEntityRadiusEntities(monster, 10);
                    if (entities.size() > 1) {
                        float health = monster.getMaxHealth();
                        double atk = GetS.GetEntityAttribute(monster,Attributes.ATTACK_DAMAGE);
                        for (Entity i : entities) {
                            if (i instanceof LivingEntity living) {
                                health = health + living.getMaxHealth();
                                atk = atk + GetS.GetEntityAttribute(living, Attributes.ATTACK_DAMAGE);
                                living.addTag(Dat_.Tags_MonsterSon);
                            }
                        }
                        SetS.SetMonsterEffect(monster,MobEffects.DAMAGE_BOOST,999999,entities.size());
                        SetS.SetMonsterEffect(monster,MobEffects.GLOWING,999999,entities.size());
                        SetS.SetMonsterMaxHealth(monster,health);
                        SetS.SetMonsterMaxAttackDamage(monster,atk);
                        monster.addTag(Dat_.Tags_MonsterFather);
                        monster.heal(health);
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            //Safe Son
            if (event.getEntity() instanceof Monster monster){
                if (monster.getTags().contains(Dat_.Tags_MonsterSon)){
                    List<Entity> fathers = GetS.GetEntityRadiusEntitiesWithTag(monster,20,Dat_.Tags_MonsterFather);
                    if (fathers.size()>0) {
                        event.setCanceled(true);
                        return;
                    }
                }
            }
            //
            if (event.getEntity() instanceof Mob mob) {
                //
                float damage = 0.2f;
                if (event.getSource() == DamageSource.OUT_OF_WORLD) {
                    damage = 0.5f;
                }
                damage = mob.getMaxHealth() * damage;
                if (event.getAmount() > damage) {
                    event.setAmount(damage);
                    mob.addTag(Dat_.Tags_DamageSafe);
                }
            }

        }
        @SubscribeEvent
        public static void onAttack(LivingAttackEvent event) {
            if (event.getEntity().getTags().contains(Dat_.Tags_DamageSafe)){
                int _tick = event.getEntity().tickCount - event.getEntity().getLastHurtByMobTimestamp();
                if ( _tick > 0 && _tick < 20){
                    event.setCanceled(true);
                    return;
                }
                event.getEntity().removeTag(Dat_.Tags_DamageSafe);
            }
            if (event.getEntity() instanceof Mob mob) {
                Entity source = event.getSource().getEntity();
                if (source != null) {
                    float AAR = 9.0F;
                    if (source != event.getSource().getDirectEntity()) {
                        AAR = 256.0F;
                    }
                    double x = source.getX() - mob.getX();
                    double y = source.getY() - mob.getY();
                    double z = source.getZ() - mob.getZ();
                    if (x * x + y * y + z * z > AAR) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
