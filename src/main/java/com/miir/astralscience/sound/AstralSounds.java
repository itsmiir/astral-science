package com.miir.astralscience.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;

import static com.miir.astralscience.AstralScience.id;

public class AstralSounds {
    public static SoundEvent PHOSPHORIC_JUNGLE_AMBIENCE = SoundEvent.of(id("phosphoric_jungle"));
    public static SoundEvent LOW_HUM = SoundEvent.of(id("low_hum"));
    public static SoundEvent SUBTERRANEAN_LAKE = SoundEvent.of(id("subterranean_lake"));

    public static void register() {
        Registry.register(Registries.SOUND_EVENT, id("phosphoric_jungle"), PHOSPHORIC_JUNGLE_AMBIENCE);
        Registry.register(Registries.SOUND_EVENT, id("low_hum"), LOW_HUM);
        Registry.register(Registries.SOUND_EVENT, id("subterranean_lake"), SUBTERRANEAN_LAKE);
    }
}
