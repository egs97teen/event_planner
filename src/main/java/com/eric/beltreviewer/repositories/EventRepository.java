package com.eric.beltreviewer.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.eric.beltreviewer.models.Event;

@Repository
public interface EventRepository extends CrudRepository<Event, Long>{

	@Query("SELECT e, u FROM Event e JOIN e.host u WHERE e.state = ?1 ORDER BY e.date ASC")
	List<Object[]> findEventsInState(String state);
	
	@Query("SELECT e, u FROM Event e JOIN e.host u WHERE e.state != ?1 ORDER BY e.date ASC")
	List<Object[]> findEventsOutOfState(String state);

	@Modifying
	@Query("update Event e set e.name = ?1, e.date = ?2, e.city = ?3, e.state = ?4 WHERE e.id = ?5")
	int updateEventDetails(String name, Date date, String city, String state, Long id);
}
