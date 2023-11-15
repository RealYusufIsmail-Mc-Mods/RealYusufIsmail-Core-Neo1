package io.github.realyusufismail.realyusufismailcore.data.support.dimension;

import io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.DimensionBuilder;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class DimensionProvider implements DataProvider {
    private final DataGenerator generator;
    private final String modId;

    public List<DimensionBuilder> dimensionBuilders;

    public DimensionProvider(DataGenerator generator, String modId) {
        this.generator = generator;
        this.modId = modId;
    }

    public abstract void addDimensionBuilders();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        PackOutput dataProvider = generator.getPackOutput();

        for (DimensionBuilder dimensionBuilder : dimensionBuilders) {
            Path path = resolvePath(dataProvider, "data/" + modId + "/dimension/" + dimensionBuilder.getName() + ".json");
            DataProvider.saveStable(cachedOutput, dimensionBuilder.toJson(), path);
        }

        return CompletableFuture.completedFuture(null);
    }

    private Path resolvePath(PackOutput path, String pathOther) {
        return path.getOutputFolder().resolve(pathOther);
    }

    @Override
    public @NotNull String getName() {
        return "Dimension Type Provider for " + modId;
    }
}
