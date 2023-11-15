package io.github.realyusufismail.realyusufismailcore.data.support.dimension;

import io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.DimensionBuilder;
import io.github.realyusufismail.realyusufismailcore.data.support.dimension.util.DimensionType;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class DimensionProvider implements DataProvider {
    private final DataGenerator generator;
    private final ExistingFileHelper existingFileHelper;
    private final String modid;

    public List<DimensionBuilder> dimensionBuilders;

    public DimensionProvider(DataGenerator generator, ExistingFileHelper existingFileHelper, String modid) {
        this.generator = generator;
        this.existingFileHelper = existingFileHelper;
        this.modid = modid;
    }

    public abstract void addDimensionBuilders();

    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput) {
        PackOutput dataProvider = generator.getPackOutput();

        for (DimensionBuilder dimensionBuilder : dimensionBuilders) {
            Path path = resolvePath(dataProvider, "data/" + modid + "/dimension/" + dimensionBuilder.getName() + ".json");
            DataProvider.saveStable(cachedOutput, dimensionBuilder.toJson(), path);
        }

        return CompletableFuture.completedFuture(null);
    }

    private Path resolvePath(PackOutput path, String pathOther) {
        return path.getOutputFolder().resolve(pathOther);
    }

    @Override
    public @NotNull String getName() {
        return "Dimension Type Provider for " + modid;
    }
}
