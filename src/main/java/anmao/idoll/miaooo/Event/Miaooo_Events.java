package anmao.idoll.miaooo.Event;

import anmao.idoll.miaooo.Miaooo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Miaooo_Events {
    @Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
    public static class ForgeEvents{
        //
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
        // private static float tmpi;
        @SubscribeEvent
        public static void onJoinLevel(EntityJoinLevelEvent event){
            /*
            tmpi++;
            System.out.println( "["+tmpi +"]Mob spawn " + event.getEntity().getName());
             */
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
        public static void onAttack(LivingAttackEvent event){
            if (event.getEntity() instanceof Mob mob){
                //Entity source = event.getSource().getDirectEntity();
                Entity source = event.getSource().getEntity();
                if (source != null){
                    float AAR = 3.0F;
                    ItemStack item = source.getHandSlots().iterator().next();
                    /*
                    if (source instanceof LivingEntity livingEntity){
                        if(livingEntity.getMainHandItem().getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_SPEED).isEmpty()){
                            System.out.println("Ture");
                            AAR = 16.0F;
                        }
                    }
                     */
                    if (item.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_SPEED).isEmpty()){
                        if (item.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).isEmpty()){
                            AAR = 16.0F;
                        }
                    }
                    if (Math.abs(source.getX() - mob.getX()) >AAR || Math.abs( source.getY() -mob.getY()) >AAR || Math.abs( source.getZ() -mob.getZ()) >AAR){
                        //----------------------------------
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
}
