package de.yvelgard.clientmod;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record YPayload(byte[] data) implements CustomPacketPayload {

    public static final Type<YPayload> TYPE = new Type<>(Identifier.fromNamespaceAndPath("yvelgard", "client"));

    public static final StreamCodec<FriendlyByteBuf, YPayload> CODEC = CustomPacketPayload.codec(
            (payload, buf) -> buf.writeBytes(payload.data),
            buf -> {
                byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);
                return new YPayload(bytes);
            });

    @Override
    public Type<YPayload> type() {
        return TYPE;
    }
}
