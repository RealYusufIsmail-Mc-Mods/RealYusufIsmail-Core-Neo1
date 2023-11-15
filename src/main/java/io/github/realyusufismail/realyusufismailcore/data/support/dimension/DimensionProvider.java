package io.github.realyusufismail.realyusufismailcore.data.support.dimension;

import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class DimensionProvider implements DataProvider {
    private final DataGenerator generator;
    private final ExistingFileHelper existingFileHelper;
    private final String modid;

    public DimensionProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, String modid) {
        this.generator = generator;
        this.existingFileHelper = existingFileHelper;
        this.modid = modid;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        return null;
    }

    @Override
    public @NotNull String getName() {
        return "Dimension Type Provider for " + modid;
    }
}
