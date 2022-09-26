package com.ace.web.pf.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ace.web.pf.datamodel.Test;
import com.ace.web.pf.dtomodel.StudentDTO;

@Repository
public interface TestRepository extends JpaRepository<Test,Integer > {
	@Query(value="select * from test u where u.name like %?%",nativeQuery = true)
	List<Test> findByName1(String name);

	
}
