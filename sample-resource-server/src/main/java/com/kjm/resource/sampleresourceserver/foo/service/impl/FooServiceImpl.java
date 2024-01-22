package com.kjm.resource.sampleresourceserver.foo.service.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kjm.resource.sampleresourceserver.foo.model.Foo;
import com.kjm.resource.sampleresourceserver.foo.repository.IFooRepository;
import com.kjm.resource.sampleresourceserver.foo.service.IFooService;

@Service
public class FooServiceImpl implements IFooService {

    private IFooRepository fooRepository;

    public FooServiceImpl(IFooRepository fooRepository) {
        this.fooRepository = fooRepository;
    }

    @Override
    public Optional<Foo> findById(Long id) {
        return fooRepository.findById(id);
    }

    @Override
    public Foo save(Foo foo) {
        return fooRepository.save(foo);
    }

    @Override
    public Iterable<Foo> findAll() {
        return fooRepository.findAll();
    }
}
