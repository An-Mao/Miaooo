package anmao.idoll.miaooo.Item;

import anmao.idoll.miaooo.Item.Custom.DarknessPocketWatch;
import anmao.idoll.miaooo.Miaooo;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MiaoooItems {
    public static final DeferredRegister<Item> Items = DeferredRegister.create(ForgeRegistries.ITEMS, Miaooo.MOD_ID);
    public static final RegistryObject<Item> DARKNESS_POCKET_WATCH = Items.register("darkness_pocket_watch",
            () -> new DarknessPocketWatch(new Item.Properties().tab(TabCreativeMode.CATER_TAB)));


    public static void register(IEventBus eventBus){
        Items.register(eventBus);
    }
}
