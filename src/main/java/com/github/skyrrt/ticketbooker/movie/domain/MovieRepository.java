package com.github.skyrrt.ticketbooker.movie.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface MovieRepository extends CrudRepository<Movie, Long> {
}
