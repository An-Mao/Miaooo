package anmao.idoll.miaooo;

import anmao.idoll.miaooo.Config.Configs;
import anmao.idoll.miaooo.Net.Messages;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Miaooo.MOD_ID)
public class Miaooo
{
    // public static final String MIAOOO_TAGS_DROP = "MiaoooDropChance";
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "miaooo";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();



    //public static final Dat_ DAT = new Dat_();
    public Miaooo()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON,Configs.GENERAL_SPEC,"miaooo.toml");
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }



    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        Messages.register();
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
    }
}
