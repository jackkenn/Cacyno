package com.example.demo.dao;

import com.example.demo.model.DemoModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDemoDao {
    int insertDemo(UUID id, DemoModel demo);

    default int insertDemo(DemoModel demo) {
        UUID id = UUID.randomUUID();
        return insertDemo(id, demo);
    }

    List<DemoModel> selectAll();

    Optional<DemoModel> selectDemoById(UUID id);

    int deleteDemoById(UUID id);

    int updateDemoById(UUID id, DemoModel model);
}
