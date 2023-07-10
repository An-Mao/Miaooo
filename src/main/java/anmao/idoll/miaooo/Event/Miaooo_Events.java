package anmao.idoll.miaooo.Event;

import anmao.idoll.miaooo.Miaooo;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class Miaooo_Events {
    @Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
    public static class ForgeEvents{
        //
        @SubscribeEvent
        public static void onSpawned(LivingSpawnEvent.AllowDespawn event){
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
        @SubscribeEvent
        public static void onJoinLevel(EntityJoinLevelEvent event){
            /*
            tmpi++;
            System.out.println( "["+tmpi +"]Mob spawn " + event.getEntity().getName());

             */
        }
    }
}
