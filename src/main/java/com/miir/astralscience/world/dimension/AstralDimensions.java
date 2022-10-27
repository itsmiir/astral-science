package com.miir.astralscience.world.dimension;

import com.miir.astralscience.AstralScience;
import com.miir.astralscience.tag.AstralTags;
import com.miir.astralscience.util.AstralText;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.math.*;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;

import java.util.Random;

public class AstralDimensions {
    /**
     * ranked in progression order, the planets are
     * -overworld
     * -sylene
     * -aere
     * -mekemek
     * -cyri (moon of phosphor)
     * -phosphor
     * -hydes
     * -iris
     * -zu
     * -ouran/psidon
     * -
     */
    public static final RegistryKey<World> HALYUS = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("halyus")); // star

    public static final RegistryKey<World> ERMIS = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("ermis"));

    public static final RegistryKey<World> PHOSPHOR = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("phosphor"));
    public static final RegistryKey<World> CYRI = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("cyri"));

    public static final RegistryKey<World> OVERWORLD_ORBIT = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("overworld" + AstralScience.ORBIT_SUFFIX));
    public static final RegistryKey<World> SYLENE = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("sylene"));

    public static final RegistryKey<World> AERE = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("aere"));
    public static final RegistryKey<World> MEKEMEK = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("mekemek"));

    public static final RegistryKey<World> ZU = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("zu"));
    public static final RegistryKey<World> IRIS = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("iris"));
    public static final RegistryKey<World> HYDES = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("hydes"));

    public static final RegistryKey<World> PSIDON = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("psidon"));
    public static final RegistryKey<World> OURAN = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("ouran")); // binary planetary system; not moon

    public static final RegistryKey<World> KRONOS = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("kronos")); // cool and exotic
    public static final RegistryKey<World> OMEIA = RegistryKey.of(Registry.WORLD_KEY, AstralScience.id("omeia"));
    public static final RegistryKey<World>[] VISITABLE_DIMENSIONS = new RegistryKey[]{
            HALYUS,
            ERMIS,
            PHOSPHOR,
            CYRI,
            World.OVERWORLD,
            SYLENE,
            AERE,
            MEKEMEK,
            ZU,
            IRIS,
            HYDES,
            PSIDON,
            OURAN,
            KRONOS,
            OMEIA
    };

    public static RegistryKey<World> orbit(RegistryKey<World> key) {
        return RegistryKey.of(Registry.WORLD_KEY, AstralScience.id(key.getValue().getPath()+AstralScience.ORBIT_SUFFIX));
    }
    public static boolean isOrbit(World world) {
        return isOrbit(world.getRegistryKey());
    }
    public static boolean isOrbit(RegistryKey<World> world) {
        return (world.getValue().getPath().contains(AstralScience.ORBIT_SUFFIX) && isAstralDimension(world));
    }

    public static double gravityCalc(double multiplier) {
        if (multiplier == 1.0) {
            return 1;
        }
        return (2E-5 * Math.pow(multiplier, 4) +
                0.0015 * Math.pow(multiplier, 3) -
                0.0479 * Math.pow(multiplier, 2) +
                1.0053 * multiplier + 0.2765);
    }

    public static boolean isAstralDimension(World world) {
        return isAstralDimension(world.getRegistryKey());
    }

    public static boolean isAstralDimension(RegistryKey<World> world) {
        return world.getValue().getNamespace().equals(AstralScience.MOD_ID);
    }

    public static boolean isSuperCold(World world, BlockPos pos) {
        if (world.getBlockState(pos).isIn(AstralTags.DEEP_COLD)) {
            return true;
        } else {
            return !hasAtmosphere(world, false);
        }
    }

    public static boolean hasOrbitalDimension(World world) {
        return ((isAstralDimension(world) && !isOrbit(world)) || world.getRegistryKey().equals(World.OVERWORLD));
    }
    public static boolean hasAtmosphere(World world, boolean getHost) {
        if (isAstralDimension(world)) {
            if (isOrbit(world) && !getHost) {
                return false;
            } else {
                switch (AstralText.deorbitify(world.getRegistryKey().getValue().getPath())) {
                    case "halyus":
                    case "phosphor":
                    case "overworld":
                    case "aere":
                    case "zu":
                    case "kronos":
                    case "ouran":
                    case "psidon":
                    case "cyri":
                    case "omeia":
                        return true;
                    default:
                        return false;
                }
            }
        }
        return true;
    }

    public static boolean canDeepFreeze(LivingEntity livingEntity) {
        if (livingEntity.isSpectator() || livingEntity.getType().isIn(AstralTags.DEEP_FREEZE_IMMUNE) ) {
            return false;
        } else {
            boolean bl = livingEntity.getEquippedStack(EquipmentSlot.HEAD).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && livingEntity.getEquippedStack(EquipmentSlot.CHEST).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && livingEntity.getEquippedStack(EquipmentSlot.LEGS).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES) && livingEntity.getEquippedStack(EquipmentSlot.FEET).isIn(ItemTags.FREEZE_IMMUNE_WEARABLES);
            return !bl;
        }
    }

    public static boolean canHouseStarship(RegistryKey<World> world) {
        return AstralDimensions.isOrbit(world);
    }
    public static boolean canHouseStarship(World world) {
        return canHouseStarship(world.getRegistryKey());
    }

    public static String fromId(String path) {
        return StringUtils.capitalize(path);
    }

    public static boolean isVisibleFromHere(RegistryKey<World> destination, RegistryKey<World> here) {
        if (!isOrbit(here)) return false;
        String origin = AstralText.deorbitify(here.getValue().getPath());
        if (origin.equals(destination.getValue().getPath())) return false;
        switch (destination.getValue().getPath()) {
            case "cyri" -> {return origin.equals("phosphor");}
            case "sylene" -> {return origin.equals("overworld");}
            case "mekemek" -> {return origin.equals("aere");}
            case "iris", "hydes" -> {return origin.equals("zu");}
            case "omeia" -> {return origin.equals("kronos");}
            case "halyus" -> {return false;}

            default -> {return true;}
        }
    }

    public static Vec2f getRelativeDirection(RegistryKey<World> destination, World origin) {
        Vec2f angles = new Vec2f(180, 0);
        float k = 45;
        if (destination.equals(SYLENE)) return angles;
        Random r = new Random(destination.getValue().getPath().hashCode()/2);
        return angles.add(new Vec2f(MathHelper.lerp(r.nextFloat(), 0, 370), MathHelper.lerp(r.nextFloat(), -k, k)));
    }
}
