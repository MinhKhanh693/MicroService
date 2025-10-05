package mk.auth.service.util;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

@Component("authoritiesKeyGenerator")
public class AuthoritiesKeyGenerator implements KeyGenerator {

    // A constant key to return when the input list is null, empty, or effectively empty after filtering.
    private static final String EMPTY_OR_INVALID_LIST_KEY = "EMPTY_OR_INVALID_AUTH_LIST";
    // A constant key to return when the input parameters are invalid for key generation.
    private static final String INVALID_PARAMS_KEY = "INVALID_PARAMS_FOR_KEYGEN";

    /**
     * Generates a cache key based on the input parameters.
     * Assumes the first parameter (params[0]) is the List<String> authorities.
     *
     * @param target the target instance
     * @param method the method being called
     * @param params the parameters supplied to the method (expects List<String> at index 0)
     * @return the generated cache key (a String) or a predefined constant string for invalid/empty inputs.
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // Guard clause: Validate parameters — expect at least one parameter, which must be a List.
        if (params.length == 0 || !(params[0] instanceof List)) {
            return INVALID_PARAMS_KEY;
        }

        // Suppress warnings necessary due to KeyGenerator interface signature using Object…
        @SuppressWarnings("unchecked")
        List<String> authorities = (List<String>) params[0];

        // Delegate actual key generation to a separate method
        return generateKeyFromAuthorities(authorities);
    }

    /**
     * Generates a cache key string from a list of authorities.
     * Filters out blank strings, sorts them, ensures uniqueness, and joins them with a comma.
     * Returns a predefined constant if the list is null, empty, or contains only blank strings.
     *
     * @param authorities The list of authorities.
     * @return A generated key string, or a constant for empty/invalid lists.
     */
    private String generateKeyFromAuthorities(List<String> authorities) {
        if (CollectionUtils.isEmpty(authorities)) {
            return EMPTY_OR_INVALID_LIST_KEY;
        }

        String key = authorities.stream()
                .filter(StringUtils::hasText) // Ensure elements are not null or blank
                .distinct()                 // Keep only unique authorities
                .sorted()                   // Sort alphabetically for consistency
                .collect(Collectors.joining(",")); // Join into a single comma-separated string

        // If after filtering, the resulting key is empty (a list contained only null/blank strings),
        // return the predefined constant. Otherwise, return the generated key.
        return key.isEmpty() ? EMPTY_OR_INVALID_LIST_KEY : key;
    }
}