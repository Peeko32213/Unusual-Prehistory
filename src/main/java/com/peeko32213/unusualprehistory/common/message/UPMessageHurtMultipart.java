package com.peeko32213.unusualprehistory.common.message;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.entity.IHurtableMultipart;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class UPMessageHurtMultipart {
    public int part;
    public int parent;
    public float damage;
    public String damageType;

    public UPMessageHurtMultipart(int part, int parent, float damage) {
        this.part = part;
        this.parent = parent;
        this.damage = damage;
        this.damageType = "";
    }

    public UPMessageHurtMultipart(int part, int parent, float damage, String damageType) {
        this.part = part;
        this.parent = parent;
        this.damage = damage;
        this.damageType = damageType;
    }


    public static UPMessageHurtMultipart read(FriendlyByteBuf buf) {
        return new UPMessageHurtMultipart(buf.readInt(), buf.readInt(), buf.readFloat(), buf.readUtf());
    }

    public static void write(UPMessageHurtMultipart message, FriendlyByteBuf buf) {
        buf.writeInt(message.part);
        buf.writeInt(message.parent);
        buf.writeFloat(message.damage);
        buf.writeUtf(message.damageType);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(UPMessageHurtMultipart message, Supplier<NetworkEvent.Context> context) {
            context.get().setPacketHandled(true);
            Player player = context.get().getSender();
            if(context.get().getDirection().getReceptionSide() == LogicalSide.CLIENT){
                player = UnusualPrehistory.PROXY.getClientSidePlayer();
            }

            if (player != null) {
                if (player.level != null) {
                    Entity part = player.level.getEntity(message.part);
                    Entity parent = player.level.getEntity(message.parent);
                    if(part instanceof IHurtableMultipart && parent instanceof LivingEntity){
                        ((IHurtableMultipart) part).onAttackedFromServer((LivingEntity)parent, message.damage, new DamageSource(message.damageType));
                    }
                    if(part == null && parent != null && parent.isMultipartEntity()){
                        parent.hurt(new DamageSource(message.damageType), message.damage);
                    }
                }
            }
        }
    }
}
