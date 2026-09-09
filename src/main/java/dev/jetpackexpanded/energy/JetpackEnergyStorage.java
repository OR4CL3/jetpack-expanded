package dev.jetpackexpanded.energy;

import dev.jetpackexpanded.item.JetpackItem;
import dev.jetpackexpanded.registry.JetpackTier;
import dev.jetpackexpanded.util.JetpackUtils;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.minecraft.item.ItemStack;
import team.reborn.energy.api.EnergyStorage;

/**
 * Expõe a energia do jetpack para o ecossistema TeamReborn Energy
 * (carregadores, cabos, etc. conseguem carregar o jetpack automaticamente).
 */
@SuppressWarnings("UnstableApiUsage")
public class JetpackEnergyStorage implements EnergyStorage {

    private final ContainerItemContext ctx;
    private final JetpackTier tier;

    public JetpackEnergyStorage(ContainerItemContext ctx, JetpackTier tier) {
        this.ctx  = ctx;
        this.tier = tier;
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        if (tier.isCreative()) return 0;

        ItemStack stack   = ctx.getItemVariant().toStack();
        long current      = JetpackUtils.getEnergy(stack);
        long space        = tier.maxEnergy() - current;
        long toInsert     = Math.min(Math.min(maxAmount, tier.chargeRate()), space);

        if (toInsert <= 0) return 0;

        ItemStack updated = stack.copy();
        JetpackUtils.setEnergy(updated, current + toInsert);

        // Atualiza o slot via transaction do Fabric Transfer API
        if (ctx.exchange(ItemVariant.of(updated), 1, transaction) == 1) {
            return toInsert;
        }
        return 0;
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        return 0; // jetpacks não fornecem energia para itens externos
    }

    @Override
    public long getAmount() {
        if (tier.isCreative()) return tier.maxEnergy();
        return JetpackUtils.getEnergy(ctx.getItemVariant().toStack());
    }

    @Override
    public long getCapacity() {
        return tier.maxEnergy();
    }
}
