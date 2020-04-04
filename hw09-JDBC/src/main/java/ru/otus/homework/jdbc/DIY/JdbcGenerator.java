package ru.otus.homework.jdbc.DIY;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JdbcGenerator<T> implements StatementGenerator<T> {
    final Class<?> clazz;
    final private String tableName;
    final private Field[] fields;
    final private Class<?>[] fieldTypes;
    final private String insertSQLString;
    final private String selectSQLString;
    final private String updateSQLString;
    private String idFieldName;

    @Override
    public String getInsertStatement() {
        return insertSQLString;
    }

    @Override
    public String getUpdateStatement() {
        return updateSQLString;
    }

    @Override
    public String getSelectStatement() {
        return selectSQLString;
    }

    @Override
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

    public JdbcGenerator(T object) throws UnsupportedTypeException {
        clazz = object.getClass();
        tableName = clazz.getSimpleName();
        fields = clazz.getFields();
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
                        .append(" = ?");
                if (i<(fields.length-1)) result.append(", ");
            }
        }
        result.append("  where ")
                .append(idFieldName)
                .append(" = ?");
        System.out.println(result);
        return result.toString();

    }

    public List<String> getValuesForSave(T object) {
        List<String> result = new ArrayList<>();
        for (int i = 1; i < fields.length; i++) {
            try {
                result.add(fields[i].get(object).toString());
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
                    result.add(fields[i].get(object).toString());
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
