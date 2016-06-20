package com.solt.flash.dao;

import java.io.Serializable;
import java.util.*;

public interface Dao<T> extends Serializable {

    public void insert(T t);

    public void update(T t);

    public void delete(Object id);

    public List<T> select(String where, Map<String, Object> params);
    public long selectCount(String where, Map<String, Object> params);

    public List<T> select(String where, Map<String, Object> params, String sortParam);
    public List<T> select(String where, Map<String, Object> params, String sortParam, int cntFrom, int offset);

    public T findById(Object id);

}