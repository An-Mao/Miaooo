package anmao.mc.miaooo;

import anmao.mc.miaooo.Config.Configs;
import com.mojang.logging.LogUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Miaooo.MOD_ID)
public class Miaooo
{
    public static final String MOD_ID = "miaooo";
    private static final Logger LOGGER = LogUtils.getLogger();
    public Miaooo()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        //

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Configs.GENERAL_SPEC,"miaooo.toml");
        MinecraftForge.EVENT_BUS.register(this);
    }
}
