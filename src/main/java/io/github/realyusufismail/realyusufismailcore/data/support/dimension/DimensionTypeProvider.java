package io.github.realyusufismail.realyusufismailcore.data.support.dimension;

import lombok.Setter;
import lombok.val;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Setter
public class DimensionTypeProvider implements DataProvider {
    private final DataGenerator generator;
    private final ExistingFileHelper existingFileHelper;
    private final String modid;

    private boolean isUltrwarm = false;
    private boolean isNatural = false;
    private boolean isPiglinSafe = false;
    private boolean respawnAnchorWorks = false;
    private boolean bedWorks = false;
    private boolean hasRaids = false;
    private boolean hasSkylight = false;
    private boolean hasCeiling = false;
    private int cordinateScale = 0;
    private int ambientLight = 0;
    private int fixedTime = 0;
    private float logicalHeight = 0;
    private String effects = "minecraft:overworld";
    private String infiniburn = "minecraft:infiniburn_overworld";
    private double minY = 0;
    private double height = 0;
    private Map<String, Map<Integer, Integer>> monsterSpawnLightLevel = new HashMap<>();
    private int monsterSpawnBlockLightLimit = 0;

    public DimensionTypeProvider(DataGenerator generator, ExistingFileHelper existingFileHelper,
                                 String modid) {
        this.generator = generator;
        this.existingFileHelper = existingFileHelper;
        this.modid = modid;
    }

    @Override
    public @NotNull CompletableFuture<?> run(@NotNull CachedOutput cachedOutput) {
        val dataProvider = generator.getPackOutput();

        val dimension = new DimensionTypeBuilder(modid, existingFileHelper);

        dimension.setUltrawarm(this.isUltrwarm);
        dimension.setNatural(this.isNatural);
        dimension.setPiglinSafe(this.isPiglinSafe);
        dimension.setRespawnAnchorWorks(this.respawnAnchorWorks);
        dimension.setBedWorks(this.bedWorks);
        dimension.setHasRaids(this.hasRaids);
        dimension.setHasSkylight(this.hasSkylight);
        dimension.setHasCeiling(this.hasCeiling);
        dimension.setCoordinateScale(this.cordinateScale);
        dimension.setAmbientLight(this.ambientLight);
        dimension.setFixedTime(this.fixedTime);
        dimension.setLogicalHeight(this.logicalHeight);
        dimension.setEffects(this.effects);
        dimension.setInfiniburn(this.infiniburn);
        dimension.setMinY(this.minY);
        dimension.setHeight(this.height);
        dimension.setMonsterSpawnLightLevel(this.monsterSpawnLightLevel);
        dimension.setMonsterSpawnBlockLightLimit(this.monsterSpawnBlockLightLimit);
        saveData(cachedOutput, dimension);
        return CompletableFuture.completedFuture(null);
    }

    private Path resolvePath(PackOutput path, String pathOther) {
        return path.getOutputFolder().resolve(pathOther);
    }

    private void saveData(CachedOutput cache, DimensionTypeBuilder jsonObject) {
        DataProvider.saveStable(cache, jsonObject.toJson(), resolvePath(generator.getPackOutput(),
                "data/" + modid + "/dimension_type/" + modid + ".json"));
    }

    @Override
    public @NotNull String getName() {
        return "Dimension Type Provider for " + modid;
    }
}
