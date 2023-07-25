package anmao.idoll.miaooo.ApiFcn;

public class IsS {
    public static boolean IsSpawnBigMonster(int p){
        int rn = GetS.GetRandomNumber(0,1000);
        return  rn <= p;
    }

    public static boolean IsSpawnSkillMonster(int p){
        int rn = GetS.GetRandomNumber(0,1000);
        return  rn <= p;
    }
}
