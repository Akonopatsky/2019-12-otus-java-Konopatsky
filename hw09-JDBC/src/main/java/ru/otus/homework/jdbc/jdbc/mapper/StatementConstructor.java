package ru.otus.homework.jdbc.jdbc.mapper;

import ru.otus.homework.jdbc.core.model.Id;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StatementConstructor<T> {
    final Class<?> clazz;
    private final String tableName;
    private final Field[] fields;
    private final Class<?>[] fieldTypes;
    private final String insertSQLString;
    private final String selectSQLString;
    private final String updateSQLString;
    private String idFieldName;

    public String getInsertStatement() {
        return insertSQLString;
    }

    public String getUpdateStatement() {
        return updateSQLString;
    }

    public String getSelectStatement() {
        return selectSQLString;
    }

    public StatementConstructor(T object) throws UnsupportedTypeException {
        clazz = object.getClass();
        tableName = clazz.getSimpleName().toLowerCase();
        fields = clazz.getDeclaredFields();
        fieldTypes = new Class[fields.length];
        initFields();
        insertSQLString = insertString();
        selectSQLString = selectString();
        updateSQLString = updateString();
    }

    private void initFields() throws UnsupportedTypeException {
        int idCount = 0;
        for (int i = 0; i < fields.length; i++) {
            fieldTypes[i] = fields[i].getType();
            if (fields[i].isAnnotationPresent(Id.class)) {
                idFieldName = fields[i].getName();
                idCount++;
            }
        }
        if (idCount != 1)
            throw new UnsupportedTypeException("in class " + clazz.getName() + " find " +idCount + " id fields, must be 1 ");
    }

    public T createObject(ResultSet resultSet) throws UnsupportedTypeException {
        Object[] values = new Object[fields.length];
        try {
            for (int i = 0; i < values.length ; i++) {
                values[i] = resultSet.getObject(i+1);
            }
            return (T)clazz.getConstructor(fieldTypes).newInstance(values);
        } catch (Exception e) {
            throw new UnsupportedTypeException(e);
        }
    }

    private String selectString() {
        StringBuilder result = new StringBuilder();
        result.append("select * from ")
                .append(tableName)
                .append("  where ")
                .append(idFieldName)
                .append(" = ?");
        return result.toString();
    }

    private String insertString() {
        if (fields.length<1) throw new UnsupportedOperationException("class has no one field");
        StringBuilder result = new StringBuilder();
        result.append("insert into ")
                .append(tableName)
                .append("(");

        for (int i = 1; i < fields.length; i++) {
            result.append(fields[i].getName());
            if (i<(fields.length-1)) result.append(", ");
        }
        result.append(") ")
                .append("values (");

        for (int i = 1; i < fields.length ; i++) {
            result.append("?");
            if (i<(fields.length-1)) result.append(", ");
        }
        result.append(") ");
        return result.toString();
    }

    private String updateString() {
        StringBuilder result = new StringBuilder();
        result.append("update ")
                .append(tableName)
                .append(" set ");
        for (int i = 1; i < fields.length ; i++) {
            if (!fields[i].getName().equals(idFieldName)) {
                result.append(fields[i].getName())
                        .append(" = ?,");
            }
        }
        result.deleteCharAt(result.length()-1);
        result.append("  where ")
                .append(idFieldName)
                .append(" = ?");
        return result.toString();
    }

    public List<String> getValuesForSave(T object) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i < fields.length; i++) {
            try {
                boolean accessible = fields[i].canAccess(object);
                fields[i].setAccessible(true);
                result.add(fields[i].get(object).toString());
                fields[i].setAccessible(accessible);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public List<String> getValuesForUpdate(T object) {
        List<String> result = new ArrayList<>();
        String idString = "";
        for (int i = 1; i < fields.length; i++) {
            try {
                if (!fields[i].getName().equals(idFieldName)) {
                    boolean accessible = fields[i].canAccess(object);
                    fields[i].setAccessible(true);
                    result.add(fields[i].get(object).toString());
                    fields[i].setAccessible(accessible);
                }
                else {
                    idString = fields[i].get(object).toString();
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        result.add(idString);
        return result;
    }
}
