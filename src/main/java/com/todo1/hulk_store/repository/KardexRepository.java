package com.todo1.hulk_store.repository;

import com.todo1.hulk_store.model.Kardex;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface KardexRepository extends CrudRepository<Kardex, Integer> {
    List<Kardex> findByProduct_Id(Integer id);
}
