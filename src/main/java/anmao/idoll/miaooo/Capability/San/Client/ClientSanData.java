package anmao.idoll.miaooo.Capability.San.Client;

public class ClientSanData {
    private static int playerSan;
    public static void set(int san){
        playerSan = san;
    }
    public static int getSan(){
        return playerSan;
    }
}
