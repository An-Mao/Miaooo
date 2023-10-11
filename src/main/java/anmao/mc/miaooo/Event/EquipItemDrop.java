package anmao.mc.miaooo.Event;

import anmao.mc.miaooo.Config.C;
import anmao.mc.miaooo.Dat.Dat_;
import anmao.mc.miaooo.Miaooo;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
public class EquipItemDrop {
    private static final EquipmentSlot[] SLOTS = {
            EquipmentSlot.CHEST,
            EquipmentSlot.FEET,
            EquipmentSlot.HEAD,
            EquipmentSlot.LEGS,
            EquipmentSlot.MAINHAND,
            EquipmentSlot.OFFHAND
    };
    @SubscribeEvent
    public static void onJoinLevel(EntityJoinLevelEvent event){
        if (!event.getLevel().isClientSide()) {
            //调整生物装备掉落概率
            //过滤实体类型为Mob
            if (event.getEntity() instanceof Mob mob) {
                //判断是否已设置
                if (!mob.getTags().contains(Dat_.Tags_ChanceDrop)) {
                    if (C.banItemDrop >= 0.0f) {
                        //添加标签
                        mob.addTag(Dat_.Tags_ChanceDrop);
                        //设置掉落概率
                        for (EquipmentSlot slot : SLOTS) {
                            mob.setDropChance(slot, C.banItemDrop);
                        }
                    }
                }
            }
        }
    }
}
