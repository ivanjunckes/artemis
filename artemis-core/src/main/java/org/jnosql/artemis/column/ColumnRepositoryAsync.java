/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jnosql.artemis.column;


import org.jnosql.diana.api.ExecuteAsyncQueryException;
import org.jnosql.diana.api.column.ColumnDeleteQuery;
import org.jnosql.diana.api.column.ColumnQuery;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.StreamSupport;

/**
 * This interface that represents the common operation between an entity
 * and {@link org.jnosql.diana.api.column.ColumnEntity} to do async operations
 *
 * @see org.jnosql.diana.api.column.ColumnFamilyManagerAsync
 */
public interface ColumnRepositoryAsync {

    /**
     * Saves an entity asynchronously
     *
     * @param entity entity to be saved
     * @param <T>    the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when entity is null
     */
    <T> void save(T entity) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity entity to be saved
     * @param ttl    the time to live
     * @param <T>    the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when either entity or ttl are null
     */
    <T> void save(T entity, Duration ttl) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Saves entities asynchronously, by default it's just run for each saving using
     * {@link ColumnRepository#save(Object)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when entities is null
     */
    default <T> void save(Iterable<T> entities) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException {
        Objects.requireNonNull(entities, "entities is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(this::save);
    }

    /**
     * Saves entities asynchronously with time to live, by default it's just run for each saving using
     * {@link ColumnRepository#save(Object, Duration)},
     * each NoSQL vendor might replace to a more appropriate one.
     *
     * @param entities entities to be saved
     * @param ttl      time to live
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when either entities or ttl are null
     */
    default <T> void save(Iterable<T> entities, Duration ttl) throws NullPointerException {
        Objects.requireNonNull(entities, "entities is required");
        Objects.requireNonNull(ttl, "ttl is required");
        StreamSupport.stream(entities.spliterator(), false).forEach(d -> save(d, ttl));
    }

    /**
     * Saves an entity asynchronously
     *
     * @param entity   entity to be saved
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the saved entity within parameters
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when either entity or callback are null
     */
    <T> void save(T entity, Consumer<T> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;


    /**
     * Saves an entity asynchronously with time to live
     *
     * @param entity   entity to be saved
     * @param ttl      time to live
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the saved entity within parameters
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when either entity or ttl or callback are null
     */
    <T> void save(T entity, Duration ttl, Consumer<T> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Updates an entity asynchronously
     *
     * @param entity entity to be updated
     * @param <T>    the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when entity is null
     */
    <T> void update(T entity) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Updates an entity asynchronously
     *
     * @param entity   entity to be updated
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the updated entity within parametersa
     * @param <T>      the instance type
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when either entity or callback are null
     */
    <T> void update(T entity, Consumer<T> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Deletes an entity asynchronously
     *
     * @param query query to delete an entity
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when query are null
     */
    void delete(ColumnDeleteQuery query) throws ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;

    /**
     * Deletes an entity asynchronously
     *
     * @param query    query to delete an entity
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the null within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to delete asynchronous
     * @throws NullPointerException          when either query or callback are null
     */
    void delete(ColumnDeleteQuery query, Consumer<Void> callBack) throws ExecuteAsyncQueryException,
            UnsupportedOperationException, NullPointerException;

    /**
     * Finds entities from query asynchronously
     *
     * @param query    query to find entities
     * @param <T>      the instance type
     * @param callBack the callback, when the process is finished will call this instance returning
     *                 the result of query within parameters
     * @throws ExecuteAsyncQueryException    when there is a async error
     * @throws UnsupportedOperationException when the database does not have support to save asynchronous
     * @throws NullPointerException          when either query or callback are null
     */
    <T> void find(ColumnQuery query, Consumer<List<T>> callBack) throws
            ExecuteAsyncQueryException, UnsupportedOperationException, NullPointerException;
}
