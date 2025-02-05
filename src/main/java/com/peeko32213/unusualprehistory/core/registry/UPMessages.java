package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.message.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.server.ServerLifecycleHooks;

public class UPMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(UnusualPrehistory.MODID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(SyncItemStackC2SPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncItemStackC2SPacket::new)
                .encoder(SyncItemStackC2SPacket::toBytes)
                .consumerMainThread(SyncItemStackC2SPacket::handle)
                .add();

        net.messageBuilder(SyncItemStackS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(SyncItemStackS2CPacket::new)
                .encoder(SyncItemStackS2CPacket::toBytes)
                .consumerMainThread(SyncItemStackS2CPacket::handle)
                .add();

        net.messageBuilder(AmberProtectionSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(AmberProtectionSyncS2CPacket::new)
                .encoder(AmberProtectionSyncS2CPacket::toBytes)
                .consumerMainThread(AmberProtectionSyncS2CPacket::handle)
                .add();

        net.messageBuilder(AttackInputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AttackInputMessage::new)
                .encoder(AttackInputMessage::toBytes)
                .consumerMainThread(AttackInputMessage::handle)
                .add();

        net.messageBuilder(AttackOutputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(AttackOutputMessage::new)
                .encoder(AttackOutputMessage::toBytes)
                .consumerMainThread(AttackOutputMessage::handle)
                .add();

        net.registerMessage(id(), UPMessageHurtMultipart.class,
                UPMessageHurtMultipart::write,
                UPMessageHurtMultipart::read,
                UPMessageHurtMultipart.Handler::handle);

        net.registerMessage(id(), LootFruitTierPacketS2C.class,
                LootFruitTierPacketS2C::encode,
                LootFruitTierPacketS2C::decode,
                LootFruitTierPacketS2C::onPacketReceived);

        net.registerMessage(id(), LootFruitPacketS2C.class,
                LootFruitPacketS2C::encode,
                LootFruitPacketS2C::decode,
                LootFruitPacketS2C::onPacketReceived);

        net.registerMessage(id(), AnalyzerRecipeS2C.class,
                AnalyzerRecipeS2C::encode,
                AnalyzerRecipeS2C::decode,
                AnalyzerRecipeS2C::onPacketReceived);

        net.registerMessage(id(), EncyclopediaS2C.class,
                EncyclopediaS2C::encode,
                EncyclopediaS2C::decode,
                EncyclopediaS2C::onPacketReceived);

        net.registerMessage(id(), EncyclopediaRootPageS2C.class,
                EncyclopediaRootPageS2C::encode,
                EncyclopediaRootPageS2C::decode,
                EncyclopediaRootPageS2C::onPacketReceived);

//        net.registerMessage(id(), BalaurMountMessage.class,
//                BalaurMountMessage::write,
//                BalaurMountMessage::read,
//                BalaurMountMessage.Handler::handle);

        net.messageBuilder(ParticleSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(ParticleSyncS2CPacket::new)
                .encoder(ParticleSyncS2CPacket::toBytes)
                .consumerMainThread(ParticleSyncS2CPacket::handle)
                .add();

    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }


    public static <MSG> void sendMSGToAll(MSG message) {
        for (ServerPlayer player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {
            sendNonLocal(message, player);
        }
    }

    public static <MSG> void sendNonLocal(MSG msg, ServerPlayer player) {
        INSTANCE.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }
}

