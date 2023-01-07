package com.jswy.infrastructure.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

public class JpaTradeRepository<T, ID extends Serializable> implements JpaRepository<T, ID> {

	@Override
	public Page<T> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return this.findAll(pageable);
	}

	@Override
	public <S extends T> S save(S entity) {
		// TODO Auto-generated method stub
		return save(entity);
	}

	@Override
	public Optional<T> findById(ID id) {
		// TODO Auto-generated method stub
		return findById(id);
	}

	@Override
	public boolean existsById(ID id) {
		// TODO Auto-generated method stub
		return existsById(id);
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return count();
	}

	@Override
	public void deleteById(ID id) {
		// TODO Auto-generated method stub
		deleteById(id);
	}

	@Override
	public void delete(T entity) {
		// TODO Auto-generated method stub
		delete(entity);
	}

	@Override
	public void deleteAll(Iterable<? extends T> entities) {
		// TODO Auto-generated method stub
		deleteAll(entities);
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		deleteAll();
	}

	@Override
	public <S extends T> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return findOne(example);
	}

	@Override
	public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return findAll(example, pageable);
	}

	@Override
	public <S extends T> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return count(example);
	}

	@Override
	public <S extends T> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return exists(example);
	}

	@Override
	public List<T> findAll() {
		// TODO Auto-generated method stub
		return findAll();
	}

	@Override
	public List<T> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return findAll(sort);
	}

	@Override
	public List<T> findAllById(Iterable<ID> ids) {
		// TODO Auto-generated method stub
		return findAllById(ids);
	}

	@Override
	public <S extends T> List<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return saveAll(entities);
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		flush();
	}

	@Override
	public <S extends T> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<T> entities) {
		// TODO Auto-generated method stub
		deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub
		deleteAllInBatch();
	}

	@Override
	public T getOne(ID id) {
		// TODO Auto-generated method stub
		return getOne(id);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return findAll(example);
	}

	@Override
	public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return findAll(example, sort);
	}

}
