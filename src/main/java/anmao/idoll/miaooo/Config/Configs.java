package anmao.idoll.miaooo.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Configs {
    public static final ForgeConfigSpec GENERAL_SPEC;

    static {
        ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();
        setupConfig(configBuilder);
        GENERAL_SPEC = configBuilder.build();
    }
    public static ForgeConfigSpec.DoubleValue Config_MaxDamageWithHealth;
    public static ForgeConfigSpec.DoubleValue Config_MaxDamageWithHealthOut;
    public static ForgeConfigSpec.IntValue Config_DamageRadius;
    public static ForgeConfigSpec.IntValue Config_DamageRadiusO;
    public static ForgeConfigSpec.IntValue Config_DamageRadiusOffHand;
    public static ForgeConfigSpec.IntValue Config_DamageRadiusOffHandO;
    public static ForgeConfigSpec.DoubleValue Config_MinDamage;
    public static ForgeConfigSpec.DoubleValue Config_BanItemDrop;
    public static ForgeConfigSpec.IntValue Config_MonsterFather;
    public static ForgeConfigSpec.IntValue Config_MonsterFatherRadius;
    public static ForgeConfigSpec.IntValue Config_MonsterSonRadius;
    public static ForgeConfigSpec.DoubleValue Config_MonsterSonDamageScale;


    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        Config_MaxDamageWithHealth = builder.defineInRange("max_damage_with_health", 0.2, 0, 1);
        Config_MaxDamageWithHealthOut = builder.defineInRange("max_damage_with_health_out", 0.5, 0, 1);
        Config_DamageRadius = builder.defineInRange("damage_radius",9,1,9999);
        Config_DamageRadiusO = builder.defineInRange("damage_radius_o",256,1,9999);
        Config_DamageRadiusOffHand = builder.defineInRange("DamageRadiusOffhandItem",1,0,9999);
        Config_DamageRadiusOffHandO = builder.defineInRange("DamageRadiusOffhandItemO",16,0,9999);
        Config_MinDamage = builder.defineInRange("min_damage", 10.0, 0.0, 100000000.0);
        Config_BanItemDrop = builder.defineInRange("BanItemDrop", 0.0, -1.0, 100.0);
        Config_MonsterFather = builder.defineInRange("MonsterFather", 50, 0, 1000);
        Config_MonsterFatherRadius = builder.defineInRange("MonsterFatherRadius", 10, 0, 1000);
        Config_MonsterSonRadius = builder.defineInRange("MonsterSonRadius", 20, 0, 1000);
        Config_MonsterSonDamageScale = builder.defineInRange("MonsterSonDamageScale", 0.0, 0.0, 1.0);
    }
}
