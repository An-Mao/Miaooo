package anmao.idoll.miaooo.ApiFcn;

import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;

import java.util.ArrayList;
import java.util.List;

public class GetS {
    public static List< Entity> GetEntityRadiusEntities(Entity _entity,float _radius){
        return (List<Entity>) _entity.level.getEntities(EntityTypeTest.forClass(_entity.getClass()),_entity.getBoundingBox().inflate(_radius),Entity::isAlive);
        //return  level.getEntities(entity,entity.getBoundingBox().inflate(6));
    }
    public static List< Entity> GetEntityRadiusEntitiesWithTags(Entity _entity,float _radius,String _str){
        List<Entity> entities = GetEntityRadiusEntities(_entity,_radius);
        List<Entity>  ne = new ArrayList<Entity>();
        for (Entity i:entities){
            if (i.getTags().contains(_str)){
                ne.add(i);
            }
        }
        return ne;
        //return  level.getEntities(entity,entity.getBoundingBox().inflate(6));
    }
    public static int GetRandomNumber(int _min , int _max)
    {
        return RandomSource.createNewThreadLocalInstance().nextInt(_min,_max);
    }
}
