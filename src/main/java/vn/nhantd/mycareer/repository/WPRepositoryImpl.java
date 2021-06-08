package vn.nhantd.mycareer.repository;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import vn.nhantd.mycareer.model.user.WorkProgress;

import java.util.List;
import java.util.Optional;

@Repository
public class WPRepositoryImpl implements WPRepository {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public <S extends WorkProgress> S save(S s) {
        return null;
    }

    @Override
    public <S extends WorkProgress> List<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<WorkProgress> findById(ObjectId objectId) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(ObjectId objectId) {
        return false;
    }

    @Override
    public List<WorkProgress> findAll() {
        return null;
    }

    @Override
    public Iterable<WorkProgress> findAllById(Iterable<ObjectId> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(ObjectId objectId) {

    }

    @Override
    public void delete(WorkProgress workProgress) {

    }

    @Override
    public void deleteAll(Iterable<? extends WorkProgress> iterable) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<WorkProgress> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<WorkProgress> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WorkProgress> S insert(S s) {
        return null;
    }

    @Override
    public <S extends WorkProgress> List<S> insert(Iterable<S> iterable) {
        return null;
    }

    @Override
    public <S extends WorkProgress> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends WorkProgress> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends WorkProgress> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends WorkProgress> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends WorkProgress> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends WorkProgress> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public List<WorkProgress> findAllQuery(Query query) {
        List<WorkProgress> list = mongoTemplate.find(query, WorkProgress.class);
        return list;
    }
}
