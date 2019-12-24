/*
 * Copyright 1999-2019 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mutants.v36.com.alibaba.fastjson.parser;

import static mutants.v36.com.alibaba.fastjson.parser.JSONLexer.EOI;
import static mutants.v36.com.alibaba.fastjson.parser.JSONToken.*;

import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import mutants.v36.com.alibaba.fastjson.*;
import mutants.v36.com.alibaba.fastjson.parser.Feature;
import mutants.v36.com.alibaba.fastjson.parser.JSONLexer;
import mutants.v36.com.alibaba.fastjson.parser.JSONLexerBase;
import mutants.v36.com.alibaba.fastjson.parser.JSONScanner;
import mutants.v36.com.alibaba.fastjson.parser.JSONToken;
import mutants.v36.com.alibaba.fastjson.parser.ParseContext;
import mutants.v36.com.alibaba.fastjson.parser.ParserConfig;
import mutants.v36.com.alibaba.fastjson.parser.SymbolTable;
import mutants.v36.com.alibaba.fastjson.parser.deserializer.*;
import mutants.v36.com.alibaba.fastjson.serializer.*;
import mutants.v36.com.alibaba.fastjson.util.TypeUtils;
import mutants.v36.com.alibaba.fastjson.parser.Feature;
import mutants.v36.com.alibaba.fastjson.parser.JSONLexer;
import mutants.v36.com.alibaba.fastjson.parser.JSONLexerBase;
import mutants.v36.com.alibaba.fastjson.parser.JSONScanner;
import mutants.v36.com.alibaba.fastjson.parser.JSONToken;
import mutants.v36.com.alibaba.fastjson.parser.ParseContext;
import mutants.v36.com.alibaba.fastjson.parser.ParserConfig;
import mutants.v36.com.alibaba.fastjson.parser.SymbolTable;

/**
 * @author wenshao[szujobs@hotmail.com]
 */
public class DefaultJSONParser implements Closeable {

    public final Object                input;
    public final mutants.v36.com.alibaba.fastjson.parser.SymbolTable symbolTable;
    protected mutants.v36.com.alibaba.fastjson.parser.ParserConfig config;

    private final static Set<Class<?>> primitiveClasses   = new HashSet<Class<?>>();

    private String                     dateFormatPattern  = JSON.DEFFAULT_DATE_FORMAT;
    private DateFormat                 dateFormat;

    public final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer;

    protected mutants.v36.com.alibaba.fastjson.parser.ParseContext context;

    private mutants.v36.com.alibaba.fastjson.parser.ParseContext[]             contextArray;
    private int                        contextArrayIndex  = 0;

    private List<ResolveTask>          resolveTaskList;

    public final static int            NONE               = 0;
    public final static int            NeedToResolve      = 1;
    public final static int            TypeNameRedirect   = 2;

    public int                         resolveStatus      = NONE;

    private List<ExtraTypeProvider>    extraTypeProviders = null;
    private List<ExtraProcessor>       extraProcessors    = null;
    protected FieldTypeResolver        fieldTypeResolver  = null;

    private int                        objectKeyLevel     = 0;

    private boolean                    autoTypeEnable;
    private String[]                   autoTypeAccept     = null;

    protected transient BeanContext    lastBeanContext;

    static {
        Class<?>[] classes = new Class[] {
                boolean.class,
                byte.class,
                short.class,
                int.class,
                long.class,
                float.class,
                double.class,

                Boolean.class,
                Byte.class,
                Short.class,
                Integer.class,
                Long.class,
                Float.class,
                Double.class,

                BigInteger.class,
                BigDecimal.class,
                String.class
        };

        for (Class<?> clazz : classes) {
            primitiveClasses.add(clazz);
        }
    }

    public String getDateFomartPattern() {
        return dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat(dateFormatPattern, lexer.getLocale());
            dateFormat.setTimeZone(lexer.getTimeZone());
        }
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormatPattern = dateFormat;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat) {
        this.dateFormat = dateFormat;
    }

    public DefaultJSONParser(String input){
        this(input, mutants.v36.com.alibaba.fastjson.parser.ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(final String input, final mutants.v36.com.alibaba.fastjson.parser.ParserConfig config){
        this(input, new mutants.v36.com.alibaba.fastjson.parser.JSONScanner(input, JSON.DEFAULT_PARSER_FEATURE), config);
    }

    public DefaultJSONParser(final String input, final mutants.v36.com.alibaba.fastjson.parser.ParserConfig config, int features){
        this(input, new mutants.v36.com.alibaba.fastjson.parser.JSONScanner(input, features), config);
    }

    public DefaultJSONParser(final char[] input, int length, final mutants.v36.com.alibaba.fastjson.parser.ParserConfig config, int features){
        this(input, new mutants.v36.com.alibaba.fastjson.parser.JSONScanner(input, length, features), config);
    }

    public DefaultJSONParser(final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer){
        this(lexer, mutants.v36.com.alibaba.fastjson.parser.ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer, final mutants.v36.com.alibaba.fastjson.parser.ParserConfig config){
        this(null, lexer, config);
    }

    public DefaultJSONParser(final Object input, final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer, final mutants.v36.com.alibaba.fastjson.parser.ParserConfig config){
        this.lexer = lexer;
        this.input = input;
        this.config = config;
        this.symbolTable = config.symbolTable;

        int ch = lexer.getCurrent();
        if (ch == '{') {
            lexer.next();
            ((mutants.v36.com.alibaba.fastjson.parser.JSONLexerBase) lexer).token = mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACE;
        } else if (ch == '[') {
            lexer.next();
            ((JSONLexerBase) lexer).token = mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACKET;
        } else {
            lexer.nextToken(); // prime the pump
        }
    }

    public SymbolTable getSymbolTable() {
        return symbolTable;
    }

    public String getInput() {
        if (input instanceof char[]) {
            return new String((char[]) input);
        }
        return input.toString();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final Object parseObject(final Map object, Object fieldName) {
        final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer = this.lexer;

        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.NULL) {
            lexer.nextToken();
            return null;
        }

        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
            lexer.nextToken();
            return object;
        }

        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING && lexer.stringVal().length() == 0) {
            lexer.nextToken();
            return object;
        }

        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACE && lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
            throw new JSONException("syntax error, expect {, actual " + lexer.tokenName() + ", " + lexer.info());
        }

        mutants.v36.com.alibaba.fastjson.parser.ParseContext context = this.context;
        try {
            boolean isJsonObjectMap = object instanceof JSONObject;
            Map map = isJsonObjectMap ? ((JSONObject) object).getInnerMap() : object;

            boolean setContextFlag = false;
            for (;;) {
                lexer.skipWhitespace();
                char ch = lexer.getCurrent();
                if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas)) {
                    while (ch == ',') {
                        lexer.next();
                        lexer.skipWhitespace();
                        ch = lexer.getCurrent();
                    }
                }

                boolean isObjectKey = false;
                Object key;
                if (ch == '"') {
                    key = lexer.scanSymbol(symbolTable, '"');
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos() + ", name " + key);
                    }
                } else if (ch == '}') {
                    lexer.next();
                    lexer.resetStringPosition();
                    lexer.nextToken();

                    if (!setContextFlag) {
                        if (this.context != null && fieldName == this.context.fieldName && object == this.context.object) {
                            context = this.context;
                        } else {
                            mutants.v36.com.alibaba.fastjson.parser.ParseContext contextR = setContext(object, fieldName);
                            if (context == null) {
                                context = contextR;
                            }
                            setContextFlag = true;
                        }
                    }

                    return object;
                } else if (ch == '\'') {
                    if (!lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowSingleQuotes)) {
                        throw new JSONException("syntax error");
                    }

                    key = lexer.scanSymbol(symbolTable, '\'');
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos());
                    }
                } else if (ch == EOI) {
                    throw new JSONException("syntax error");
                } else if (ch == ',') {
                    throw new JSONException("syntax error");
                } else if ((ch >= '0' && ch <= '9') || ch == '-') {
                    lexer.resetStringPosition();
                    lexer.scanNumber();
                    try {
                        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT) {
                            key = lexer.integerValue();
                        } else {
                            key = lexer.decimalValue(true);
                        }
                        if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.NonStringKeyAsString) || isJsonObjectMap) {
                            key = key.toString();
                        }
                    } catch (NumberFormatException e) {
                        throw new JSONException("parse number key error" + lexer.info());
                    }
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("parse number key error" + lexer.info());
                    }
                } else if (ch == '{' || ch == '[') {
                    if (objectKeyLevel++ > 512) {
                        throw new JSONException("object key level > 512");
                    }
                    lexer.nextToken();
                    key = parse();
                    isObjectKey = true;
                } else {
                    if (!lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowUnQuotedFieldNames)) {
                        throw new JSONException("syntax error");
                    }

                    key = lexer.scanSymbolUnQuoted(symbolTable);
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos() + ", actual " + ch);
                    }
                }

                if (!isObjectKey) {
                    lexer.next();
                    lexer.skipWhitespace();
                }

                ch = lexer.getCurrent();

                lexer.resetStringPosition();

                if (key == JSON.DEFAULT_TYPE_KEY
                        && !lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect)) {
                    String typeName = lexer.scanSymbol(symbolTable, '"');

                    if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.IgnoreAutoType)) {
                        continue;
                    }

                    Class<?> clazz = null;
                    if (object != null
                            && object.getClass().getName().equals(typeName)) {
                        clazz = object.getClass();
                    } else {

                        boolean allDigits = true;
                        for (int i = 0; i < typeName.length(); ++i) {
                            char c = typeName.charAt(i);
                            if (c < '0' || c > '9') {
                                allDigits = false;
                                break;
                            }
                        }

                        if (!allDigits) {
                            clazz = config.checkAutoType(typeName, null, lexer.getFeatures());
                        }
                    }

                    if (clazz == null) {
                        map.put(JSON.DEFAULT_TYPE_KEY, typeName);
                        continue;
                    }

                    lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                        try {
                            Object instance = null;
                            ObjectDeserializer deserializer = this.config.getDeserializer(clazz);
                            if (deserializer instanceof JavaBeanDeserializer) {
                            	instance = TypeUtils.cast(object, clazz, this.config);
                            }

                            if (instance == null) {
                                if (clazz == Cloneable.class) {
                                    instance = new HashMap();
                                } else if ("java.util.Collections$EmptyMap".equals(typeName)) {
                                    instance = Collections.emptyMap();
                                } else if ("java.util.Collections$UnmodifiableMap".equals(typeName)) {
                                    instance = Collections.unmodifiableMap(new HashMap());
                                } else {
                                    instance = clazz.newInstance();
                                }
                            }

                            return instance;
                        } catch (Exception e) {
                            throw new JSONException("create instance error", e);
                        }
                    }

                    this.setResolveStatus(TypeNameRedirect);

                    if (this.context != null
                            && fieldName != null
                            && !(fieldName instanceof Integer)
                            && !(this.context.fieldName instanceof Integer)) {
                        this.popContext();
                    }

                    if (object.size() > 0) {
                        Object newObj = TypeUtils.cast(object, clazz, this.config);
                        this.setResolveStatus(NONE);
                        this.parseObject(newObj);
                        return newObj;
                    }

                    ObjectDeserializer deserializer = config.getDeserializer(clazz);
                    Class deserClass = deserializer.getClass();
                    if (JavaBeanDeserializer.class.isAssignableFrom(deserClass)
                            && deserClass != JavaBeanDeserializer.class
                            && deserClass != ThrowableDeserializer.class) {
                        this.setResolveStatus(NONE);
                    } else if (deserializer instanceof MapDeserializer) {
                        this.setResolveStatus(NONE);
                    }
                    Object obj = deserializer.deserialze(this, clazz, fieldName);
                    return obj;
                }

                if (key == "$ref"
                        && context != null
                        && !lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.DisableSpecialKeyDetect)) {
                    lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING);
                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING) {
                        String ref = lexer.stringVal();
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE);

                        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                            map.put(key, ref);
                            continue;
                        }

                        Object refValue = null;
                        if ("@".equals(ref)) {
                            if (this.context != null) {
                                mutants.v36.com.alibaba.fastjson.parser.ParseContext thisContext = this.context;
                                Object thisObj = thisContext.object;
                                if (thisObj instanceof Object[] || thisObj instanceof Collection<?>) {
                                    refValue = thisObj;
                                } else if (thisContext.parent != null) {
                                    refValue = thisContext.parent.object;
                                }
                            }
                        } else if ("..".equals(ref)) {
                            if (context.object != null) {
                                refValue = context.object;
                            } else {
                                addResolveTask(new ResolveTask(context, ref));
                                setResolveStatus(DefaultJSONParser.NeedToResolve);
                            }
                        } else if ("$".equals(ref)) {
                            mutants.v36.com.alibaba.fastjson.parser.ParseContext rootContext = context;
                            while (rootContext.parent != null) {
                                rootContext = rootContext.parent;
                            }

                            if (rootContext.object != null) {
                                refValue = rootContext.object;
                            } else {
                                addResolveTask(new ResolveTask(rootContext, ref));
                                setResolveStatus(DefaultJSONParser.NeedToResolve);
                            }
                        } else {
                            addResolveTask(new ResolveTask(context, ref));
                            setResolveStatus(DefaultJSONParser.NeedToResolve);
                        }

                        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                            throw new JSONException("syntax error, " + lexer.info());
                        }
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);

                        return refValue;
                    } else {
                        throw new JSONException("illegal ref, " + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(lexer.token()));
                    }
                }

                if (!setContextFlag) {
                    if (this.context != null && fieldName == this.context.fieldName && object == this.context.object) {
                        context = this.context;
                    } else {
                        mutants.v36.com.alibaba.fastjson.parser.ParseContext contextR = setContext(object, fieldName);
                        if (context == null) {
                            context = contextR;
                        }
                        setContextFlag = true;
                    }
                }

                if (object.getClass() == JSONObject.class) {
                    if (key == null) {
                        key = "null";
                    }
                }

                Object value;
                if (ch == '"') {
                    lexer.scanString();
                    String strValue = lexer.stringVal();
                    value = strValue;

                    if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat)) {
                        mutants.v36.com.alibaba.fastjson.parser.JSONScanner iso8601Lexer = new mutants.v36.com.alibaba.fastjson.parser.JSONScanner(strValue);
                        if (iso8601Lexer.scanISO8601DateIfMatch()) {
                            value = iso8601Lexer.getCalendar().getTime();
                        }
                        iso8601Lexer.close();
                    }

                    map.put(key, value);
                } else if (ch >= '0' && ch <= '9' || ch == '-') {
                    lexer.scanNumber();
                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT) {
                        value = lexer.integerValue();
                    } else {
                        value = lexer.decimalValue(lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.UseBigDecimal));
                    }

                    map.put(key, value);
                } else if (ch == '[') {
                    lexer.nextToken();

                    JSONArray list = new JSONArray();

                    final boolean parentIsArray = fieldName != null && fieldName.getClass() == Integer.class;
//                    if (!parentIsArray) {
//                        this.setContext(context);
//                    }
                    if (fieldName == null) {
                        this.setContext(context);
                    }

                    this.parseArray(list, key);

                    if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.UseObjectArray)) {
                        value = list.toArray();
                    } else {
                        value = list;
                    }
                    map.put(key, value);

                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                        lexer.nextToken();
                        return object;
                    } else if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                        continue;
                    } else {
                        throw new JSONException("syntax error");
                    }
                } else if (ch == '{') {
                    lexer.nextToken();

                    final boolean parentIsArray = fieldName != null && fieldName.getClass() == Integer.class;

                    Map input;
                    if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.CustomMapDeserializer)) {
                        MapDeserializer mapDeserializer = (MapDeserializer) config.getDeserializer(Map.class);


                        input = (lexer.getFeatures() & mutants.v36.com.alibaba.fastjson.parser.Feature.OrderedField.mask) != 0
                                ? mapDeserializer.createMap(Map.class, lexer.getFeatures())
                                : mapDeserializer.createMap(Map.class);
                    } else {
                        input = new JSONObject(lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.OrderedField));
                    }
                    mutants.v36.com.alibaba.fastjson.parser.ParseContext ctxLocal = null;

                    if (!parentIsArray) {
                        ctxLocal = setContext(context, input, key);
                    }

                    Object obj = null;
                    boolean objParsed = false;
                    if (fieldTypeResolver != null) {
                        String resolveFieldName = key != null ? key.toString() : null;
                        Type fieldType = fieldTypeResolver.resolve(object, resolveFieldName);
                        if (fieldType != null) {
                            ObjectDeserializer fieldDeser = config.getDeserializer(fieldType);
                            obj = fieldDeser.deserialze(this, fieldType, key);
                            objParsed = true;
                        }
                    }
                    if (!objParsed) {
                        obj = this.parseObject(input, key);
                    }

                    if (ctxLocal != null && input != obj) {
                        ctxLocal.object = object;
                    }

                    if (key != null) {
                        checkMapResolve(object, key.toString());
                    }

                    map.put(key, obj);

                    if (parentIsArray) {
                        //setContext(context, obj, key);
                        setContext(obj, key);
                    }

                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                        lexer.nextToken();

                        setContext(context);
                        return object;
                    } else if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                        if (parentIsArray) {
                            this.popContext();
                        } else {
                            this.setContext(context);
                        }
                        continue;
                    } else {
                        throw new JSONException("syntax error, " + lexer.tokenName());
                    }
                } else {
                    lexer.nextToken();
                    value = parse();

                    map.put(key, value);

                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                        lexer.nextToken();
                        return object;
                    } else if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                        continue;
                    } else {
                        throw new JSONException("syntax error, position at " + lexer.pos() + ", name " + key);
                    }
                }

                lexer.skipWhitespace();
                ch = lexer.getCurrent();
                if (ch == ',') {
                    lexer.next();
                    continue;
                } else if (ch == '}') {
                    lexer.next();
                    lexer.resetStringPosition();
                    lexer.nextToken();

                    // this.setContext(object, fieldName);
                    this.setContext(value, key);

                    return object;
                } else {
                    throw new JSONException("syntax error, position at " + lexer.pos() + ", name " + key);
                }

            }
        } finally {
            this.setContext(context);
        }

    }

    public mutants.v36.com.alibaba.fastjson.parser.ParserConfig getConfig() {
        return config;
    }

    public void setConfig(ParserConfig config) {
        this.config = config;
    }

    // compatible
    @SuppressWarnings("unchecked")
    public <T> T parseObject(Class<T> clazz) {
        return (T) parseObject(clazz, null);
    }

    public <T> T parseObject(Type type) {
        return parseObject(type, null);
    }

    @SuppressWarnings("unchecked")
    public <T> T parseObject(Type type, Object fieldName) {
        int token = lexer.token();
        if (token == mutants.v36.com.alibaba.fastjson.parser.JSONToken.NULL) {
            lexer.nextToken();
            return null;
        }

        if (token == mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING) {
            if (type == byte[].class) {
                byte[] bytes = lexer.bytesValue();
                lexer.nextToken();
                return (T) bytes;
            }

            if (type == char[].class) {
                String strVal = lexer.stringVal();
                lexer.nextToken();
                return (T) strVal.toCharArray();
            }
        }

        ObjectDeserializer deserializer = config.getDeserializer(type);

        try {
            if (deserializer.getClass() == JavaBeanDeserializer.class) {
                if (lexer.token()!= mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACE && lexer.token()!= mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACKET) {
                throw new JSONException("syntax error,except start with { or [,but actually start with "+ lexer.tokenName());
            }
                return (T) ((JavaBeanDeserializer) deserializer).deserialze(this, type, fieldName, 0);
            } else {
                return (T) deserializer.deserialze(this, type, fieldName);
            }
        } catch (JSONException e) {
            throw e;
        } catch (Throwable e) {
            throw new JSONException(e.getMessage(), e);
        }
    }

    public <T> List<T> parseArray(Class<T> clazz) {
        List<T> array = new ArrayList<T>();
        parseArray(clazz, array);
        return array;
    }

    public void parseArray(Class<?> clazz, @SuppressWarnings("rawtypes") Collection array) {
        parseArray((Type) clazz, array);
    }

    @SuppressWarnings("rawtypes")
    public void parseArray(Type type, Collection array) {
        parseArray(type, array, null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void parseArray(Type type, Collection array, Object fieldName) {
        int token = lexer.token();
        if (token == mutants.v36.com.alibaba.fastjson.parser.JSONToken.SET || token == mutants.v36.com.alibaba.fastjson.parser.JSONToken.TREE_SET) {
            lexer.nextToken();
            token = lexer.token();
        }

        if (token != mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACKET) {
            throw new JSONException("expect '[', but " + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(token) + ", " + lexer.info());
        }

        ObjectDeserializer deserializer = null;
        if (int.class == type) {
            deserializer = IntegerCodec.instance;
            lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT);
        } else if (String.class == type) {
            deserializer = StringCodec.instance;
            lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING);
        } else {
            deserializer = config.getDeserializer(type);
            lexer.nextToken(deserializer.getFastMatchToken());
        }

        mutants.v36.com.alibaba.fastjson.parser.ParseContext context = this.context;
        this.setContext(array, fieldName);
        try {
            for (int i = 0;; ++i) {
                if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas)) {
                    while (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                        lexer.nextToken();
                        continue;
                    }
                }

                if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET) {
                    break;
                }

                if (int.class == type) {
                    Object val = IntegerCodec.instance.deserialze(this, null, null);
                    array.add(val);
                } else if (String.class == type) {
                    String value;
                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING) {
                        value = lexer.stringVal();
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                    } else {
                        Object obj = this.parse();
                        if (obj == null) {
                            value = null;
                        } else {
                            value = obj.toString();
                        }
                    }

                    array.add(value);
                } else {
                    Object val;
                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.NULL) {
                        lexer.nextToken();
                        val = null;
                    } else {
                        val = deserializer.deserialze(this, type, i);
                    }
                    array.add(val);
                    checkListResolve(array);
                }

                if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                    lexer.nextToken(deserializer.getFastMatchToken());
                    continue;
                }
            }
        } finally {
            this.setContext(context);
        }

        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
    }

    public Object[] parseArray(Type[] types) {
        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.NULL) {
            lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
            return null;
        }

        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACKET) {
            throw new JSONException("syntax error : " + lexer.tokenName());
        }

        Object[] list = new Object[types.length];
        if (types.length == 0) {
            lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET);

            if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET) {
                throw new JSONException("syntax error");
            }

            lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
            return new Object[0];
        }

        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT);

        for (int i = 0; i < types.length; ++i) {
            Object value;

            if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.NULL) {
                value = null;
                lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
            } else {
                Type type = types[i];
                if (type == int.class || type == Integer.class) {
                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT) {
                        value = Integer.valueOf(lexer.intValue());
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                    } else {
                        value = this.parse();
                        value = TypeUtils.cast(value, type, config);
                    }
                } else if (type == String.class) {
                    if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING) {
                        value = lexer.stringVal();
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                    } else {
                        value = this.parse();
                        value = TypeUtils.cast(value, type, config);
                    }
                } else {
                    boolean isArray = false;
                    Class<?> componentType = null;
                    if (i == types.length - 1) {
                        if (type instanceof Class) {
                            Class<?> clazz = (Class<?>) type;
                            if (!((clazz == byte[].class || clazz == char[].class) && lexer.token() == LITERAL_STRING)) {
                                isArray = clazz.isArray();
                                componentType = clazz.getComponentType();
                            }
                        }
                    }

                    // support varArgs
                    if (isArray && lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACKET) {
                        List<Object> varList = new ArrayList<Object>();

                        ObjectDeserializer deserializer = config.getDeserializer(componentType);
                        int fastMatch = deserializer.getFastMatchToken();

                        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET) {
                            for (;;) {
                                Object item = deserializer.deserialze(this, type, null);
                                varList.add(item);

                                if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                                    lexer.nextToken(fastMatch);
                                } else if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET) {
                                    break;
                                } else {
                                    throw new JSONException("syntax error :" + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(lexer.token()));
                                }
                            }
                        }

                        value = TypeUtils.cast(varList, type, config);
                    } else {
                        ObjectDeserializer deserializer = config.getDeserializer(type);
                        value = deserializer.deserialze(this, type, i);
                    }
                }
            }
            list[i] = value;

            if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET) {
                break;
            }

            if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                throw new JSONException("syntax error :" + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(lexer.token()));
            }

            if (i == types.length - 1) {
                lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET);
            } else {
                lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT);
            }
        }

        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET) {
            throw new JSONException("syntax error");
        }

        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);

        return list;
    }

    public void parseObject(Object object) {
        Class<?> clazz = object.getClass();
        JavaBeanDeserializer beanDeser = null;
        ObjectDeserializer deserializer = config.getDeserializer(clazz);
        if (deserializer instanceof JavaBeanDeserializer) {
            beanDeser = (JavaBeanDeserializer) deserializer;
        }

        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACE && lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
            throw new JSONException("syntax error, expect {, actual " + lexer.tokenName());
        }

        for (;;) {
            // lexer.scanSymbol
            String key = lexer.scanSymbol(symbolTable);

            if (key == null) {
                if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                    lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                    break;
                }
                if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                    if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas)) {
                        continue;
                    }
                }
            }

            FieldDeserializer fieldDeser = null;
            if (beanDeser != null) {
                fieldDeser = beanDeser.getFieldDeserializer(key);
            }

            if (fieldDeser == null) {
                if (!lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.IgnoreNotMatch)) {
                    throw new JSONException("setter not found, class " + clazz.getName() + ", property " + key);
                }

                lexer.nextTokenWithColon();
                parse(); // skip

                if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                    lexer.nextToken();
                    return;
                }

                continue;
            } else {
                Class<?> fieldClass = fieldDeser.fieldInfo.fieldClass;
                Type fieldType = fieldDeser.fieldInfo.fieldType;
                Object fieldValue;
                if (fieldClass == int.class) {
                    lexer.nextTokenWithColon(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT);
                    fieldValue = IntegerCodec.instance.deserialze(this, fieldType, null);
                } else if (fieldClass == String.class) {
                    lexer.nextTokenWithColon(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING);
                    fieldValue = StringCodec.deserialze(this);
                } else if (fieldClass == long.class) {
                    lexer.nextTokenWithColon(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT);
                    fieldValue = LongCodec.instance.deserialze(this, fieldType, null);
                } else {
                    ObjectDeserializer fieldValueDeserializer = config.getDeserializer(fieldClass, fieldType);

                    lexer.nextTokenWithColon(fieldValueDeserializer.getFastMatchToken());
                    fieldValue = fieldValueDeserializer.deserialze(this, fieldType, null);
                }

                fieldDeser.setValue(object, fieldValue);
            }

            if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                continue;
            }

            if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                return;
            }
        }
    }

    public Object parseArrayWithType(Type collectionType) {
        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.NULL) {
            lexer.nextToken();
            return null;
        }

        Type[] actualTypes = ((ParameterizedType) collectionType).getActualTypeArguments();

        if (actualTypes.length != 1) {
            throw new JSONException("not support type " + collectionType);
        }

        Type actualTypeArgument = actualTypes[0];

        if (actualTypeArgument instanceof Class) {
            List<Object> array = new ArrayList<Object>();
            this.parseArray((Class<?>) actualTypeArgument, array);
            return array;
        }

        if (actualTypeArgument instanceof WildcardType) {
            WildcardType wildcardType = (WildcardType) actualTypeArgument;

            // assert wildcardType.getUpperBounds().length == 1;
            Type upperBoundType = wildcardType.getUpperBounds()[0];

            // assert upperBoundType instanceof Class;
            if (Object.class.equals(upperBoundType)) {
                if (wildcardType.getLowerBounds().length == 0) {
                    // Collection<?>
                    return parse();
                } else {
                    throw new JSONException("not support type : " + collectionType);
                }
            }

            List<Object> array = new ArrayList<Object>();
            this.parseArray((Class<?>) upperBoundType, array);
            return array;

            // throw new JSONException("not support type : " +
            // collectionType);return parse();
        }

        if (actualTypeArgument instanceof TypeVariable) {
            TypeVariable<?> typeVariable = (TypeVariable<?>) actualTypeArgument;
            Type[] bounds = typeVariable.getBounds();

            if (bounds.length != 1) {
                throw new JSONException("not support : " + typeVariable);
            }

            Type boundType = bounds[0];
            if (boundType instanceof Class) {
                List<Object> array = new ArrayList<Object>();
                this.parseArray((Class<?>) boundType, array);
                return array;
            }
        }

        if (actualTypeArgument instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) actualTypeArgument;

            List<Object> array = new ArrayList<Object>();
            this.parseArray(parameterizedType, array);
            return array;
        }

        throw new JSONException("TODO : " + collectionType);
    }

    public void acceptType(String typeName) {
        mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer = this.lexer;

        lexer.nextTokenWithColon();

        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING) {
            throw new JSONException("type not match error");
        }

        if (typeName.equals(lexer.stringVal())) {
            lexer.nextToken();
            if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                lexer.nextToken();
            }
        } else {
            throw new JSONException("type not match error");
        }
    }

    public int getResolveStatus() {
        return resolveStatus;
    }

    public void setResolveStatus(int resolveStatus) {
        this.resolveStatus = resolveStatus;
    }

    public Object getObject(String path) {
        for (int i = 0; i < contextArrayIndex; ++i) {
            if (path.equals(contextArray[i].toString())) {
                return contextArray[i].object;
            }
        }

        return null;
    }

    @SuppressWarnings("rawtypes")
    public void checkListResolve(Collection array) {
        if (resolveStatus == NeedToResolve) {
            if (array instanceof List) {
                final int index = array.size() - 1;
                final List list = (List) array;
                ResolveTask task = getLastResolveTask();
                task.fieldDeserializer = new ResolveFieldDeserializer(this, list, index);
                task.ownerContext = context;
                setResolveStatus(DefaultJSONParser.NONE);
            } else {
                ResolveTask task = getLastResolveTask();
                task.fieldDeserializer  = new ResolveFieldDeserializer(array);
                task.ownerContext = context;
                setResolveStatus(DefaultJSONParser.NONE);
            }
        }
    }

    @SuppressWarnings("rawtypes")
    public void checkMapResolve(Map object, Object fieldName) {
        if (resolveStatus == NeedToResolve) {
            ResolveFieldDeserializer fieldResolver = new ResolveFieldDeserializer(object, fieldName);
            ResolveTask task = getLastResolveTask();
            task.fieldDeserializer = fieldResolver;
            task.ownerContext = context;
            setResolveStatus(DefaultJSONParser.NONE);
        }
    }

    @SuppressWarnings("rawtypes")
    public Object parseObject(final Map object) {
        return parseObject(object, null);
    }

    public JSONObject parseObject() {
        JSONObject object = new JSONObject(lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.OrderedField));
        Object parsedObject = parseObject(object);

        if (parsedObject instanceof JSONObject) {
            return (JSONObject) parsedObject;
        }

        if (parsedObject == null) {
            return null;
        }

        return new JSONObject((Map) parsedObject);
    }

    @SuppressWarnings("rawtypes")
    public final void parseArray(final Collection array) {
        parseArray(array, null);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public final void parseArray(final Collection array, Object fieldName) {
        final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer = this.lexer;

        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.SET || lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.TREE_SET) {
            lexer.nextToken();
        }

        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACKET) {
            throw new JSONException("syntax error, expect [, actual " + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(lexer.token()) + ", pos "
                                    + lexer.pos() + ", fieldName " + fieldName);
        }

        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING);

        if (this.context != null && this.context.level > 512) {
            throw new JSONException("array level > 512");
        }

        mutants.v36.com.alibaba.fastjson.parser.ParseContext context = this.context;
        this.setContext(array, fieldName);
        try {
            for (int i = 0;; ++i) {
                if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas)) {
                    while (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                        lexer.nextToken();
                        continue;
                    }
                }

                Object value;
                switch (lexer.token()) {
                    case LITERAL_INT:
                        value = lexer.integerValue();
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                        break;
                    case LITERAL_FLOAT:
                        if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.UseBigDecimal)) {
                            value = lexer.decimalValue(true);
                        } else {
                            value = lexer.decimalValue(false);
                        }
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                        break;
                    case LITERAL_STRING:
                        String stringLiteral = lexer.stringVal();
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);

                        if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat)) {
                            mutants.v36.com.alibaba.fastjson.parser.JSONScanner iso8601Lexer = new mutants.v36.com.alibaba.fastjson.parser.JSONScanner(stringLiteral);
                            if (iso8601Lexer.scanISO8601DateIfMatch()) {
                                value = iso8601Lexer.getCalendar().getTime();
                            } else {
                                value = stringLiteral;
                            }
                            iso8601Lexer.close();
                        } else {
                            value = stringLiteral;
                        }

                        break;
                    case TRUE:
                        value = Boolean.TRUE;
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                        break;
                    case FALSE:
                        value = Boolean.FALSE;
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                        break;
                    case LBRACE:
                        JSONObject object = new JSONObject(lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.OrderedField));
                        value = parseObject(object, i);
                        break;
                    case LBRACKET:
                        Collection items = new JSONArray();
                        parseArray(items, i);
                        if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.UseObjectArray)) {
                            value = items.toArray();
                        } else {
                            value = items;
                        }
                        break;
                    case NULL:
                        value = null;
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING);
                        break;
                    case UNDEFINED:
                        value = null;
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING);
                        break;
                    case RBRACKET:
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                        return;
                    case EOF:
                        throw new JSONException("unclosed jsonArray");
                    default:
                        value = parse();
                        break;
                }

                array.add(value);
                checkListResolve(array);

                if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA) {
                    lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_STRING);
                    continue;
                }
            }
        } finally {
            this.setContext(context);
        }
    }

    public mutants.v36.com.alibaba.fastjson.parser.ParseContext getContext() {
        return context;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (resolveTaskList == null) {
            resolveTaskList = new ArrayList<ResolveTask>(2);
        }
        return resolveTaskList;
    }

    public void addResolveTask(ResolveTask task) {
        if (resolveTaskList == null) {
            resolveTaskList = new ArrayList<ResolveTask>(2);
        }
        resolveTaskList.add(task);
    }

    public ResolveTask getLastResolveTask() {
        return resolveTaskList.get(resolveTaskList.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (extraProcessors == null) {
            extraProcessors = new ArrayList<ExtraProcessor>(2);
        }
        return extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (extraTypeProviders == null) {
            extraTypeProviders = new ArrayList<ExtraTypeProvider>(2);
        }
        return extraTypeProviders;
    }

    public FieldTypeResolver getFieldTypeResolver() {
        return fieldTypeResolver;
    }

    public void setFieldTypeResolver(FieldTypeResolver fieldTypeResolver) {
        this.fieldTypeResolver = fieldTypeResolver;
    }

    public void setContext(mutants.v36.com.alibaba.fastjson.parser.ParseContext context) {
        if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.DisableCircularReferenceDetect)) {
            return;
        }
        this.context = context;
    }

    public void popContext() {
        if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.DisableCircularReferenceDetect)) {
            return;
        }

        this.context = this.context.parent;

        if (contextArrayIndex <= 0) {
            return;
        }

        contextArrayIndex--;
        contextArray[contextArrayIndex] = null;
    }

    public mutants.v36.com.alibaba.fastjson.parser.ParseContext setContext(Object object, Object fieldName) {
        if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.DisableCircularReferenceDetect)) {
            return null;
        }

        return setContext(this.context, object, fieldName);
    }

    public mutants.v36.com.alibaba.fastjson.parser.ParseContext setContext(mutants.v36.com.alibaba.fastjson.parser.ParseContext parent, Object object, Object fieldName) {
        if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.DisableCircularReferenceDetect)) {
            return null;
        }

        this.context = new mutants.v36.com.alibaba.fastjson.parser.ParseContext(parent, object, fieldName);
        addContext(this.context);

        return this.context;
    }

    private void addContext(mutants.v36.com.alibaba.fastjson.parser.ParseContext context) {
        int i = contextArrayIndex++;
        if (contextArray == null) {
            contextArray = new mutants.v36.com.alibaba.fastjson.parser.ParseContext[8];
        } else if (i >= contextArray.length) {
            int newLen = (contextArray.length * 3) / 2;
            mutants.v36.com.alibaba.fastjson.parser.ParseContext[] newArray = new mutants.v36.com.alibaba.fastjson.parser.ParseContext[newLen];
            System.arraycopy(contextArray, 0, newArray, 0, contextArray.length);
            contextArray = newArray;
        }
        contextArray[i] = context;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parseKey() {
        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.IDENTIFIER) {
            String value = lexer.stringVal();
            lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
            return value;
        }
        return parse(null);
    }

    public Object parse(Object fieldName) {
        final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer = this.lexer;
        switch (lexer.token()) {
            case SET:
                lexer.nextToken();
                HashSet<Object> set = new HashSet<Object>();
                parseArray(set, fieldName);
                return set;
            case TREE_SET:
                lexer.nextToken();
                TreeSet<Object> treeSet = new TreeSet<Object>();
                parseArray(treeSet, fieldName);
                return treeSet;
            case LBRACKET:
                JSONArray array = new JSONArray();
                parseArray(array, fieldName);
                if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.UseObjectArray)) {
                    return array.toArray();
                }
                return array;
            case LBRACE:
                JSONObject object = new JSONObject(lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.OrderedField));
                return parseObject(object, fieldName);
//            case LBRACE: {
//                Map<String, Object> map = lexer.isEnabled(Feature.OrderedField)
//                        ? new LinkedHashMap<String, Object>()
//                        : new HashMap<String, Object>();
//                Object obj = parseObject(map, fieldName);
//                if (obj != map) {
//                    return obj;
//                }
//                return new JSONObject(map);
//            }
            case LITERAL_INT:
                Number intValue = lexer.integerValue();
                lexer.nextToken();
                return intValue;
            case LITERAL_FLOAT:
                Object value = lexer.decimalValue(lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.UseBigDecimal));
                lexer.nextToken();
                return value;
            case LITERAL_STRING:
                String stringLiteral = lexer.stringVal();
                lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);

                if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowISO8601DateFormat)) {
                    mutants.v36.com.alibaba.fastjson.parser.JSONScanner iso8601Lexer = new JSONScanner(stringLiteral);
                    try {
                        if (iso8601Lexer.scanISO8601DateIfMatch()) {
                            return iso8601Lexer.getCalendar().getTime();
                        }
                    } finally {
                        iso8601Lexer.close();
                    }
                }

                return stringLiteral;
            case NULL:
                lexer.nextToken();
                return null;
            case UNDEFINED:
                lexer.nextToken();
                return null;
            case TRUE:
                lexer.nextToken();
                return Boolean.TRUE;
            case FALSE:
                lexer.nextToken();
                return Boolean.FALSE;
            case NEW:
                lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.IDENTIFIER);

                if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.IDENTIFIER) {
                    throw new JSONException("syntax error");
                }
                lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LPAREN);

                accept(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LPAREN);
                long time = lexer.integerValue().longValue();
                accept(mutants.v36.com.alibaba.fastjson.parser.JSONToken.LITERAL_INT);

                accept(mutants.v36.com.alibaba.fastjson.parser.JSONToken.RPAREN);

                return new Date(time);
            case EOF:
                if (lexer.isBlankInput()) {
                    return null;
                }
                throw new JSONException("unterminated json string, " + lexer.info());
            case HEX:
                byte[] bytes = lexer.bytesValue();
                lexer.nextToken();
                return bytes;
            case IDENTIFIER:
                String identifier = lexer.stringVal();
                if ("NaN".equals(identifier)) {
                    lexer.nextToken();
                    return null;
                }
                throw new JSONException("syntax error, " + lexer.info());
            case ERROR:
            default:
                throw new JSONException("syntax error, " + lexer.info());
        }
    }

    public void config(mutants.v36.com.alibaba.fastjson.parser.Feature feature, boolean state) {
        this.lexer.config(feature, state);
    }

    public boolean isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature feature) {
        return lexer.isEnabled(feature);
    }

    public mutants.v36.com.alibaba.fastjson.parser.JSONLexer getLexer() {
        return lexer;
    }

    public final void accept(final int token) {
        final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer = this.lexer;
        if (lexer.token() == token) {
            lexer.nextToken();
        } else {
            throw new JSONException("syntax error, expect " + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(token) + ", actual "
                                    + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(lexer.token()));
        }
    }

    public final void accept(final int token, int nextExpectToken) {
        final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer = this.lexer;
        if (lexer.token() == token) {
            lexer.nextToken(nextExpectToken);
        } else {
            throwException(token);
        }
    }

    public void throwException(int token) {
        throw new JSONException("syntax error, expect " + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(token) + ", actual "
                                + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(lexer.token()));
    }

    public void close() {
        final mutants.v36.com.alibaba.fastjson.parser.JSONLexer lexer = this.lexer;

        try {
            if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AutoCloseSource)) {
                if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.EOF) {
                    throw new JSONException("not close json text, token : " + mutants.v36.com.alibaba.fastjson.parser.JSONToken.name(lexer.token()));
                }
            }
        } finally {
            lexer.close();
        }
    }

    public Object resolveReference(String ref) {
        if(contextArray == null) {
            return null;
        }
        for (int i = 0; i < contextArray.length && i < contextArrayIndex; i++) {
            mutants.v36.com.alibaba.fastjson.parser.ParseContext context = contextArray[i];
            if (context.toString().equals(ref)) {
                return context.object;
            }
        }
        return null;
    }

    public void handleResovleTask(Object value) {
        if (resolveTaskList == null) {
            return;
        }

        for (int i = 0, size = resolveTaskList.size(); i < size; ++i) {
            ResolveTask task = resolveTaskList.get(i);
            String ref = task.referenceValue;

            Object object = null;
            if (task.ownerContext != null) {
                object = task.ownerContext.object;
            }

            Object refValue;

            if (ref.startsWith("$")) {
                refValue = getObject(ref);
                if (refValue == null) {
                    try {
                        refValue = JSONPath.eval(value, ref);
                    } catch (JSONPathException ex) {
                        // skip
                    }
                }
            } else {
                refValue = task.context.object;
            }

            FieldDeserializer fieldDeser = task.fieldDeserializer;

            if (fieldDeser != null) {
                if (refValue != null
                        && refValue.getClass() == JSONObject.class
                        && fieldDeser.fieldInfo != null
                        && !Map.class.isAssignableFrom(fieldDeser.fieldInfo.fieldClass)) {
                    Object root = this.contextArray[0].object;
                    refValue = JSONPath.eval(root, ref);
                }

                fieldDeser.setValue(object, refValue);
            }
        }
    }

    public static class ResolveTask {

        public final mutants.v36.com.alibaba.fastjson.parser.ParseContext context;
        public final String       referenceValue;
        public FieldDeserializer  fieldDeserializer;
        public mutants.v36.com.alibaba.fastjson.parser.ParseContext ownerContext;

        public ResolveTask(mutants.v36.com.alibaba.fastjson.parser.ParseContext context, String referenceValue){
            this.context = context;
            this.referenceValue = referenceValue;
        }
    }

    public void parseExtra(Object object, String key) {
        final JSONLexer lexer = this.lexer; // xxx
        lexer.nextTokenWithColon();
        Type type = null;

        if (extraTypeProviders != null) {
            for (ExtraTypeProvider extraProvider : extraTypeProviders) {
                type = extraProvider.getExtraType(object, key);
            }
        }
        Object value = type == null //
            ? parse() // skip
            : parseObject(type);

        if (object instanceof ExtraProcessable) {
            ExtraProcessable extraProcessable = ((ExtraProcessable) object);
            extraProcessable.processExtra(key, value);
            return;
        }

        if (extraProcessors != null) {
            for (ExtraProcessor process : extraProcessors) {
                process.processExtra(object, key, value);
            }
        }

        if (resolveStatus == NeedToResolve) {
            resolveStatus = NONE;
        }
    }

    public Object parse(PropertyProcessable object, Object fieldName) {
        if (lexer.token() != mutants.v36.com.alibaba.fastjson.parser.JSONToken.LBRACE) {
            String msg = "syntax error, expect {, actual " + lexer.tokenName();
            if (fieldName instanceof String) {
                msg += ", fieldName ";
                msg += fieldName;
            }
            msg += ", ";
            msg += lexer.info();

            JSONArray array = new JSONArray();
            parseArray(array, fieldName);

            if (array.size() == 1) {
                Object first = array.get(0);
                if (first instanceof JSONObject) {
                    return (JSONObject) first;
                }
            }

            throw new JSONException(msg);
        }

        ParseContext context = this.context;
        try {
            for (int i = 0;;++i) {
                lexer.skipWhitespace();
                char ch = lexer.getCurrent();
                if (lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowArbitraryCommas)) {
                    while (ch == ',') {
                        lexer.next();
                        lexer.skipWhitespace();
                        ch = lexer.getCurrent();
                    }
                }

                String key;
                if (ch == '"') {
                    key = lexer.scanSymbol(symbolTable, '"');
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos());
                    }
                } else if (ch == '}') {
                    lexer.next();
                    lexer.resetStringPosition();
                    lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                    return object;
                } else if (ch == '\'') {
                    if (!lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowSingleQuotes)) {
                        throw new JSONException("syntax error");
                    }

                    key = lexer.scanSymbol(symbolTable, '\'');
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos());
                    }
                } else {
                    if (!lexer.isEnabled(mutants.v36.com.alibaba.fastjson.parser.Feature.AllowUnQuotedFieldNames)) {
                        throw new JSONException("syntax error");
                    }

                    key = lexer.scanSymbolUnQuoted(symbolTable);
                    lexer.skipWhitespace();
                    ch = lexer.getCurrent();
                    if (ch != ':') {
                        throw new JSONException("expect ':' at " + lexer.pos() + ", actual " + ch);
                    }
                }

                lexer.next();
                lexer.skipWhitespace();
                ch = lexer.getCurrent();

                lexer.resetStringPosition();

                if (key == JSON.DEFAULT_TYPE_KEY && !lexer.isEnabled(Feature.DisableSpecialKeyDetect)) {
                    String typeName = lexer.scanSymbol(symbolTable, '"');

                    Class<?> clazz = config.checkAutoType(typeName, null, lexer.getFeatures());

                    if (Map.class.isAssignableFrom(clazz) ) {
                        lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                        if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACE) {
                            lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);
                            return object;
                        }
                        continue;
                    }

                    ObjectDeserializer deserializer = config.getDeserializer(clazz);

                    lexer.nextToken(mutants.v36.com.alibaba.fastjson.parser.JSONToken.COMMA);

                    setResolveStatus(DefaultJSONParser.TypeNameRedirect);

                    if (context != null && !(fieldName instanceof Integer)) {
                        popContext();
                    }

                    return (Map) deserializer.deserialze(this, clazz, fieldName);
                }

                Object value;
                lexer.nextToken();

                if (i != 0) {
                    setContext(context);
                }

                Type valueType = object.getType(key);

                if (lexer.token() == mutants.v36.com.alibaba.fastjson.parser.JSONToken.NULL) {
                    value = null;
                    lexer.nextToken();
                } else {
                    value = parseObject(valueType, key);
                }

                object.apply(key, value);

                setContext(context, value, key);
                setContext(context);

                final int tok = lexer.token();
                if (tok == mutants.v36.com.alibaba.fastjson.parser.JSONToken.EOF || tok == mutants.v36.com.alibaba.fastjson.parser.JSONToken.RBRACKET) {
                    return object;
                }

                if (tok == JSONToken.RBRACE) {
                    lexer.nextToken();
                    return object;
                }
            }
        } finally {
            setContext(context);
        }
    }

}
