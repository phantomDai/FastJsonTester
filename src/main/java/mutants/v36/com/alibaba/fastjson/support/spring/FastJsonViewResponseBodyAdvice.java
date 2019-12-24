package mutants.v36.com.alibaba.fastjson.support.spring;

import mutants.v36.com.alibaba.fastjson.support.spring.FastJsonContainer;
import mutants.v36.com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import mutants.v36.com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import mutants.v36.com.alibaba.fastjson.support.spring.PropertyPreFilters;
import mutants.v36.com.alibaba.fastjson.support.spring.annotation.FastJsonFilter;
import mutants.v36.com.alibaba.fastjson.support.spring.annotation.FastJsonView;
import mutants.v36.com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import mutants.v36.com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * A convenient base class for {@code ResponseBodyAdvice} implementations
 * that customize the response before JSON serialization with {@link FastJsonHttpMessageConverter4}'s concrete
 * subclasses.
 * <p>
 *
 * @author yanquanyu
 * @author liuming
 */
@Order
@ControllerAdvice
public class FastJsonViewResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return FastJsonHttpMessageConverter.class.isAssignableFrom(converterType) && returnType.hasMethodAnnotation(FastJsonView.class);
    }

    public mutants.v36.com.alibaba.fastjson.support.spring.FastJsonContainer beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        mutants.v36.com.alibaba.fastjson.support.spring.FastJsonContainer container = getOrCreateContainer(body);
        beforeBodyWriteInternal(container, selectedContentType, returnType, request, response);
        return container;
    }

    private mutants.v36.com.alibaba.fastjson.support.spring.FastJsonContainer getOrCreateContainer(Object body) {
        return (body instanceof mutants.v36.com.alibaba.fastjson.support.spring.FastJsonContainer ? (mutants.v36.com.alibaba.fastjson.support.spring.FastJsonContainer) body : new mutants.v36.com.alibaba.fastjson.support.spring.FastJsonContainer(body));

    }

    protected void beforeBodyWriteInternal(FastJsonContainer container, MediaType contentType,
                                           MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {
        FastJsonView annotation = returnType.getMethodAnnotation(FastJsonView.class);

        FastJsonFilter[] include = annotation.include();
        FastJsonFilter[] exclude = annotation.exclude();
        mutants.v36.com.alibaba.fastjson.support.spring.PropertyPreFilters filters = new PropertyPreFilters();
        for (FastJsonFilter item : include) {
            filters.addFilter(item.clazz(),item.props());
        }
        for (FastJsonFilter item : exclude) {
            filters.addFilter(item.clazz()).addExcludes(item.props());
        }
        container.setFilters(filters);
    }
}
