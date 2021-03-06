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

import org.jnosql.diana.api.Value;
import org.jnosql.diana.api.key.BucketManager;
import org.jnosql.diana.api.key.KeyValueEntity;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@KeyValueRepositoryInterceptor
class DefaultKeyValueRepository implements KeyValueRepository {

    private KeyValueEntityConverter converter;

    private Instance<BucketManager> manager;


    private KeyValueWorkflow flow;

    @Inject
    DefaultKeyValueRepository(KeyValueEntityConverter converter, Instance<BucketManager> manager, KeyValueWorkflow flow) {
        this.converter = converter;
        this.manager = manager;
        this.flow = flow;
    }

    DefaultKeyValueRepository() {
    }

    @Override
    public <T> T put(T entity) throws NullPointerException {
        UnaryOperator<KeyValueEntity<?>> putAction = k -> {
            manager.get().put(k);
            return k;

        };
        return flow.flow(entity, putAction);
    }

    @Override
    public <T> T put(T entity, Duration ttl) throws NullPointerException, UnsupportedOperationException {
        UnaryOperator<KeyValueEntity<?>> putAction = k -> {
            manager.get().put(k, ttl);
            return k;

        };
        return flow.flow(entity, putAction);
    }

    @Override
    public <K, T> Optional<T> get(K key, Class<T> clazz) throws NullPointerException {
        Optional<Value> value = manager.get().get(key);
        return value.map(v -> converter.toEntity(clazz, v))
                .filter(Objects::nonNull)
                .map(t -> Optional.ofNullable(t))
                .orElse(Optional.empty());
    }

    @Override
    public <K, T> Iterable<T> get(Iterable<K> keys, Class<T> clazz) throws NullPointerException {
        return StreamSupport.stream(manager.get()
                .get(keys).spliterator(), false)
                .map(v -> converter.toEntity(clazz, v))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public <K> void remove(K key) throws NullPointerException {
        manager.get().remove(key);
    }

    @Override
    public <K> void remove(Iterable<K> keys) throws NullPointerException {
        manager.get().remove(keys);
    }
}
