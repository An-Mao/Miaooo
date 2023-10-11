package anmao.mc.miaooo.Event;

import anmao.mc.miaooo.Config.C;
import anmao.mc.miaooo.Dat.Dat_;
import anmao.mc.miaooo.Miaooo;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
@Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
public class AttackRadius {

    @SubscribeEvent
    public static void onAttack(LivingAttackEvent event) {
        //判断实体类型
        if (event.getEntity() instanceof Mob mob) {
            //判断免伤标签
            if (mob.getTags().contains(Dat_.Tags_DamageSafe)){
                int _tick = mob.tickCount - mob.getLastHurtByMobTimestamp();
                if ( _tick > 0 && _tick < C.safeTime){
                    event.setCanceled(true);
                    return;
                }
                mob.removeTag(Dat_.Tags_DamageSafe);
            }
            //攻击范围
            Entity source = event.getSource().getEntity();
            if (source != null) {
                Iterator<ItemStack> items = event.getSource().getEntity().getHandSlots().iterator();
                ItemStack oitem = null;
                if (items.hasNext()){
                    oitem = items.next();
                    if (items.hasNext()){
                        oitem = items.next();
                    }else {
                        oitem = null;
                    }
                }
                //System.out.println(oitem);
                int AAR;
                if (source != event.getSource().getDirectEntity()) {
                    AAR = C.attackRadiusOther;
                    if (oitem != null && oitem.getItem() == Items.AIR) {
                        AAR += C.attackRadiusOffHandOther;
                    }
                }else {
                    AAR = C.attackRadius;
                    if (oitem != null && !oitem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_SPEED).isEmpty()) {
                        if (!oitem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).isEmpty()) {
                            AAR += C.attackRadiusOffHand;
                        }
                    }
                }
                double x = source.getX() - mob.getX();
                double y = source.getY() - mob.getY();
                double z = source.getZ() - mob.getZ();
                if ( x < -AAR || x > AAR || y < -AAR || y > AAR || z < -AAR || z > AAR){
                    event.setCanceled(true);
                }
            }
        }
    }
}
