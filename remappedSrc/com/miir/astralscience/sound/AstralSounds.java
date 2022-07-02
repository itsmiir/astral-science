package com.miir.astralscience.sound;

import com.miir.astralscience.AstralScience;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import static com.miir.astralscience.AstralScience.id;

public class AstralSounds {
    public static SoundEvent PHOSPHORIC_JUNGLE_AMBIENCE = new SoundEvent(id("phosphoric_jungle"));
    public static SoundEvent LOW_HUM = new SoundEvent(id("low_hum"));
    public static SoundEvent SUBTERRANEAN_LAKE = new SoundEvent(id("subterranean_lake"));

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, id("phosphoric_jungle"), PHOSPHORIC_JUNGLE_AMBIENCE);
        Registry.register(Registry.SOUND_EVENT, id("low_hum"), LOW_HUM);
        Registry.register(Registry.SOUND_EVENT, id("subterranean_lake"), SUBTERRANEAN_LAKE);
    }
}
