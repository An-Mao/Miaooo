package anmao.idoll.miaooo.Event;

import anmao.idoll.miaooo.ApiFcn.GetS;
import anmao.idoll.miaooo.ApiFcn.IsS;
import anmao.idoll.miaooo.ApiFcn.SetS;
import anmao.idoll.miaooo.Config.Configs;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
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
                    List<Entity> sons = GetS.GetEntityRadiusEntitiesWithTag(monster, Configs.Config_MonsterSonRadius.get(), Dat_.Tags_MonsterSon);
                    for (Entity son:sons){
                        son.remove(Entity.RemovalReason.DISCARDED);
                    }
                }

                //heal monster
                List<Entity> entities = GetS.GetEntityRadiusEntities(monster, Configs.Config_HealRadius.get());
                if (entities.size() > 1) {
                    float heal_health = (float) (monster.getMaxHealth() * Configs.Config_HealScale.get());
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
            //------------------------------------------------


            //------------------------------------------------
            if (event.getEntity() instanceof Mob mob){
                if(!mob.getTags().contains(Dat_.Tags_ChanceDrop)) {
                    float a = Configs.Config_BanItemDrop.get().floatValue();
                    if( a >= 0.0f) {
                        mob.addTag(Dat_.Tags_ChanceDrop);
                        mob.setDropChance(EquipmentSlot.CHEST, a);
                        mob.setDropChance(EquipmentSlot.FEET, a);
                        mob.setDropChance(EquipmentSlot.HEAD, a);
                        mob.setDropChance(EquipmentSlot.LEGS, a);
                        mob.setDropChance(EquipmentSlot.MAINHAND, a);
                        mob.setDropChance(EquipmentSlot.OFFHAND, a);
                    }
                }
            }

            //add father spawn
            if (event.getEntity() instanceof Monster monster){

                if (IsS.IsSpawnBigMonster()) {
                    List<Entity> entities = GetS.GetEntityRadiusEntities(monster, Configs.Config_MonsterFatherRadius.get());
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
                if (!monster.getTags().contains(Dat_.Tags_MonsterFather)) {
                    if (monster.getTags().contains(Dat_.Tags_MonsterSon)) {
                        List<Entity> fathers = GetS.GetEntityRadiusEntitiesWithTag(monster, Configs.Config_MonsterSonRadius.get(), Dat_.Tags_MonsterFather);
                        if (!fathers.isEmpty()) {
                            event.setAmount((float) (event.getAmount() * Configs.Config_MonsterSonDamageScale.get()));
                            //event.setCanceled(true);
                            return;
                        }
                    }
                }
            }
            //
            if (event.getEntity() instanceof Mob mob) {
                //
                if (event.getAmount() > Configs.Config_MinDamage.get()) {
                    double damage = Configs.Config_MaxDamageWithHealth.get();
                    if (event.getSource() == DamageSource.OUT_OF_WORLD) {
                        damage = Configs.Config_MaxDamageWithHealth.get();
                    }
                    damage = mob.getMaxHealth() * damage;
                    if (event.getAmount() > damage) {
                        event.setAmount((float) damage);
                        mob.addTag(Dat_.Tags_DamageSafe);
                    }
                }
            }

        }
        @SubscribeEvent
        public static void onAttack(LivingAttackEvent event) {
            //monster safe
            if (event.getEntity() instanceof Mob mob) {
                //monster safe
                if (mob.getTags().contains(Dat_.Tags_DamageSafe)){
                    int _tick = mob.tickCount - mob.getLastHurtByMobTimestamp();
                    if ( _tick > 0 && _tick < Configs.Config_SafeTime.get()){
                        event.setCanceled(true);
                        return;
                    }
                    mob.removeTag(Dat_.Tags_DamageSafe);
                }
                //atk radius
                Entity source = event.getSource().getEntity();
                if (source != null) {
                    Iterator<ItemStack> items = event.getSource().getEntity().getHandSlots().iterator();
                    ItemStack oitem = items.next();
                    if (items.hasNext()){
                        oitem = items.next();
                    }
                    //System.out.println(oitem);
                    int AAR;
                    if (source != event.getSource().getDirectEntity()) {
                        AAR = Configs.Config_DamageRadiusO.get();
                        if (oitem.getItem() == Items.AIR) {
                            AAR += Configs.Config_DamageRadiusOffHandO.get();
                        }
                    }else {
                        AAR = Configs.Config_DamageRadius.get();
                        if (!oitem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_SPEED).isEmpty()){
                            if (!oitem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).isEmpty()){
                                AAR += Configs.Config_DamageRadiusOffHand.get();
                            }
                        }
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
