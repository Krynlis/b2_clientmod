package de.b2clientmod.forge;

import de.b2clientmod.B2Keys;
import de.b2clientmod.B2Payload;
import de.b2clientmod.ServerLink;
import io.netty.buffer.Unpooled;
import java.util.function.Predicate;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.EventNetworkChannel;

@Mod(B2Keys.MOD_ID)
public final class B2ClientForge {

    private static EventNetworkChannel channel;

    public B2ClientForge() {
        channel = ChannelBuilder.named(B2Payload.TYPE.id()).optional().eventNetworkChannel();
        channel.addListener(B2ClientForge::receive);

        ServerLink.init(new ServerLink.Transport() {
            @Override
            public boolean canSend() {
                ClientPacketListener connection = Minecraft.getInstance().getConnection();
                return connection != null && channel.isRemotePresent(connection.getConnection());
            }

            @Override
            public void send(B2Payload payload) {
                ClientPacketListener connection = Minecraft.getInstance().getConnection();
                if (connection != null) {
                    channel.send(new FriendlyByteBuf(Unpooled.wrappedBuffer(payload.data())), connection.getConnection());
                }
            }
        });

        RegisterKeyMappingsEvent.BUS.addListener(event -> {
            for (KeyMapping mapping : B2Keys.create(KeyMapping.Category.register(B2Keys.CATEGORY_ID))) {
                event.register(mapping);
            }
        });
        TickEvent.ClientTickEvent.Post.BUS.addListener(event -> ServerLink.tick(Minecraft.getInstance()));
        ScreenEvent.Opening.BUS.addListener((Predicate<ScreenEvent.Opening>) event -> ServerLink.interceptScreen(event.getNewScreen()));
        ClientPlayerNetworkEvent.LoggingOut.BUS.addListener(event -> ServerLink.reset());
    }

    private static void receive(CustomPayloadEvent event) {
        CustomPayloadEvent.Context source = event.getSource();
        if (!source.isClientSide() || event.getPayload() == null) {
            return;
        }
        FriendlyByteBuf buf = event.getPayload();
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        source.enqueueWork(() -> ServerLink.receive(new B2Payload(bytes), Minecraft.getInstance()));
        source.setPacketHandled(true);
    }
}
