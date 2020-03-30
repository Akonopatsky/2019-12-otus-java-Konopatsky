package ru.otus.homework.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Tamplater<T> {
    final Class<?> clazz;
    final private String tableName;
    final private Field[] fields;
    final private String insertSQLString;
    final private String selectSQLString;
    private String idFieldname;



    public Tamplater(T object) throws UnsupportedTypeException {
        clazz = object.getClass();
        tableName = clazz.getSimpleName();
        fields = clazz.getFields();
        initFields();
        insertSQLString = getInsertString();
        selectSQLString = getSelectString();
    }

    private String getSelectString() {
        StringBuilder result = new StringBuilder();
        result.append("select * from ")
                .append(tableName)
                .append("  where ")
                .append(idFieldname)
                .append(" = ?");
        System.out.println(result);
        return result.toString();
    }

    private String getInsertString() {
        if (fields.length<1) throw new UnsupportedOperationException("class has no one field");
        StringBuilder result = new StringBuilder();
        result.append("insert into ")
                .append(tableName)
                .append("(");
        for (Field field : fields) {
            result.append(field.getName())
                    .append(",");
        }
        result.deleteCharAt(result.length()-1);
        result.append(") ")
                .append("values (");
        for (Field field : fields) {
            result.append("?,");
        }
        result.deleteCharAt(result.length()-1);
        result.append(") ");
        System.out.println(result);
        return result.toString();
    }

    private void initFields() throws UnsupportedTypeException {
        int idCount = 0;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Id.class)) {
                idFieldname = field.getName();
                idCount++;
            }
        }
        if (idCount != 1)
            throw new UnsupportedTypeException("in class " + clazz.getName() + " find " +idCount + " id fields, must be 1 ");
    }

    private List<String> getParams(T object) {
        List<String> result = new ArrayList<>();
        for (Field field : fields) {
            try {
                result.add(field.get(object).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

}
