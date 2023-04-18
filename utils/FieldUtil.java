package utils;

import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldUtil {

    /* VarsType */
    public static List<String> getVarsTypeListByClass(Class<?> c) {
        List<String> list = new ArrayList<>();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            list.add(fieldType.getSimpleName());
        }
        return list;
    }

    public static Object getVarsTypeListByClass(Class<?> c, boolean toString) {
        if (!toString) return getVarsTypeListByClass(c);

        StringBuilder strBuilder = new StringBuilder();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldType = field.getType();
            strBuilder.append(fieldType.getSimpleName()).append(",");
        }

        //去掉最后的 ","
        strBuilder.deleteCharAt(strBuilder.length() - 1);

        return strBuilder.toString();
    }

    public static List<String> getVarsTypeListByObj(Object obj) {
        if (obj == null) {
            return null;
        }
        Class<?> c = obj.getClass();

        return getVarsTypeListByClass(c);
    }

    public static Object getVarsTypeListByObj(Object obj, boolean toString) {
        if (!toString) return getVarsTypeListByObj(obj);

        if (obj == null) {
            return null;
        }
        Class<?> c = obj.getClass();
        return getVarsTypeListByClass(c, true);
    }


    /* VarsName */
    public static List<String> getVarsNameListByClass(Class<?> c) {
        if (c == null) {
            return null;
        }
        List<String> list = new ArrayList<>();
        Field[] fields = c.getDeclaredFields();
        for (Field field : fields) {
            //获取成员变量的全称
            list.add(field.getName());
        }

        return list;
    }

    public static Object getVarsNameListByClass(Class<?> c, boolean toString) {
        if (c == null) {
            return null;
        }
        if (!toString) return getVarsNameListByClass(c);

        Field[] fields = c.getDeclaredFields();
        StringBuilder strBuilder = new StringBuilder();
        for (Field field : fields) {
            //获取成员变量的全称
            strBuilder.append(field.getName()).append(",");
        }
        strBuilder.deleteCharAt(strBuilder.length() - 1);

        return strBuilder.toString();
    }

    public static List<String> getVarsNameListByObj(Object obj) {
        if (obj == null) {
            return null;
        }

        Class<?> c = obj.getClass();
        return getVarsNameListByClass(c);
    }

    public static Object getVarsNameListByObj(Object obj, boolean toString) {
        if (obj == null) {
            return null;
        }
        if (!toString) return getVarsNameListByObj(obj);

        Class<?> c = obj.getClass();
        return getVarsNameListByClass(c, true);
    }


    /* VarsValue */
    @SneakyThrows
    public static List<Object> getVarsValueByObj(Object obj) {
        Class<?> clazz = obj.getClass();
        List<Object> vars = new ArrayList<>();
        Field[] fs = clazz.getDeclaredFields();
        for (Field field : fs) {
            field.setAccessible(true);
            vars.add(field.get(obj));
        }
        return vars;
    }

    @SneakyThrows
    public static Object getVarsValueByObj(Object obj, boolean toString) {
        if (!toString) return getVarsValueByObj(obj);

        Class<?> clazz = obj.getClass();
        StringBuilder strBuilder = new StringBuilder();
        Field[] fs = clazz.getDeclaredFields();
        for (Field field : fs) {
            field.setAccessible(true);
            strBuilder.append(field.get(obj)).append(",");
        }
        strBuilder.deleteCharAt(strBuilder.length() - 1);

        return strBuilder.toString();
    }


    /* Map */
    private static Map<?, ?> listToMap(List<?> a, List<?> b) {
        return a.stream().collect(Collectors.toMap(key -> key, key -> b.get(a.indexOf(key))));
    }

    public static Map<?, ?> getTypeNameMapByClass(Class<?> c) {
        if (c == null) {
            return null;
        }
        return listToMap(getVarsTypeListByClass(c), getVarsNameListByClass(c));
    }

    public static Map<?, ?> getTypeNameMapByObj(Object obj) {
        Class<?> c = obj.getClass();
        return getTypeNameMapByClass(c);
    }

    public static Map<?, ?> getNameValueMapByObj(Object obj) {
        if (obj == null) {
            return null;
        }

        return listToMap(getVarsNameListByObj(obj), getVarsValueByObj(obj));
    }

    public static Map<?, ?> getTypeValueMapByObj(Object obj) {
        if (obj == null) {
            return null;
        }

        return listToMap(getVarsTypeListByObj(obj), getVarsValueByObj(obj));
    }


    /* Others */
    public static int getVarsSumByClass(Class<?> c) {
        if (c == null) {
            return 0;
        }
        Field[] fields = c.getDeclaredFields();
        return fields.length;
    }

    public static int getVarsSumByObj(Object obj) {
        if (obj == null) {
            return 0;
        }
        Class<?> c = obj.getClass();
        return getVarsSumByClass(c);
    }
}