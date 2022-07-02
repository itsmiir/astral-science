package com.miir.astralscience.util.logging;

import net.minecraft.util.logging.LoggerPrintStream;
import org.jetbrains.annotations.Nullable;

import java.io.OutputStream;

public abstract class AstralScienceLoggerPrintStream extends LoggerPrintStream {

    public AstralScienceLoggerPrintStream(String name, OutputStream out) {
        super(name, out);
    }

    @Override
    protected void log(@Nullable String message) {
        LOGGER.info("[Astral Science]: {}", message);
    }
}
