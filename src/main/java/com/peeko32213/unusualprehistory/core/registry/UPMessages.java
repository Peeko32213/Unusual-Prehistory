package com.peeko32213.unusualprehistory.core.registry;

import com.peeko32213.unusualprehistory.UnusualPrehistory;
import com.peeko32213.unusualprehistory.common.message.*;
import com.peeko32213.unusualprehistory.common.networking.packet.AmberProtectionSyncS2CPacket;
import com.peeko32213.unusualprehistory.common.networking.packet.SyncItemStackC2SPacket;
import com.peeko32213.unusualprehistory.common.networking.packet.SyncItemStackS2CPacket;
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

        net.messageBuilder(HwachaKeyInputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(HwachaKeyInputMessage::new)
                .encoder(HwachaKeyInputMessage::toBytes)
                .consumerMainThread(HwachaKeyInputMessage::handle)
                .add();

        net.messageBuilder(HwachaKeyOutputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(HwachaKeyOutputMessage::new)
                .encoder(HwachaKeyOutputMessage::toBytes)
                .consumerMainThread(HwachaKeyOutputMessage::handle)
                .add();

        net.messageBuilder(TrikeKeyInputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TrikeKeyInputMessage::new)
                .encoder(TrikeKeyInputMessage::toBytes)
                .consumerMainThread(TrikeKeyInputMessage::handle)
                .add();

        net.messageBuilder(TrikeKeyOutputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(TrikeKeyOutputMessage::new)
                .encoder(TrikeKeyOutputMessage::toBytes)
                .consumerMainThread(TrikeKeyOutputMessage::handle)
                .add();

        net.messageBuilder(UlughKeyInputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UlughKeyInputMessage::new)
                .encoder(UlughKeyInputMessage::toBytes)
                .consumerMainThread(UlughKeyInputMessage::handle)
                .add();

        net.messageBuilder(UlughKeyOutputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(UlughKeyOutputMessage::new)
                .encoder(UlughKeyOutputMessage::toBytes)
                .consumerMainThread(UlughKeyOutputMessage::handle)
                .add();

        net.messageBuilder(MegatheriumKeyInputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MegatheriumKeyInputMessage::new)
                .encoder(MegatheriumKeyInputMessage::toBytes)
                .consumerMainThread(MegatheriumKeyInputMessage::handle)
                .add();

        net.messageBuilder(MegatheriumKeyOutputMessage.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MegatheriumKeyOutputMessage::new)
                .encoder(MegatheriumKeyOutputMessage::toBytes)
                .consumerMainThread(MegatheriumKeyOutputMessage::handle)
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

