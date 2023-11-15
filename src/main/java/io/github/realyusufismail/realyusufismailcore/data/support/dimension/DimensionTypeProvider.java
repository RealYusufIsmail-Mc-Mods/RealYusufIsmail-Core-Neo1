package io.github.realyusufismail.realyusufismailcore.data.support.dimension;

import io.github.realyusufismail.realyusufismailcore.data.support.dimension.builder.DimensionTypeBuilder;
import lombok.Setter;
import lombok.val;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Setter
public abstract class DimensionTypeProvider implements DataProvider {
    private final DataGenerator generator;
    private final ExistingFileHelper existingFileHelper;
    private final String modId;

    protected List<DimensionTypeBuilder> dimensionTypeBuilders;

    public DimensionTypeProvider(DataGenerator generator, ExistingFileHelper existingFileHelper,
                                 String modId) {
        this.generator = generator;
        this.existingFileHelper = existingFileHelper;
        this.modId = modId;
    }

    abstract protected void addDimensionTypes();

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        val dataProvider = generator.getPackOutput();

        for (DimensionTypeBuilder dimensionTypeBuilder : dimensionTypeBuilders) {
            val jsonObject = dimensionTypeBuilder.toJson();
            val path = resolvePath(dataProvider, "data/" + modId + "/dimension_type/" + dimensionTypeBuilder.getName() + ".json");
            DataProvider.saveStable(cachedOutput, jsonObject, path);
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
