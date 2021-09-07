package com.example.demo.dao;

import com.example.demo.model.DemoModel;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("DemoDao")
public class DemoDao implements IDemoDao {

    private static List<DemoModel> _db = new ArrayList<>();

    @Override
    public int insertDemo(UUID id, DemoModel demo) {
        _db.add(new DemoModel(id, demo.get_name()));
        return 1;
    }

    @Override
    public List<DemoModel> selectAll() {
        return _db;
    }

    @Override
    public int deleteDemoById(UUID id) {
        Optional<DemoModel> demo = selectDemoById(id);
        if(demo.isEmpty()) {
            return 0;
        }
        _db.remove(demo.get());
        return 1;
    }

    @Override
    public int updateDemoById(UUID id, DemoModel demo) {
        return selectDemoById(id).map(x -> {
            int indexToUpdate = _db.indexOf(x);
            if(indexToUpdate >= 0) {
                _db.set(indexToUpdate, new DemoModel(id, demo.get_name()));
                return 1;
            }
            return 0;
        }).orElse(0);
    }

    @Override
    public Optional<DemoModel> selectDemoById(UUID id) {
        return _db.stream().filter(x -> x.get_id().equals(id)).findFirst();
    }
}
