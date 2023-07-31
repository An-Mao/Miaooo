package anmao.idoll.miaooo.Event;

import anmao.idoll.miaooo.Config.Configs;
import anmao.idoll.miaooo.Miaooo;
import anmao.idoll.miaooo.Net.Messages;
import anmao.idoll.miaooo.Net.SendToServer.TimeIsLifeS;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class Miaooo_CEvent {
    @Mod.EventBusSubscriber(modid = Miaooo.MOD_ID,value = Dist.CLIENT)
    public static class ClientForgeEvents{
        @SubscribeEvent
        public static void onKeyInput(InputEvent event){
            //Messages.sendToServer(new TimeIsLifeS());
            if (Minecraft.getInstance().player != null) {
                if(Minecraft.getInstance().player.isAlive()){
                    if (Configs.Config_TimeMin.get() != 0.0d) {
                        Messages.sendToServer(new TimeIsLifeS());
                    }
                }
            }
        }
    }
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = Miaooo.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
        }
    }
}
