package anmao.idoll.miaooo.Capability;

import net.minecraft.world.entity.monster.Monster;

import java.util.ArrayList;
import java.util.List;

public class MonsterFamily {
    private Monster monster_father = null;
    private final List<Monster> monster_son = new ArrayList<Monster>();

    public Monster getMonsterFather() {
        return this.monster_father;
    }

    public void addMonsterSon(Monster _monster){
        this.monster_son.add(_monster);
    }
    public boolean isMonster_son(Monster _monster) {
        return this.monster_son.contains(_monster);
    }

    public void setMonsterFather(Monster _monster){
        this.monster_father = _monster;
    }
}
