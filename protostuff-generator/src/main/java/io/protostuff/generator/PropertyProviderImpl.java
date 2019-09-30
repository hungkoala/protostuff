package io.protostuff.generator;

import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Custom property provider for proto tree nodes.
 *
 * @author Kostiantyn Shchepanovskyi
 */
public class PropertyProviderImpl implements PropertyProvider {

    private final Map<String, Function<?, Object>> propertyProviders = new HashMap<>();

    @Override
    public boolean hasProperty(String propertyName) {
        return propertyProviders.containsKey(propertyName);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getProperty(Object object, String propertyName) {
        Function<Object, Object> provider = (Function<Object, Object>) propertyProviders.get(propertyName);
        Preconditions.checkNotNull(provider,
                "Cannot find property '%s' for %s",
                propertyName, object);
        return provider.apply(object);
    }

    @Override
    public void register(String property, Function<?, Object> function) {
        propertyProviders.put(property, function);
    }

}
