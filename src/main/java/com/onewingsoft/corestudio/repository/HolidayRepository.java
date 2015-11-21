package com.onewingsoft.corestudio.repository;

import com.onewingsoft.corestudio.model.Holiday;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Ignacio González Bullón - <nacho.gonzalez.bullon@gmail.com>
 * @since 21/11/15.
 */
@Repository
public interface HolidayRepository extends PagingAndSortingRepository<Holiday, Long> {

}
