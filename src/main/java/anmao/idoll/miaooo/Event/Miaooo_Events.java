package anmao.idoll.miaooo.Event;

import anmao.idoll.miaooo.ApiFcn.GetS;
import anmao.idoll.miaooo.ApiFcn.IsS;
import anmao.idoll.miaooo.ApiFcn.SetS;
import anmao.idoll.miaooo.Capability.San.Net.SanNTC;
import anmao.idoll.miaooo.Capability.San.San;
import anmao.idoll.miaooo.Capability.San.SanF;
import anmao.idoll.miaooo.Capability.San.SanPro;
import anmao.idoll.miaooo.Capability.San.SanTags;
import anmao.idoll.miaooo.Capability.TimeIsLife;
import anmao.idoll.miaooo.Capability.TimeIsLifePro;
import anmao.idoll.miaooo.Config.Configs;
import anmao.idoll.miaooo.Dat.Dat_;
import anmao.idoll.miaooo.Miaooo;
import anmao.idoll.miaooo.Net.Messages;
import anmao.idoll.miaooo.Net.SendToClient.TimeIsLifeC;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import java.util.Iterator;
import java.util.List;

public class Miaooo_Events {
    @Mod.EventBusSubscriber(modid = Miaooo.MOD_ID)
    public static class ForgeEvents{
        @SubscribeEvent
        public static void onPlayerCloned(PlayerEvent.Clone event)
        {
            if (event.isWasDeath())
            {
                event.getOriginal().getCapability(TimeIsLifePro.PLAYER_Time).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(TimeIsLifePro.PLAYER_Time).ifPresent(newStore ->{
                        newStore.copyFrom(oldStore);
                    });
                });
                event.getOriginal().getCapability(SanPro.PLAYER_SAN).ifPresent(oldStore -> {
                    event.getOriginal().getCapability(SanPro.PLAYER_SAN).ifPresent(newStore ->{
                        newStore.copyFrom(oldStore);
                    });
                });
            }
        }
        @SubscribeEvent
        public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
        {

            event.register(TimeIsLife.class);
            event.register(San.class);
        }
        @SubscribeEvent
        public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event)
        {
            if (event.getObject() instanceof Player)
            {
                if (!event.getObject().getCapability(TimeIsLifePro.PLAYER_Time).isPresent())
                {
                    event.addCapability(new ResourceLocation(Miaooo.MOD_ID,"timeislife"),new TimeIsLifePro());
                }
                if (!event.getObject().getCapability(SanPro.PLAYER_SAN).isPresent())
                {
                    event.addCapability(new ResourceLocation(Miaooo.MOD_ID,"san"),new SanPro());
                }
            }
        }
        @SubscribeEvent
        public static void onPlayerTick(TickEvent.PlayerTickEvent event)
        {
            if (event.side == LogicalSide.SERVER) {
                if (event.player instanceof ServerPlayer serverPlayer) {
                    if (Configs.Config_TimeMin.get() != 0.0d) {
                        serverPlayer.getCapability(TimeIsLifePro.PLAYER_Time).ifPresent(playerTime -> {
                            playerTime.addTime();
                            if (playerTime.getTime() > Configs.Config_TimeMin.get()) {
                                serverPlayer.hurt(DamageSource.OUT_OF_WORLD, Configs.Config_TimeDamage.get().floatValue());
                            }
                            Messages.sendToPlayer(new TimeIsLifeC(playerTime.getTime()), serverPlayer);
                        });
                    }
                    serverPlayer.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                        if (SanF.SanDeath(serverPlayer, playerSan.getSan()) && serverPlayer.getRandom().nextFloat() < 0.005f) {
                            if (serverPlayer.getLevel().dimension() == Level.NETHER) {
                                playerSan.subSan(1);
                            }
                            Messages.sendToPlayer(new SanNTC(playerSan.getSan()), serverPlayer);
                        }
                    });
                }
            }
        }
        @SubscribeEvent
        public static void onSpawned(LivingSpawnEvent.AllowDespawn event){
            //
        }
        @SubscribeEvent
        public static void onDeath(LivingDeathEvent event)
        {
            if (event.getEntity() instanceof Monster monster)
            {
                // remove all son
                if (monster.getTags().contains(Dat_.Tags_MonsterFather)) {
                    List<Entity> sons = GetS.GetEntityRadiusEntitiesWithTag(monster, Configs.Config_MonsterSonRadius.get(), Dat_.Tags_MonsterSon);
                    for (Entity son:sons){
                        son.remove(Entity.RemovalReason.DISCARDED);
                    }
                }

                //heal monster
                List<Entity> entities = GetS.GetEntityRadiusEntities(monster, Configs.Config_HealRadius.get());
                if (entities.size() > 1) {
                    float heal_health = monster.getMaxHealth() * Configs.Config_HealScale.get().floatValue();
                    for (Entity i : entities) {
                        if (i instanceof LivingEntity living) {
                            living.heal(heal_health);
                        }
                    }
                }
            }else if (event.getEntity() instanceof Player dplayer)
            {
                dplayer.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                    playerSan.addSan(100);
                    Messages.sendToPlayer(new SanNTC(playerSan.getSan()),(ServerPlayer) dplayer);
                });
            }


            if (event.getSource().getEntity() instanceof ServerPlayer player)
            {
                player.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                    if (event.getEntity() instanceof Monster)
                    {
                        playerSan.addSan(1);
                        Messages.sendToPlayer(new SanNTC(playerSan.getSan()), player);
                    }else {
                        if (SanF.SanDeath(player, playerSan.getSan())) {
                            if (event.getEntity().getType() == EntityType.VILLAGER || event.getEntity().getType() == EntityType.PLAYER) {
                                playerSan.subSan(3);
                                Messages.sendToPlayer(new SanNTC(playerSan.getSan()), player);
                            } else {
                                playerSan.subSan(1);
                                Messages.sendToPlayer(new SanNTC(playerSan.getSan()), player);
                            }
                        }
                    }

                });
            }
        }
        @SubscribeEvent
        public static void onJoinLevel(EntityJoinLevelEvent event){
            //------------------------------------------------
            if (!event.getLevel().isClientSide()) {
                if (event.getEntity() instanceof ServerPlayer player) {
                    player.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                        Messages.sendToPlayer(new SanNTC(playerSan.getSan()), player);
                    });
                }


                //------------------------------------------------
                if (event.getEntity() instanceof Mob mob) {
                    if (!mob.getTags().contains(Dat_.Tags_ChanceDrop)) {
                        float a = Configs.Config_BanItemDrop.get().floatValue();
                        if (a >= 0.0f) {
                            mob.addTag(Dat_.Tags_ChanceDrop);
                            mob.setDropChance(EquipmentSlot.CHEST, a);
                            mob.setDropChance(EquipmentSlot.FEET, a);
                            mob.setDropChance(EquipmentSlot.HEAD, a);
                            mob.setDropChance(EquipmentSlot.LEGS, a);
                            mob.setDropChance(EquipmentSlot.MAINHAND, a);
                            mob.setDropChance(EquipmentSlot.OFFHAND, a);
                        }
                    }
                }

                //add father spawn
                if (event.getEntity() instanceof Monster monster) {

                    if (IsS.IsSpawnBigMonster()) {
                        List<Entity> entities = GetS.GetEntityRadiusEntities(monster, Configs.Config_MonsterFatherRadius.get());
                        if (entities.size() > 1) {
                            float health = monster.getMaxHealth();
                            double atk = GetS.GetEntityAttribute(monster, Attributes.ATTACK_DAMAGE);
                            for (Entity i : entities) {
                                if (i instanceof LivingEntity living) {
                                    health = health + living.getMaxHealth();
                                    atk = atk + GetS.GetEntityAttribute(living, Attributes.ATTACK_DAMAGE);
                                    living.addTag(Dat_.Tags_MonsterSon);
                                }
                            }
                            SetS.SetMonsterEffect(monster, MobEffects.DAMAGE_BOOST, 999999, entities.size());
                            SetS.SetMonsterEffect(monster, MobEffects.GLOWING, 999999, entities.size());
                            SetS.SetMonsterMaxHealth(monster, health);
                            SetS.SetMonsterMaxAttackDamage(monster, atk);
                            monster.addTag(Dat_.Tags_MonsterFather);
                            monster.heal(health);
                        }
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onHurt(LivingHurtEvent event){
            //Safe Son
            if (event.getEntity() instanceof Monster monster){
                if (!monster.getTags().contains(Dat_.Tags_MonsterFather)) {
                    if (monster.getTags().contains(Dat_.Tags_MonsterSon)) {
                        List<Entity> fathers = GetS.GetEntityRadiusEntitiesWithTag(monster, Configs.Config_MonsterSonRadius.get(), Dat_.Tags_MonsterFather);
                        if (!fathers.isEmpty()) {
                            float sc = Configs.Config_MonsterSonDamageScale.get().floatValue();
                            if (sc == 0.0f) {
                                event.setCanceled(true);
                            }else {
                                event.setAmount(event.getAmount() * sc);
                            }
                            return;
                        }
                    }
                }
            }
            //
            if (event.getEntity() instanceof Mob mob) {
                //
                if (event.getAmount() > Configs.Config_MinDamage.get()) {
                    float damage;
                    if (event.getSource() == DamageSource.OUT_OF_WORLD) {
                        damage = Configs.Config_MaxDamageWithHealthOut.get().floatValue();
                    }else {
                        damage = Configs.Config_MaxDamageWithHealth.get().floatValue();
                    }
                    damage = mob.getMaxHealth() * damage;
                    if (event.getAmount() > damage) {
                        event.setAmount(damage);
                        mob.addTag(Dat_.Tags_DamageSafe);
                    }
                }
            }

        }
        @SubscribeEvent
        public static void onAttack(LivingAttackEvent event) {
            //monster safe
            if (event.getEntity() instanceof Mob mob) {
                //monster safe
                if (mob.getTags().contains(Dat_.Tags_DamageSafe)){
                    int _tick = mob.tickCount - mob.getLastHurtByMobTimestamp();
                    if ( _tick > 0 && _tick < Configs.Config_SafeTime.get()){
                        event.setCanceled(true);
                        return;
                    }
                    mob.removeTag(Dat_.Tags_DamageSafe);
                }
                //atk radius
                Entity source = event.getSource().getEntity();
                if (source != null) {
                    Iterator<ItemStack> items = event.getSource().getEntity().getHandSlots().iterator();
                    ItemStack oitem = items.next();
                    if (items.hasNext()){
                        oitem = items.next();
                    }
                    //System.out.println(oitem);
                    int AAR;
                    if (source != event.getSource().getDirectEntity()) {
                        AAR = Configs.Config_DamageRadiusO.get();
                        if (oitem.getItem() == Items.AIR) {
                            AAR -= Configs.Config_DamageRadiusOffHandO.get();
                        }
                    }else {
                        AAR = Configs.Config_DamageRadius.get();
                        if (!oitem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_SPEED).isEmpty()){
                            if (!oitem.getAttributeModifiers(EquipmentSlot.MAINHAND).get(Attributes.ATTACK_DAMAGE).isEmpty()){
                                AAR += Configs.Config_DamageRadiusOffHand.get();
                            }
                        }
                    }
                    double x = source.getX() - mob.getX();
                    double y = source.getY() - mob.getY();
                    double z = source.getZ() - mob.getZ();
                    //if (x * x + y * y + z * z > AAR) {
                    //    event.setCanceled(true);
                    //}

                    if ( x < -AAR || x > AAR || y < -AAR || y > AAR || z < -AAR || z > AAR){
                        event.setCanceled(true);
                    }
                }
            }
        }
        @SubscribeEvent
        public static void onDamage(LivingDamageEvent event){
            if (event.getSource().getEntity() instanceof ServerPlayer player)
            {
                player.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                    int _san =playerSan.getSan();
                    if (_san <50 && SanF.SanDeath(player, _san))
                    {
                        float damage_self = Math.max(player.getMaxHealth()*0.04f,1.0f);
                        player.hurt(DamageSource.OUT_OF_WORLD,damage_self);
                        playerSan.subSan(1);
                        event.setAmount(event.getAmount()*(2-_san*0.01f));
                    }
                    Messages.sendToPlayer(new SanNTC(playerSan.getSan()), player);
                });
            }
        }
        @SubscribeEvent
        public static void onUseItemFinish(LivingEntityUseItemEvent.Finish event) {
            if (event.getEntity() instanceof ServerPlayer player) {
                player.getCapability(SanPro.PLAYER_SAN).ifPresent(playerSan -> {
                    if (SanF.SanDeath(player, playerSan.getSan())) {
                        SanTags sanTags = new SanTags();
                        if (sanTags.isSanTags(event.getItem())){
                            if (sanTags.getType() == SanTags.TYPE_ADD){
                                playerSan.addSan(sanTags.getNumbers());
                            }
                            if (sanTags.getType() == SanTags.TYPE_SUB){
                                playerSan.subSan(sanTags.getNumbers());
                            }
                        }
                        Messages.sendToPlayer(new SanNTC(playerSan.getSan()), player);
                    }
                });
            }
        }
    }
}
