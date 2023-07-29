package anmao.idoll.miaooo.ApiFcn;

import anmao.idoll.miaooo.Config.Configs;

public class IsS {
    public static boolean IsSpawnBigMonster(){
        int rn = GetS.GetRandomNumber(0,1000);
        return  rn <= Configs.Config_MonsterFather.get();
    }

    public static boolean IsSpawnSkillMonster(int p){
        int rn = GetS.GetRandomNumber(0,1000);
        return  rn <= p;
    }
}
