package anmao.idoll.miaooo.Net;

import anmao.idoll.miaooo.Miaooo;
import anmao.idoll.miaooo.Net.SendToClient.TimeIsLifeC;
import anmao.idoll.miaooo.Net.SendToServer.TimeIsLifeS;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class Messages {
    private static SimpleChannel INSTANCE;
    private static int packetId = 0;
    private static int id(){
        return packetId++;
    }
    public static void register(){
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(Miaooo.MOD_ID,"messages")).networkProtocolVersion(()->"1.0").serverAcceptedVersions(s -> true).clientAcceptedVersions(s -> true).simpleChannel();
        INSTANCE = net;
        net.messageBuilder(TimeIsLifeS.class,id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TimeIsLifeS::new)
                .encoder(TimeIsLifeS::toBytes)
                .consumerMainThread(TimeIsLifeS::handle).add();
        net.messageBuilder(TimeIsLifeC.class,id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TimeIsLifeC::new)
                .encoder(TimeIsLifeC::toBytes)
                .consumerMainThread(TimeIsLifeC::handle).add();
    }
    public static <MSG> void sendToServer(MSG msg){
        INSTANCE.sendToServer(msg);
    }
    public static <MSG> void sendToPlayer(MSG msg, ServerPlayer serverPlayer){
        INSTANCE.send(PacketDistributor.PLAYER.with(()->serverPlayer), msg);
    }
}
