package com.jswy.infrastructure.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;

import com.jswy.domain.generic.demo.model.TradeRecord;
import com.jswy.domain.generic.demo.model.TradeRecordId;
import com.jswy.domain.generic.demo.repository.TradeRepository;
import com.jswy.domain.support.GenericRepository;

/**
 * 基于通用资源库模板适配的实现
 * 
 * @author admin
 *
 */
public class TradeRecordRepositoryJpaAdapter implements TradeRepository {
	/**
	 * 采用通用资源库适配器的 JPA 实现类典型
	 */
	private final GenericRepository<TradeRecord, TradeRecordId> repository;

	/**
	 * BaseRepositoryImpl类继承SimpleJpaRepository类，并实现父类构造器
	 * 
	 * @param domainClass
	 * @param entityManager
	 */
	public TradeRecordRepositoryJpaAdapter(GenericRepository<TradeRecord, TradeRecordId> repository) {
		this.repository = repository;
	}

	@Override
	public TradeRecord instanceOf(TradeRecordId tradeRecordId) {
		return repository.findById(tradeRecordId);
	}

	@Override
	public TradeRecord findById(TradeRecordId tradeRecordId) {
		return repository.findById(tradeRecordId);
	}

	@Override
	public void save(TradeRecord tradeRecord) {
		repository.save(tradeRecord);
	}

	@Override
	public void delete(TradeRecord tradeRecord) {
		repository.delete(tradeRecord);
	}

	@Override
	public TradeRecord findByTradeNumber(String tradeNumber) {
		Specification<TradeRecord> specification = (root, criterialQuery, criteriaBuilder) -> criteriaBuilder
				.equal(root.get("tradeNumber"), tradeNumber);
		List<TradeRecord> list = null;//repository.findBy(specification.toString());
		return null;//list.size() > 0 ? list.get(0) : null;
	}

	@Override
	public List<TradeRecord> findAll() {
		return null;
	}

}
