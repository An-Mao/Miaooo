package anmao.mc.miaooo.Config;

public class C extends Configs{
    public static float banItemDrop = Config_BanItemDrop.get().floatValue();

    public static int healRadius = Config_HealRadius.get();
    public static float healScale = Config_HealScale.get().floatValue();


    public static float maxDamageWithHealthOut = Config_MaxDamageWithHealthOut.get().floatValue();
    public static float maxDamageWithHealth= Config_MaxDamageWithHealth.get().floatValue();


    public static int safeTime = Config_SafeTime.get();
    public static int attackRadiusOther = Config_DamageRadiusO.get();
    public static int attackRadiusOffHandOther = Config_DamageRadiusOffHandO.get();
    public static int attackRadius = Config_DamageRadius.get();
    public static int attackRadiusOffHand = Config_DamageRadiusOffHand.get();
}
