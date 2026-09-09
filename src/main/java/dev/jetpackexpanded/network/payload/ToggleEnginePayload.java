package dev.jetpackexpanded.network.payload;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import static dev.jetpackexpanded.JetpackExpanded.MOD_ID;

/** Enviado pelo cliente quando o jogador pressiona a tecla de toggle do motor. */
public record ToggleEnginePayload() implements CustomPayload {

    public static final Id<ToggleEnginePayload> ID =
        new Id<>(Identifier.of(MOD_ID, "toggle_engine"));

    public static final PacketCodec<PacketByteBuf, ToggleEnginePayload> CODEC =
        PacketCodec.unit(new ToggleEnginePayload());

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
