package com.todo1.hulk_store.repository;

import com.todo1.hulk_store.model.Kardex;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Interface to interact whit Kardex rows saved or to save in the DB
 */
public interface KardexRepository extends CrudRepository<Kardex, Integer> {
    /**
     * Obtain a kardex card complete
     *
     * @param id product id form the kardex
     * @return Kardex card
     */
    List<Kardex> findByProduct_Id(Integer id);
}
