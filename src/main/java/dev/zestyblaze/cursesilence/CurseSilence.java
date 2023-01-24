package dev.zestyblaze.cursesilence;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

import java.util.List;

public class CurseSilence implements ClientModInitializer {
	public static final String MODID = "cursesilence";

	@Override
	public void onInitializeClient() {
		ModLoadingContext.registerConfig(MODID, ModConfig.Type.CLIENT ,CurseSilenceConfig.CLIENT);
	}

	@Environment(EnvType.CLIENT)
	public static void appendEnchantmentsAndCurses(List<Component> tooltip, ListTag enchantments) {
		for(int i = 0; i < enchantments.size(); ++i) {
			CompoundTag compoundTag = enchantments.getCompound(i);
			Registry.ENCHANTMENT.getOptional(ResourceLocation.tryParse(compoundTag.getString("id"))).ifPresent((e) -> {
				tooltip.add(e.getFullname(compoundTag.getInt("lvl")));
			});
		}
	}
}
