package com.ace.web.pf.repository;


import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ace.web.pf.datamodel.Student;
import com.ace.web.pf.dtomodel.StudentDTO;


@Repository
public interface StudentRepository
		extends CrudRepository<Student, Long>, JpaRepository<Student, Long>, DataTablesRepository<Student, Long>, BaseRepository {
	
	@Query
	Student findByName(@Param("name") String name);
	@Query(value="select * from test u where u.name like %?%",nativeQuery = true)
	List<StudentDTO> findByName1(String name);

//	@Query(value="select * from testfornumber",nativeQuery = true)
//	List<Map<String,Object>> list();

}
