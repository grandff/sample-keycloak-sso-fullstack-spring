package com.kjm.resource.sampleresourceserver.foo.service;

import java.util.Optional;

import com.kjm.resource.sampleresourceserver.foo.model.Foo;

public interface IFooService {
    Optional<Foo> findById(Long id);

    Foo save(Foo foo);

    Iterable<Foo> findAll();

}
