package anmao.idoll.miaooo.Event;

import anmao.idoll.miaooo.Miaooo;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Miaooo_Events {
    @Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
    public static class ForgeEvents{
        /*
        @SubscribeEvent
        public static void onSpawned(LivingSpawnEvent.AllowDespawn event){
            /*
            if(!event.getEntity().getTags().contains("MiaoooDropChance")){
                event.getEntity().addTag("MiaoooDropChance");
                event.getEntity().setDropChance(EquipmentSlot.CHEST,0.0f);
                event.getEntity().setDropChance(EquipmentSlot.FEET,0.0f);
                event.getEntity().setDropChance(EquipmentSlot.HEAD,0.0f);
                event.getEntity().setDropChance(EquipmentSlot.LEGS,0.0f);
                event.getEntity().setDropChance(EquipmentSlot.MAINHAND,0.0f);
                event.getEntity().setDropChance(EquipmentSlot.OFFHAND,0.0f);

            }


        }
        */
        @SubscribeEvent
        public static void onJoinLevel(EntityJoinLevelEvent event){
            if (event.getEntity() instanceof Mob mob){
                if(!mob.getTags().contains("MiaoooDropChance")) {
                    mob.addTag("MiaoooDropChance");
                    mob.setDropChance(EquipmentSlot.CHEST, 0.0f);
                    mob.setDropChance(EquipmentSlot.FEET, 0.0f);
                    mob.setDropChance(EquipmentSlot.HEAD, 0.0f);
                    mob.setDropChance(EquipmentSlot.LEGS, 0.0f);
                    mob.setDropChance(EquipmentSlot.MAINHAND, 0.0f);
                    mob.setDropChance(EquipmentSlot.OFFHAND, 0.0f);
                }
            }
        }
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            // 限制伤害值
            if (event.getEntity() instanceof Mob mob) {
                float damage = 0.2f;
                if (event.getSource() == DamageSource.OUT_OF_WORLD) {
                    damage = 0.5f;
                }
                damage = mob.getMaxHealth() * damage;
                if (event.getAmount() > damage) {
                    event.setAmount(damage);
                    mob.addTag("MiaoooDamageSafe");
                }
            }
        }
        @SubscribeEvent
        public static void onAttack(LivingAttackEvent event) {
            if (event.getEntity().getTags().contains("MiaoooDamageSafe")){
                int _tick = event.getEntity().tickCount - event.getEntity().getLastHurtByMobTimestamp();
                if ( _tick > 0 && _tick < 20){
                    event.setCanceled(true);
                    return;
                }
                event.getEntity().removeTag("MiaoooDamageSafe");
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
