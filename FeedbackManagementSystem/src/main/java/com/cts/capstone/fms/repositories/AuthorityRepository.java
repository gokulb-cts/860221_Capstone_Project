package com.cts.capstone.fms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.capstone.fms.domain.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

	Authority findByNameIgnoreCase(String roleName);

}
