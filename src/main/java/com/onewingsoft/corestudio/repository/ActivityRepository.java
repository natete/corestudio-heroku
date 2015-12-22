package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Activity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 05/12/15.
 */
@Repository
public interface ActivityRepository extends PagingAndSortingRepository<Activity, Long> {

    Iterable<Activity> findByGroupActivity(boolean groupActivity);
}
