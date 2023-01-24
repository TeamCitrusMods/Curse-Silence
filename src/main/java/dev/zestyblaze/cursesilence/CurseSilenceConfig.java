package dev.zestyblaze.cursesilence;

import net.minecraftforge.common.ForgeConfigSpec;

public class CurseSilenceConfig {
    public static final ForgeConfigSpec CLIENT;

    public static final ForgeConfigSpec.EnumValue<CurseType> CURSE_TYPE;

    static {
        final var client = new ForgeConfigSpec.Builder();

        client.comment("Mod Config Options").push("Client");
        CURSE_TYPE = client.comment("How should the mod display curses?").defineEnum("curseType", CurseType.HINT);
        client.pop();

        CLIENT = client.build();
    }

    public enum CurseType {
        FULL, HINT, LEVEL
    }
}
