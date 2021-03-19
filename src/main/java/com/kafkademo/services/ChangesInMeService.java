package com.kafkademo.services;

import com.kafkademo.Repository.ChangesInMeRepository;
import com.kafkademo.models.ChangesInMe;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChangesInMeService {

    @Autowired
    ChangesInMeRepository changesInMeRepository;

//    Save changes

    public void saveChangesInMe(ChangesInMe changesInMe) {
        changesInMeRepository.save(changesInMe);
    }

    public void markChangeInMeAsRead(@NotNull List<ChangesInMe> changesInMeList) {
        changesInMeList.stream().forEach((changeInMe) -> {
            changesInMeRepository.setChangeInMeRead((changeInMe.getId()));
        });

    }

//    Get Unread Changes

    public List<ChangesInMe> getUnReadChangesInMe() {
        return changesInMeRepository.findByReadFalse();
    }

//    Get All Changes

    public List<ChangesInMe> getAllChangesInMe() {
        return changesInMeRepository.findAll();
    }


//    Retrieve changes with filtering on BloodSugarLevel

    public List<ChangesInMe> filterChangesInMe(Integer bloodSugarLevel) {
        return changesInMeRepository.findByBloodSugarLevelGreaterThan(bloodSugarLevel);
    }
}
