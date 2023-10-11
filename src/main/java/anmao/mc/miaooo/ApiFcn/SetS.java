package anmao.mc.miaooo.ApiFcn;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;

public class SetS {
    public static void SetMonsterEffect(Monster _monster, MobEffect _type, int _time, int _lvl){
        _monster.addEffect(new MobEffectInstance(_type, _time, _lvl));
    }
    public static void SetMonsterAttribute(Monster _monster , Attributes _attributes){
        //
    }
    public static void SetMonsterMaxHealth(Monster _entity,float _health){
        _entity.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("MaxHealth", _health, AttributeModifier.Operation.ADDITION));
    }
    public static void SetMonsterMaxAttackDamage(Monster _entity,double _atk){
        _entity.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier("AttackDamage",_atk, AttributeModifier.Operation.ADDITION));

    }
}
