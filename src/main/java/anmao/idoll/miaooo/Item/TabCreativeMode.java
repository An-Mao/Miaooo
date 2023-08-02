package anmao.idoll.miaooo.Item;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabCreativeMode {
    public static final CreativeModeTab CATER_TAB = new CreativeModeTab("miaooo") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(MiaoooItems.DARKNESS_POCKET_WATCH.get());
        }
    };
}
