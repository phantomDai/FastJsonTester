package mutants.v36.com.alibaba.fastjson.util;

import java.security.PrivilegedAction;
import java.util.HashMap;
import java.util.Map;

import mutants.v36.com.alibaba.fastjson.JSON;
import mutants.v36.com.alibaba.fastjson.JSONArray;
import mutants.v36.com.alibaba.fastjson.JSONAware;
import mutants.v36.com.alibaba.fastjson.JSONException;
import mutants.v36.com.alibaba.fastjson.JSONObject;
import mutants.v36.com.alibaba.fastjson.JSONPath;
import mutants.v36.com.alibaba.fastjson.JSONPathException;
import mutants.v36.com.alibaba.fastjson.JSONReader;
import mutants.v36.com.alibaba.fastjson.JSONStreamAware;
import mutants.v36.com.alibaba.fastjson.JSONWriter;
import mutants.v36.com.alibaba.fastjson.TypeReference;
import mutants.v36.com.alibaba.fastjson.parser.DefaultJSONParser;
import mutants.v36.com.alibaba.fastjson.parser.Feature;
import mutants.v36.com.alibaba.fastjson.parser.JSONLexer;
import mutants.v36.com.alibaba.fastjson.parser.JSONLexerBase;
import mutants.v36.com.alibaba.fastjson.parser.JSONReaderScanner;
import mutants.v36.com.alibaba.fastjson.parser.JSONScanner;
import mutants.v36.com.alibaba.fastjson.parser.JSONToken;
import mutants.v36.com.alibaba.fastjson.parser.ParseContext;
import mutants.v36.com.alibaba.fastjson.parser.ParserConfig;
import mutants.v36.com.alibaba.fastjson.parser.SymbolTable;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.AutowiredObjectDeserializer;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.DefaultFieldDeserializer;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ExtraProcessable;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import mutants.v36.com.alibaba.fastjson.serializer.AfterFilter;
import mutants.v36.com.alibaba.fastjson.serializer.BeanContext;
import mutants.v36.com.alibaba.fastjson.serializer.BeforeFilter;
import mutants.v36.com.alibaba.fastjson.serializer.ContextObjectSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.ContextValueFilter;
import mutants.v36.com.alibaba.fastjson.serializer.JSONSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.JavaBeanSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.LabelFilter;
import mutants.v36.com.alibaba.fastjson.serializer.Labels;
import mutants.v36.com.alibaba.fastjson.serializer.NameFilter;
import mutants.v36.com.alibaba.fastjson.serializer.ObjectSerializer;
import mutants.v36.com.alibaba.fastjson.serializer.PropertyFilter;
import mutants.v36.com.alibaba.fastjson.serializer.PropertyPreFilter;
import mutants.v36.com.alibaba.fastjson.serializer.SerialContext;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeBeanInfo;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeConfig;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeFilter;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeFilterable;
import mutants.v36.com.alibaba.fastjson.serializer.SerializeWriter;
import mutants.v36.com.alibaba.fastjson.serializer.SerializerFeature;
import mutants.v36.com.alibaba.fastjson.serializer.ValueFilter;
import mutants.v36.com.alibaba.fastjson.util.*;
import mutants.v36.com.alibaba.fastjson.util.FieldInfo;
import mutants.v36.com.alibaba.fastjson.util.IOUtils;
import mutants.v36.com.alibaba.fastjson.util.JavaBeanInfo;
import mutants.v36.com.alibaba.fastjson.util.TypeUtils;
import mutants.v36.com.alibaba.fastjson.util.*;
import mutants.v36.com.alibaba.fastjson.util.FieldInfo;
import mutants.v36.com.alibaba.fastjson.util.IOUtils;
import mutants.v36.com.alibaba.fastjson.util.JavaBeanInfo;
import mutants.v36.com.alibaba.fastjson.util.TypeUtils;

public class ASMClassLoader extends ClassLoader {

    private static java.security.ProtectionDomain DOMAIN;
    
    private static Map<String, Class<?>> classMapping = new HashMap<String, Class<?>>();

    static {
        DOMAIN = (java.security.ProtectionDomain) java.security.AccessController.doPrivileged(new PrivilegedAction<Object>() {

            public Object run() {
                return ASMClassLoader.class.getProtectionDomain();
            }
        });
        
        Class<?>[] jsonClasses = new Class<?>[] {JSON.class,
            JSONObject.class,
            JSONArray.class,
            JSONPath.class,
            JSONAware.class,
            JSONException.class,
            JSONPathException.class,
            JSONReader.class,
            JSONStreamAware.class,
            JSONWriter.class,
            TypeReference.class,
                    
            FieldInfo.class,
            TypeUtils.class,
            IOUtils.class,
            IdentityHashMap.class,
            ParameterizedTypeImpl.class,
            JavaBeanInfo.class,
                    
            ObjectSerializer.class,
            JavaBeanSerializer.class,
            SerializeFilterable.class,
            SerializeBeanInfo.class,
            JSONSerializer.class,
            SerializeWriter.class,
            SerializeFilter.class,
            Labels.class,
            LabelFilter.class,
            ContextValueFilter.class,
            AfterFilter.class,
            BeforeFilter.class,
            NameFilter.class,
            PropertyFilter.class,
            PropertyPreFilter.class,
            ValueFilter.class,
            SerializerFeature.class,
            ContextObjectSerializer.class,
            SerialContext.class,
            SerializeConfig.class,
                    
            JavaBeanDeserializer.class,
            ParserConfig.class,
            DefaultJSONParser.class,
            JSONLexer.class,
            JSONLexerBase.class,
            ParseContext.class,
            JSONToken.class,
            SymbolTable.class,
            Feature.class,
            JSONScanner.class,
            JSONReaderScanner.class,
                    
            AutowiredObjectDeserializer.class,
            ObjectDeserializer.class,
            ExtraProcessor.class,
            ExtraProcessable.class,
            ExtraTypeProvider.class,
            BeanContext.class,
            FieldDeserializer.class,
            DefaultFieldDeserializer.class,
        };
        
        for (Class<?> clazz : jsonClasses) {
            classMapping.put(clazz.getName(), clazz);
        }
    }
    
    public ASMClassLoader(){
        super(getParentClassLoader());
    }

    public ASMClassLoader(ClassLoader parent){
        super (parent);
    }

    static ClassLoader getParentClassLoader() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader != null) {
            try {
                contextClassLoader.loadClass(JSON.class.getName());
                return contextClassLoader;
            } catch (ClassNotFoundException e) {
                // skip
            }
        }
        return JSON.class.getClassLoader();
    }

    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class<?> mappingClass = classMapping.get(name);
        if (mappingClass != null) {
            return mappingClass;
        }
        
        try {
            return super.loadClass(name, resolve);
        } catch (ClassNotFoundException e) {
            throw e;
        }
    }

    public Class<?> defineClassPublic(String name, byte[] b, int off, int len) throws ClassFormatError {
        Class<?> clazz = defineClass(name, b, off, len, DOMAIN);

        return clazz;
    }

    public boolean isExternalClass(Class<?> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();

        if (classLoader == null) {
            return false;
        }

        ClassLoader current = this;
        while (current != null) {
            if (current == classLoader) {
                return false;
            }

            current = current.getParent();
        }

        return true;
    }

}
