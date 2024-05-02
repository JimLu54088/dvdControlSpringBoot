package jp.co.csii.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.csii.entity.DVDEntity;
import jp.co.csii.repository.DVDRepository;

@Service
public class DVDService {

    @Autowired
    private DVDRepository dVDRepository;

    public List<DVDEntity> findByName(String dvdname) {
        return dVDRepository.findByName(dvdname);
    }

    public void insertDVD(DVDEntity dvdEntity) {
        dVDRepository.insertDVD(dvdEntity);
    }

    public List<DVDEntity> findById(int dvdId) {
        return dVDRepository.findById(dvdId);
    }

    public void deletebyId(int dvdId) {
        dVDRepository.deletebyId(dvdId);
    }

    public void borrowById(DVDEntity dvdEntity) {
        dVDRepository.borrowById(dvdEntity);

    }

    public void backById(int dvdId){
        dVDRepository.backById(dvdId);

    }

}
