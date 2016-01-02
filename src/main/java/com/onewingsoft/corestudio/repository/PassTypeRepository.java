package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Activity;
import com.onewingsoft.corestudio.model.PassType;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/12/15.
 */
@Repository
public interface PassTypeRepository extends PagingAndSortingRepository<PassType, Long> {

    Iterable<PassType> findByActivity(Activity activity);
}
