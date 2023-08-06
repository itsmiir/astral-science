package com.miir.astralscience;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class Prelaunch implements PreLaunchEntrypoint {
    public static final Logger LOGGER = LogManager.getLogger("Astral Science");
    //    fun
        public static final String[] STARTUP_MESSAGES = new String[]{
            "!",
            "#buffinhale",
            ":pineapple:",
            ":tiny_potato:",
            "Improved!",
            "Certified!",
            "Guaranteed!",
            "Assured!",
            "Ensured!",
            "Insured!",
            "(with a dark side :3)",
            "Not an attack surface!",
            "You will not go to space today!",
            "Powerful rocks!",
            "Powerful flowers!",
            "Powerful stars!",
            "Better, Higher, Faster!",
            "Did I mention the recorder?",
            "Get your snacks!",
            "Great atmosphere!",
            "I'm sorry, I can't do that for you!",
            "In memory of Hardened Clay!",
            "In memory of Zombie Pigmen!",
            "It was me all along!",
            "It's the crust!",
            "Lots of open space!",
            "Made with Fabric!",
            "Nice!",
            "Ribbit!",
            "Now with more flowers!",
            "...",
            "Doth not a lolcow bleed?",
            "Tensors, what are they good for?",
            "Bring the pain!",
            "miir wuz here",
            "Now I'm mad!",
            "",
            "The G stands for Galactic!",
            "Only a fraction of my true power!",
            "Over 4 confirmed downloads!",
            "Subscribe to my podcast!",
            "Follow my Twitter!",
            "Subscribe to my subreddit!",
            "Join my discord!",
            "The Final Frontier!",
            "This was a triumph!",
            "Time to go all out!",
            "To infinity and beyond!",
            "Welcome back.",
            "Welcome to the disco!",
            "Yare yare daze...",
            "Yeah, that tracks!",
            "Zzz...",
            "if this is a crash report, i didn't do it!"
    };

    @Override
    public void onPreLaunch() {
        MixinExtrasBootstrap.init();
        LOGGER.info(STARTUP_MESSAGES[new Random().nextInt(STARTUP_MESSAGES.length)]);

    }
}
