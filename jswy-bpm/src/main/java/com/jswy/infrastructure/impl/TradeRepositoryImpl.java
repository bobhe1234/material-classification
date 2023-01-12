package com.jswy.infrastructure.impl;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.jswy.domain.generic.demo.model.TradeRecord;
import com.jswy.domain.generic.demo.model.TradeRecordId;
import com.jswy.domain.generic.demo.repository.TradeRepository;

@Repository
public class TradeRepositoryImpl extends HibernateSupport<TradeRecord> implements TradeRepository {

	@PersistenceContext
	private final EntityManager entityManager;

	TradeRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
		this.entityManager = entityManager;
	}

	@Override
	public TradeRecord findById(TradeRecordId tradeId) {
		// return Optional.ofNullable(entityManager.find(TradeRecord.class, id));
		if (StringUtils.isEmpty(tradeId.toString())) {
			return null;
		}
		Query<TradeRecord> query = getSession()
				.createQuery("from TradeRecord where trade_id=:trade_id and isDelete=0", TradeRecord.class)
				.setParameter("trade_id", tradeId.toString());
		return query.uniqueResult();
	}

	@Override
	public TradeRecord findByTradeNumber(String tradeNumber) {
		if (StringUtils.isEmpty(tradeNumber)) {
			return null;
		}
		Query<TradeRecord> query = getSession()
				.createQuery("from TradeRecord where tradeNumber=:tradeNumber and isDelete=0", TradeRecord.class)
				.setParameter("tradeNumber", tradeNumber);
		return query.uniqueResult();
	}

	@Override
	public List<TradeRecord> findAll() {
		return entityManager.createQuery("select o from TradeRecord o").getResultList();
	}

	@Override
	public void save(TradeRecord tradeRecord) {
		entityManager.merge(tradeRecord);
	}

	@Override
	public void delete(TradeRecord buyer) {
		entityManager.remove(buyer);
	}

	@Override
	public TradeRecord instanceOf(TradeRecordId tradeRecordId) {
		// return Optional.ofNullable(entityManager.find(TradeRecord.class, id));
		if (StringUtils.isEmpty(tradeRecordId.toString())) {
			return null;
		}
		Query<TradeRecord> query = getSession()
				.createQuery("from TradeRecord where trade_id=:trade_id and isDelete=0", TradeRecord.class)
				.setParameter("trade_id", tradeRecordId.toString());
		return query.uniqueResult();
	}
}

abstract class HibernateSupport<T> {

	private EntityManager entityManager;

	HibernateSupport(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	public void save(T object) {
		entityManager.persist(object);
		entityManager.flush();
	}

	public void update(T object) {
		entityManager.merge(object);
		entityManager.flush();
	}
}