package mutants.v40.com.alibaba.fastjson.support.spring;

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
