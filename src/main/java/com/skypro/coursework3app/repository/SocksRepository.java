package com.skypro.coursework3app.repository;

import com.skypro.coursework3app.model.socks.Socks;
import com.skypro.coursework3app.model.socks.SocksBatch;

import java.util.List;
import java.util.Map;

public interface SocksRepository {

    void save(SocksBatch socksBatch);

    int remove(SocksBatch socksBatch);

    Map<Socks, Integer> getAll();

    List<SocksBatch> getList();

    void replace(List<SocksBatch> socksBatches);


}
