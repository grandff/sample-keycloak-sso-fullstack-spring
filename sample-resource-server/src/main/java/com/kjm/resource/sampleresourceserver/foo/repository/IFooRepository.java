package com.kjm.resource.sampleresourceserver.foo.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.kjm.resource.sampleresourceserver.foo.model.Foo;

public interface IFooRepository extends PagingAndSortingRepository<Foo, Long> {
}
