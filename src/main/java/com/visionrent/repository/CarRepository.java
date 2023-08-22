package com.visionrent.repository;

import com.visionrent.domain.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {


	/**
	 * @param id imageId
	 * @return number of cars that has image with this ID
	 */
	@Query("SELECT count(*) from Car c JOIN c.image im WHERE im.id=:id")
	Integer findCarCountByImageId(@Param("id") String id);

	@Query("SELECT c FROM Car c JOIN c.image im where im.id=:id")
	List<Car> findCarsByImageId(@Param("id") String id);

	@EntityGraph(attributePaths = {"image"})
	List<Car> findAll();

	@EntityGraph(attributePaths = {"image"})
	Optional<Car> findById(Long id);

	@EntityGraph(attributePaths = {"image"})
	Page<Car> findAll(Pageable pageable);


}
