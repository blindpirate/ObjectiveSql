package com.github.braisdom.objsql;

import com.github.braisdom.objsql.reflection.ClassUtils;
import com.github.braisdom.objsql.transition.ColumnTransitional;
import com.github.braisdom.objsql.util.WordUtil;

public class DynamicTableRowDescriptor<T extends DynamicModel> implements TableRowDescriptor {

    private final Class<T> clazz;

    public DynamicTableRowDescriptor(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T newInstance() {
        return ClassUtils.createNewInstance(clazz);
    }

    @Override
    public void setAutoGeneratedPK(Object bean, Object primaryKeyValue) {
        throw new UnsupportedOperationException("The dynamic model has no primary key");
    }

    @Override
    public String getFieldName(String columnName) {
        return WordUtil.camelize(columnName);
    }

    @Override
    public Class getFieldType(String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName).getType();
        } catch (NoSuchFieldException e) {
            return null;
        }
    }

    @Override
    public boolean isTransitable(String fieldName) {
        return false;
    }

    @Override
    public ColumnTransitional getColumnTransition(String fieldName) {
        // The dynamic model need not transit
        return null;
    }

    @Override
    public void setValue(Object modelObject, String fieldName, Object fieldValue) {
        ((DynamicModel)modelObject).put(fieldName, fieldValue);
    }

    @Override
    public Object getValue(Object modelObject, String fieldName) {
        return ((DynamicModel)modelObject).get(fieldName);
    }
}
