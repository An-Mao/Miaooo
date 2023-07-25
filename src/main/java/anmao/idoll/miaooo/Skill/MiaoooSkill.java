package anmao.idoll.miaooo.Skill;

import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import java.util.List;

public class MiaoooSkill {
    private static final String[] SkillList = {
            "SkillForceField",
            "SkillImmortality",
            "SkillSoulStrike",
            "SkillLeader",
            "SkillCorrosion",
            "SkillDemonBreaker",
            "SkillReflection",
            "SkillRealm",
            "SkillRepel",
            "SkillChaos"
    };
    private static final int SkillNumber = 10;

    private static final MobEffectInstance ForceField = new MobEffectInstance(MobEffects.LEVITATION,99999,5);

    private static final float LeaderRadius = 10;

    private static final float RealmRadius = 10;

    private static final MobEffectInstance[] DeBuff = {
            new MobEffectInstance(MobEffects.WITHER,99999,1),
            new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,99999,1),
            new MobEffectInstance(MobEffects.DIG_SLOWDOWN,99999,1),
            new MobEffectInstance(MobEffects.UNLUCK,99999,1),
            new MobEffectInstance(MobEffects.CONFUSION,99999,1),
            new MobEffectInstance(MobEffects.BLINDNESS,99999,1),
            new MobEffectInstance(MobEffects.HUNGER,99999,1),
            new MobEffectInstance(MobEffects.WEAKNESS,99999,1),
            new MobEffectInstance(MobEffects.POISON,99999,1),
            new MobEffectInstance(MobEffects.DARKNESS,99999,1)
    };
    private static final int DeBuffNum = 10;
    public static boolean isSkillMonster(Monster _monster){
        for (String skill : SkillList) {
            if (_monster.getTags().contains(skill)){
                return true;
            }
        }
        return false;
    }
    public static void setSkill(Monster _monster){
        int nub;
        int skill;
        nub = RandomSource.createNewThreadLocalInstance().nextInt(1,SkillNumber);
        for (int i =0 ;i<nub;i++){
            skill = RandomSource.createNewThreadLocalInstance().nextInt(1,SkillNumber);
            addSkill(_monster,skill-1);
        }
    }
    public static boolean haveSkill(Monster _monster,int _skill_index){
        return _monster.getTags().contains(SkillList[_skill_index]);
    }
    public static void addSkill(Monster _monster,int _skill_index){
        _monster.addTag(SkillList[_skill_index]);
    }
    public static void removeSkill(Monster _monster,int _skill_index){
        _monster.removeTag(SkillList[_skill_index]);
    }
    public static void Skill_ForceField(Monster _monster){
        if (haveSkill(_monster,0)){
            _monster.addEffect(ForceField);
        }
    }
    public static void Skill_Immortality(Monster _monster){
        if (haveSkill(_monster,1)){
            _monster.setHealth(_monster.getMaxHealth());
        }
    }
    public static void Skill_SoulStrike(Monster _monster, LivingAttackEvent event){
        if (haveSkill(_monster,2)){
            float health = event.getEntity().getHealth() - event.getAmount();
            if (health < 0){
                health = 0;
            }
            event.getEntity().setHealth(health);
        }
    }
    public static void Skill_Leader(Monster _monster){
        if (haveSkill(_monster,3)){
            List<? extends Monster> near_monsters = _monster.level.getEntities(EntityTypeTest.forClass(_monster.getClass()), _monster.getBoundingBox().inflate(LeaderRadius), Entity::isAlive);
            if (near_monsters.size() < 1){
                addSkill(_monster,0);
                return;
            }
            for (Monster monster:near_monsters) {
                if(!isSkillMonster(monster)){
                    removeSkill(_monster,0);
                    return;
                }
            }
        }
    }
    public static boolean Leader_unLeader(Monster _monster){
        List<? extends Monster> near_monsters = _monster.level.getEntities(EntityTypeTest.forClass(_monster.getClass()), _monster.getBoundingBox().inflate(LeaderRadius), Entity::isAlive);
        for (Monster monster:near_monsters) {
            if(haveSkill(monster,3)){
                return true;
            }
        }
        return false;
    }
    public static void Skill_Corrosion(Monster _monster, LivingHurtEvent event){
        if (haveSkill(_monster,4)){
            ItemStack itm = event.getEntity().getItemBySlot(EquipmentSlot.HEAD);
            int dam = itm.getDamageValue()-10;
            if (dam < 0){dam = 0;}
            itm.setDamageValue(dam);
        }
    }
    public static void Skill_DemonBreaker(Monster _monster,LivingAttackEvent event){
        if (haveSkill(_monster,5)){
            //
        }
    }
    public static void Skill_Reflection(Monster _monster, LivingHurtEvent event){
        if (haveSkill(_monster,6)){
            event.getSource().getEntity().hurt(DamageSource.OUT_OF_WORLD,event.getAmount());
        }
    }
    public static void Skill_Realm(Monster _monster){
        if (haveSkill(_monster,7)){
            List<Player> near_players = _monster.level.getEntities(EntityTypeTest.forClass(Player.class), _monster.getBoundingBox().inflate(RealmRadius), Entity::isAlive);
            for (Player player:near_players){
                player.hurt(DamageSource.MAGIC,1);
            }
        }
    }
    public static void Skill_Repel(Monster _monster){
        if (haveSkill(_monster,8)){
            List<Player> near_players = _monster.level.getEntities(EntityTypeTest.forClass(Player.class), _monster.getBoundingBox().inflate(RealmRadius), Entity::isAlive);
            for (Player player:near_players){
                player.teleportTo(0,0,0);
            }
        }
    }
    public static void Skill_Chaos(Monster _monster,LivingHurtEvent event){
        if (haveSkill(_monster,9)){
            for (int i =0; i<2;i++){
                int index = RandomSource.createNewThreadLocalInstance().nextInt(1,DeBuffNum );
                event.getEntity().addEffect(DeBuff[index-1]);
            }
        }
    }
}
