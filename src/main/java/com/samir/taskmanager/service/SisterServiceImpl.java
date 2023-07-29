package com.samir.taskmanager.service;

import com.samir.taskmanager.Utility.NullChecker;
import com.samir.taskmanager.entity.Sister;
import com.samir.taskmanager.repository.SisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SisterServiceImpl implements SisterService {
    @Autowired
    private SisterRepository sisterRepository;

    @Override
    public List<Sister> getAllSister(String sortBy) {
        return sisterRepository.findAll(Sort.by(Sort.Direction.ASC, sortBy));
    }

    @Override
    public Sister saveSister(Sister sister) {
        sister.setSisterName(NullChecker.check(sister.getSisterName()));
        return sisterRepository.save(sister);
    }

    @Override
    public Sister getSisterById(Long id) {
        return sisterRepository.findById(id).get();
    }

    @Override
    public Sister updateSister(Sister sister) {
        sister.setSisterName(NullChecker.check(sister.getSisterName()));
        return sisterRepository.save(sister);
    }

    @Override
    public void deleteSisterById(Long id) {

        sisterRepository.deleteById(id);
    }

    @Override
    public void deleteAllSisters() {

        sisterRepository.deleteAll();
    }
}
