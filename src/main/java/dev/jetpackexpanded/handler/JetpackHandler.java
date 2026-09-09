package dev.jetpackexpanded.handler;

import dev.jetpackexpanded.item.JetpackItem;
import dev.jetpackexpanded.registry.JetpackTier;
import dev.jetpackexpanded.util.JetpackUtils;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;

/**
 * Lógica de voo do jetpack — roda no server tick.
 * Chamado por ServerTickEvents em JetpackExpanded.
 */
public final class JetpackHandler {

    private JetpackHandler() {}

    public static void onWorldTick(ServerWorld world) {
        world.getPlayers().forEach(player -> {
            ItemStack chest = player.getEquippedStack(EquipmentSlot.CHEST);
            if (!(chest.getItem() instanceof JetpackItem jetpackItem)) return;

            JetpackTier tier    = jetpackItem.getTier();
            boolean engineOn    = JetpackUtils.isEngineOn(chest);
            boolean hovering    = JetpackUtils.isHovering(chest);
            boolean hasEnergy   = tier.isCreative() || JetpackUtils.getEnergy(chest) >= tier.fuelUsage();

            if (!engineOn || !hasEnergy) {
                // Hover sem engine: amortece queda suavemente
                if (hovering && player.isFallFlying()) return;
                if (hovering && player.getVelocity().y < 0) {
                    Vec3d vel = player.getVelocity();
                    player.setVelocity(vel.x, Math.max(vel.y, -tier.hoverSpeed()), vel.z);
                    player.velocityModified = true;
                    player.fallDistance = 0;
                }
                return;
            }

            Vec3d vel = player.getVelocity();
            boolean jumping = player.input != null && player.input.jumping;
            boolean sneaking = player.isSneaking();

            double newY = vel.y;

            if (jumping) {
                // Acelera para cima
                newY = Math.min(vel.y + tier.accel(), tier.speedVertical());
            } else if (hovering) {
                // Hover: mantém altitude, amortece descida
                if (vel.y < 0) newY = Math.min(0, vel.y + tier.hoverSpeed() * 0.5);
                else if (vel.y > 0) newY = Math.max(0, vel.y - 0.05);
            } else if (sneaking) {
                // Descer controlado
                newY = Math.max(vel.y - 0.05, -tier.hoverSpeed());
            }

            player.setVelocity(vel.x, newY, vel.z);
            player.velocityModified = true;
            player.fallDistance = 0;

            // Drena energia (creative não drena)
            if (!tier.isCreative()) {
                JetpackUtils.drainEnergy(chest, tier.fuelUsage());
            }
        });
    }
}
