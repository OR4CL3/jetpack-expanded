package dev.jetpackexpanded.registry;

/**
 * Define as estatísticas de cada tier de jetpack.
 * Para adicionar um novo tier: crie uma constante aqui e registre em ModItems.
 */
public record JetpackTier(
    String id,
    long maxEnergy,      // FE máximo armazenado
    long fuelUsage,      // FE por tick voando
    long chargeRate,     // FE por tick ao carregar (via carregador externo)
    double speedVertical,// velocidade vertical máxima (m/tick)
    double speedSprint,  // boost horizontal ao voar
    double accel,        // aceleração vertical por tick
    double hoverSpeed,   // velocidade no modo hover (cai devagar)
    int defense          // pontos de defesa da chestplate
) {
    // ── Tiers ────────────────────────────────────────────────────────────────

    /** Iron — básico. ~1min de voo contínuo. */
    public static final JetpackTier MK1 = new JetpackTier(
        "mk1",
        500_000L, 80L, 200L,
        0.6, 0.15, 0.14, 0.12,
        4
    );

    /** Gold — intermediário. Mais rápido e mais energia. */
    public static final JetpackTier MK2 = new JetpackTier(
        "mk2",
        1_000_000L, 150L, 400L,
        0.85, 0.22, 0.17, 0.17,
        5
    );

    /** Diamond — avançado. Voo confortável. */
    public static final JetpackTier MK3 = new JetpackTier(
        "mk3",
        2_500_000L, 250L, 800L,
        1.15, 0.30, 0.22, 0.22,
        6
    );

    /** Netherite — último tier survival. */
    public static final JetpackTier MK4 = new JetpackTier(
        "mk4",
        5_000_000L, 400L, 1600L,
        1.6, 0.40, 0.28, 0.28,
        8
    );

    /** Creative — energia infinita, sem consumo. */
    public static final JetpackTier CREATIVE = new JetpackTier(
        "creative",
        Long.MAX_VALUE / 2, 0L, 0L,
        2.0, 0.60, 0.40, 0.40,
        20
    );

    public boolean isCreative() {
        return fuelUsage == 0;
    }
}
