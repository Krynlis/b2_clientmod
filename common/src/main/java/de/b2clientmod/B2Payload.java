package de.b2clientmod;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record B2Payload(byte[] data) implements CustomPacketPayload {

    public static final Type<B2Payload> TYPE = new Type<>(Identifier.fromNamespaceAndPath("b2", "client"));

    public static final StreamCodec<FriendlyByteBuf, B2Payload> CODEC = CustomPacketPayload.codec(
            (payload, buf) -> buf.writeBytes(payload.data),
            buf -> {
                byte[] bytes = new byte[buf.readableBytes()];
                buf.readBytes(bytes);
                return new B2Payload(bytes);
            });

    @Override
    public Type<B2Payload> type() {
        return TYPE;
    }
}
