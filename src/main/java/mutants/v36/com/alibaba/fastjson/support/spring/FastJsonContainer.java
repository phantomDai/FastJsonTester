package mutants.v36.com.alibaba.fastjson.support.spring;

import mutants.v36.com.alibaba.fastjson.support.spring.PropertyPreFilters;

/**
 * @author yanquanyu
 * @author liuming
 */
public class FastJsonContainer {
    private Object value;
    private PropertyPreFilters filters;

    FastJsonContainer(Object body){
        this.value = body;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public PropertyPreFilters getFilters() {
        return this.filters;
    }

    public void setFilters(PropertyPreFilters filters) {
        this.filters = filters;
    }
}
