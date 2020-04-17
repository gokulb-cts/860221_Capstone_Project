package com.cts.capstone.fms.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long>{
	
	public Event findByEventId(String eventId);
	
	@Query("select e from Event e join fetch e.pocUser u join u.role r where u.userId = :pocUserId and r.roleName = 'ROLE_POC'")
	public List<Event> findByPocUserId(@Param("pocUserId") Long pocUserId);
	
}
