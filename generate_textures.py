"""
Gera texturas placeholder 16x16 para os jetpacks.
Execute: python generate_textures.py
Depois substitua pelos PNGs finais desenhados no Aseprite/Pixilart.

Requer: pip install Pillow
"""
from PIL import Image
import os

ITEM_DIR  = "src/main/resources/assets/jetpackexpanded/textures/item"
ARMOR_DIR = "src/main/resources/assets/jetpackexpanded/textures/models/armor"

os.makedirs(ITEM_DIR, exist_ok=True)
os.makedirs(ARMOR_DIR, exist_ok=True)

# Paleta de cores por tier (RGBA)
TIERS = {
    "mk1":      (160, 160, 160, 255),   # cinza ferro
    "mk2":      (255, 200, 50,  255),   # dourado
    "mk3":      (80,  220, 230, 255),   # azul diamante
    "mk4":      (60,  50,  60,  255),   # cinza escuro netherite
    "creative": (200, 80,  255, 255),   # roxo/lilás
}

def make_jetpack_icon(color, path):
    """Ícone 16x16 simples: corpo centralizado + detalhes."""
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    px  = img.load()
    r, g, b, a = color
    dark = (max(0, r - 50), max(0, g - 50), max(0, b - 50), a)
    light = (min(255, r + 60), min(255, g + 60), min(255, b + 60), a)

    # Corpo principal (7x9 centralizado)
    for y in range(3, 12):
        for x in range(4, 12):
            px[x, y] = color

    # Detalhes escuros (borda)
    for x in range(4, 12): px[x, 3]  = dark
    for x in range(4, 12): px[x, 11] = dark
    for y in range(3, 12): px[4, y]  = dark
    for y in range(3, 12): px[11, y] = dark

    # Brilho (canto superior esquerdo)
    for x in range(5, 8): px[x, 4] = light

    # "Propulsores" embaixo
    px[5,  12] = dark
    px[6,  12] = color
    px[7,  12] = color
    px[8,  12] = color
    px[9,  12] = dark

    # Chama dos propulsores
    px[6,  13] = (255, 150, 0, 220)
    px[7,  13] = (255, 200, 0, 255)
    px[8,  13] = (255, 150, 0, 220)
    px[7,  14] = (255, 100, 0, 180)

    img.save(path)
    print(f"  ✓ {path}")

def make_armor_layer(color, path):
    """Textura 64x32 para armor layer_1 (só chestplate area usada)."""
    img = Image.new("RGBA", (64, 32), (0, 0, 0, 0))
    px  = img.load()
    r, g, b, a = color

    # Área do chestplate na textura de armor padrão: x=16..47, y=16..31
    for y in range(16, 32):
        for x in range(16, 48):
            px[x, y] = color

    # Borda escura
    dark = (max(0, r - 60), max(0, g - 60), max(0, b - 60), a)
    for x in range(16, 48):
        px[x, 16] = dark
        px[x, 31] = dark
    for y in range(16, 32):
        px[16, y] = dark
        px[47, y] = dark

    img.save(path)
    print(f"  ✓ {path}")


print("Gerando texturas placeholder...")

for name, color in TIERS.items():
    make_jetpack_icon(color, f"{ITEM_DIR}/jetpack_{name}.png")
    make_armor_layer(color, f"{ARMOR_DIR}/{name}_layer_1.png")

print("\nPronto! Substitua pelas texturas finais quando tiver o pixel art.")
print("Ferramentas recomendadas: Aseprite (pago) ou Pixilart.com (grátis)")
