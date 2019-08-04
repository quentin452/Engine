package nullengine.client.asset;

import nullengine.client.asset.reloading.AssetReloadManager;
import nullengine.client.asset.source.AssetSourceManager;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.Validate.notEmpty;

public class EngineAssetManager implements AssetManager {

    private final Map<String, AssetType<?>> registeredTypes = new HashMap<>();

    private final AssetSourceManager sourceManager = new AssetSourceManagerImpl();
    private final AssetReloadManagerImpl reloadManager = new AssetReloadManagerImpl();

    @Override
    public <T> AssetType<T> register(@Nonnull Class<T> assetClass, @Nonnull AssetProvider<T> provider) {
        return register(assetClass, assetClass.getSimpleName(), provider);
    }

    @Override
    public <T> AssetType<T> register(@Nonnull Class<T> assetClass, @Nonnull String name, @Nonnull AssetProvider<T> provider) {
        requireNonNull(assetClass);
        notEmpty(name);
        requireNonNull(provider);

        if (registeredTypes.containsKey(name)) {
            throw new IllegalArgumentException(String.format("AssetType %s has been registered.", name));
        }

        AssetType<T> type = new AssetType<>(assetClass, name, provider);
        registeredTypes.put(name, type);
        provider.init(this, type);
        return type;
    }

    @Override
    public Optional<AssetType<?>> getType(String name) {
        return Optional.ofNullable(registeredTypes.get(name));
    }

    @Override
    public boolean hasType(String name) {
        return registeredTypes.containsKey(name);
    }

    @Override
    public Collection<AssetType<?>> getSupportedTypes() {
        return registeredTypes.values();
    }

    @Nonnull
    @Override
    public <T> Asset<T> create(@Nonnull AssetType<T> type, @Nonnull AssetPath path) {
        Asset<T> asset = new Asset<>(type, path);
        type.getProvider().register(asset);
        return asset;
    }

    @Nonnull
    @Override
    public <T> T loadDirect(@Nonnull AssetType<T> type, @Nonnull AssetPath path) {
        return type.getProvider().loadDirect(path);
    }

    @Override
    public AssetSourceManager getSourceManager() {
        return sourceManager;
    }

    @Override
    public AssetReloadManager getReloadManager() {
        return reloadManager;
    }

    @Override
    public void reload() throws InterruptedException {
        reloadManager.reload();
    }

    public void dispose() {
        registeredTypes.values().forEach(type -> type.getProvider().dispose());
        reloadManager.dispose();
    }
}
