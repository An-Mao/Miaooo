package anmao.mc.miaooo.Config;

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
    protected static ForgeConfigSpec.DoubleValue Config_BanItemDrop;
    public static ForgeConfigSpec.IntValue Config_MonsterFather;
    public static ForgeConfigSpec.IntValue Config_MonsterFatherRadius;
    public static ForgeConfigSpec.IntValue Config_MonsterSonRadius;
    public static ForgeConfigSpec.DoubleValue Config_MonsterSonDamageScale;
    public static ForgeConfigSpec.IntValue Config_HealRadius;
    public static ForgeConfigSpec.DoubleValue Config_HealScale;
    public static ForgeConfigSpec.IntValue Config_SafeTime;
    public static ForgeConfigSpec.DoubleValue Config_TimeDamage;
    public static ForgeConfigSpec.IntValue Config_TimeMin;

    public static ForgeConfigSpec.IntValue Config_SanMax;
    public static ForgeConfigSpec.IntValue Config_SanMin;
    public static ForgeConfigSpec.IntValue Config_SanLow;
    public static ForgeConfigSpec.IntValue Config_SanLowA;
    public static ForgeConfigSpec.IntValue Config_SanLowB;
    public static ForgeConfigSpec.IntValue Config_SanLowC;
    public static ForgeConfigSpec.IntValue Config_SanLowD;


    private static void setupConfig(ForgeConfigSpec.Builder builder) {
        builder.comment("===============================================");
        builder.comment("====================Miaooo=====================");
        builder.comment("==================Ver 1.0.1====================");
        builder.comment("===============================================");

        builder.comment("根据生物血量减免伤害");
        builder.push("Scale Damage With Health");
        Config_MaxDamageWithHealth = builder.comment("Normal（通常）[0 - 1][default:0.2]").defineInRange("MaxDamageWithHealth", 0.2, 0, 1);
        Config_MaxDamageWithHealthOut = builder.comment("Out of world,kill command need this（虚空伤害，kill指令一般为虚空伤害）[0 - 1][default:0.5]").defineInRange("MaxDamageWithHealthOut", 0.5, 0, 1);
        Config_SafeTime = builder.comment("Forced invincibility time when damage reduction is triggered.（触发减伤时的强制无敌时间）[0 - 10000000][default:20][tick]").defineInRange("SafeTimeTick", 20, 0, 10000000);

        builder.comment("触发减伤的最小伤害");
        builder.push("Minimum damage to trigger scale damage");
        Config_MinDamage = builder.comment("Min Damage（最小伤害）[0.0 - 100000000.0][default:10.0]").defineInRange("MinDamage", 10.0, 0.0, 100000000.0);
        builder.pop();
        builder.pop();


        builder.comment("根据距离免疫伤害");
        builder.push("Immunity Damage With Radius");
        Config_DamageRadius = builder.comment("Close Combat（近战）[1 - 999][default:3]").defineInRange("DamageRadius",3,1,999);
        Config_DamageRadiusO = builder.comment("Other（其他）[1 - 999][default:16]").defineInRange("DamageRadiusOther",16,1,999);
        builder.pop();


        builder.comment("根据副手是否含有物品来增加或减少距离判定");
        builder.push("Add Damage Radius Judgment");
        Config_DamageRadiusOffHand = builder.comment("Close Combat（近战距离判定）[-999 - 999][default:1]").defineInRange("DamageRadiusOffhandItem",1,-999,999);
        Config_DamageRadiusOffHandO = builder.comment("Other（其他）[-999 - 999][default:-2]").defineInRange("DamageRadiusOffhandItemO",-2,-999,999);
        builder.pop();


        builder.comment("禁用生物身上的装备掉落");
        builder.push("Ban Mob Item Drop");
        Config_BanItemDrop = builder.comment("Ban Item Drop.(number < 0.0) disable this functionality,(number:0.0) ban drop,(number:other) set drop probability.（值小于0.0.时禁用此项功能，为0.0时禁用掉落，大于0.0时为设置掉落概率）[-1.0 - 1000.0][default:0.0]").defineInRange("BanItemDrop", 0.0, -1.0, 1000.0);
        builder.pop();

        builder.comment("怪物生成增强设置");
        builder.push("Monster Spawner Reinforce");
        Config_MonsterFather = builder.comment("Monster Father Spawn Probability（父体生成概率）[0 - 1000][default:50]").defineInRange("MonsterFather", 50, 0, 1000);
        Config_MonsterFatherRadius = builder.comment("Monster Faster Assimilate Radius（父体吸取范围）[0 - 1000][default:10]").defineInRange("MonsterFatherRadius", 10, 0, 1000);
        Config_MonsterSonRadius = builder.comment("Monster Son Scale Damage Radius（子体减伤范围）[0 - 1000][default:20]").defineInRange("MonsterSonRadius", 20, 0, 1000);
        Config_MonsterSonDamageScale = builder.comment("Monster Son Scale Damage（子体减伤）[0.0 - 1.0][default:0.1]").defineInRange("MonsterSonDamageScale", 0.1, 0.0, 1.0);
        builder.pop();


        builder.comment("怪物死亡治愈");
        builder.push("Monster Death Heal");
        Config_HealRadius = builder.comment("Monster Death Heal Radius（怪物死亡时治愈同类的范围）[0 - 1000][default:6]").defineInRange("HealRadius", 6, 0, 1000);
        Config_HealScale = builder.comment("Monster Death Heal Amount（治愈量）[0.0 - 1.0][default:0.1]").defineInRange("HealScale", 0.1, 0.0, 1.0);
        builder.pop();
    }
}
