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
package org.jnosql.artemis.key;


import org.jnosql.diana.api.key.KeyValueEntity;

/**
 * This interface represent the manager of events. When an entity be either saved or updated an event will be fired. This order gonna be:
 * 1) firePreKeyValue
 * 2) firePostKeyValue
 * 3) firePostDocument
 * 4) firePostEntity
 *
 * @see KeyValueWorkflow
 */
public interface KeyValueEventPersistManager {

    /**
     * Fire an event after the conversion of the entity to communication API model.
     *
     * @param entity the entity
     */
    void firePreKeyValue(KeyValueEntity<?> entity);

    /**
     * Fire an event after the response from communication layer
     *
     * @param entity the entity
     */
    void firePostKeyValue(KeyValueEntity<?> entity);

    /**
     * Fire an event once the method is called
     *
     * @param entity the entity
     * @param <T>    the entity type
     */
    <T> void firePreEntity(T entity);

    /**
     * Fire an event after convert the {@link KeyValueEntity},
     * from database response, to Entity.
     *
     * @param entity the entity
     * @param <T>    the entity kind
     */
    <T> void firePostEntity(T entity);

}
