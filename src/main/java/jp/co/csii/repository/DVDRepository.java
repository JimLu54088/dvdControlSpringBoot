package jp.co.csii.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.csii.entity.DVDEntity;

@Mapper
public interface DVDRepository {

	public List<DVDEntity> findByName(String dvdname);

	public void insertDVD(DVDEntity dvdEntity);

	public List<DVDEntity> findById(int dvdId);

	public void deletebyId(int dvdId);

	public void borrowById(DVDEntity dvdEntity);

	public void backById(int dvdId);

}
