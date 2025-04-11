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

    // A constant key to return when the input list is empty or invalid
    private static final String EMPTY_OR_INVALID_KEY = "EMPTY_AUTH_LIST";

    /**
     * Generates a cache key based on the input parameters.
     * Assumes the first parameter (params[0]) is the List<String> authorities.
     *
     * @param target the target instance
     * @param method the method being called
     * @param params the parameters supplied to the method
     * @return the generated cache key (a String)
     */
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // Check if the first parameter exists and is a List
        if (params.length > 0 && params[0] instanceof List) {
            @SuppressWarnings("unchecked")
            List<String> authorities = (List<String>) params[0];

            if (!CollectionUtils.isEmpty(authorities)) {
                return authorities.stream()
                        .filter(StringUtils::hasText)
                        .distinct()
                        .sorted()
                        .collect(Collectors.joining(","));

            } else {
                // Return the predefined key if the list is empty
                return EMPTY_OR_INVALID_KEY;
            }
        }
        return "INVALID_PARAMS_FOR_KEYGEN";
    }
}