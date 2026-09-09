package dev.jetpackexpanded.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static dev.jetpackexpanded.JetpackExpanded.MOD_ID;

/** Enviado pelo cliente quando o jogador pressiona a tecla de toggle de hover. */
public record ToggleHoverPayload() implements CustomPayload {

    public static final Id<ToggleHoverPayload> ID =
        new Id<>(Identifier.of(MOD_ID, "toggle_hover"));

    public static final PacketCodec<PacketByteBuf, ToggleHoverPayload> CODEC =
        PacketCodec.unit(new ToggleHoverPayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
