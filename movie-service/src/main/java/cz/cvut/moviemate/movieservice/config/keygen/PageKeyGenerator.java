package cz.cvut.moviemate.movieservice.config.keygen;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

public class PageKeyGenerator implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return StringUtils.arrayToDelimitedString(params, ":");
    }
}
