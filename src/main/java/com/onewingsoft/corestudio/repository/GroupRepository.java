package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Group;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 11/12/15.
 */
@Repository
public interface GroupRepository extends PagingAndSortingRepository<Group, Long> {
}
