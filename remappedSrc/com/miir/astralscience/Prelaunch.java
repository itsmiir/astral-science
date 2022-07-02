package com.miir.astralscience;

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
            "Ad Astra!",
            "Allente physe! Kyter elef! Figma caphta! Plexes veris!",
            "Arguably improved!",
            "Certified!",
            "Guaranteed!",
            "Assured!",
            "Ensured!",
            "Insured!",
            "(with a dark side :3)",
            "Not an attack surface!",
            "Powerful wands!",
            "Powerful rocks!",
            "Powerful flowers!",
            "Powerful stars!",
            "join my discord!!!1!",
            "Better, Higher, Faster!",
            "Did I mention the recorder?",
            "Get your snacks!",
            "Great atmosphere!",
            "I'm sorry, I can't do that for you!",
            "In memory of Hardened Clay!",
            "In memory of Zombie Pigmen!",
            "It was me all along!",
            "It's the crust!",
            "Let's get right into the news!",
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
            "Omeia is an ellipse?!",
            "Only a fraction of my true power!",
            "Over 4 confirmed downloads!",
            "Subscribe to my podcast!",
            "The Final Frontier!",
            "The runes mean something!",
            "This was a triumph!",
            "Time to go all out!",
            "To infinity and beyond!",
            "Welcome back.",
            "Welcome to the disco!",
            "Yare yare daze...",
            "Yeah, that tracks!",
            "Zzz...",
            "gorg",
            "if this is a crash report, i didn't do it!"
    };

    @Override
    public void onPreLaunch() {
        LOGGER.info("Astral Science: " + STARTUP_MESSAGES[new Random().nextInt(STARTUP_MESSAGES.length)]);

    }
}
