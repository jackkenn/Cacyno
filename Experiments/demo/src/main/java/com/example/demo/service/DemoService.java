package com.example.demo.service;

import com.example.demo.dao.IDemoDao;
import com.example.demo.model.DemoModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DemoService {

    private final IDemoDao _demoDao;

    @Autowired //inject
    public DemoService(@Qualifier("DemoDao") IDemoDao _demoDao) {
        this._demoDao = _demoDao;
    }

    public int addDemo(DemoModel demo) {
        return _demoDao.insertDemo(demo);
    }

    public List<DemoModel> getAll() {
        return _demoDao.selectAll();
    }

    public Optional<DemoModel> getDemoById(UUID id) {
        return _demoDao.selectDemoById(id);
    }

    public int deleteDemo(UUID id) {
        return _demoDao.deleteDemoById(id);
    }

    public int updateDemo(UUID id, DemoModel demo) {
        return _demoDao.updateDemoById(id, demo);
    }
}
