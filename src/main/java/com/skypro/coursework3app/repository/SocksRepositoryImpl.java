package com.skypro.coursework3app.repository;

import com.skypro.coursework3app.model.socks.Socks;
import com.skypro.coursework3app.model.socks.SocksBatch;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Repository
public class SocksRepositoryImpl implements SocksRepository {

    private final Map<Socks, Integer> socksMap = new HashMap<>();

    @Override
    public void save(SocksBatch socksBatch) {
        Socks socks = socksBatch.getSocks();
        if (socksMap.containsKey(socks)) {
            socksMap.replace(socks, socksMap.get(socks) + socksBatch.getQuantity());
        } else {
            socksMap.put(socks, socksBatch.getQuantity());
        }

    }

    @Override
    public int remove(SocksBatch socksBatch) {
        Socks socks = socksBatch.getSocks();
        if (socksMap.containsKey(socks)) {
            int quantity = socksMap.get(socks);
            if (quantity > socksBatch.getQuantity()) {
                socksMap.replace(socks, quantity - socksBatch.getQuantity());
                return socksBatch.getQuantity();
            } else {
                socksMap.remove(socks);
                return quantity;
            }
        }
        return 0;
    }

    @Override
    public Map<Socks, Integer> getAll() {
        return socksMap;
    }

    @Override
    public List<SocksBatch> getList() {
        List<SocksBatch> socksBatches = new ArrayList<>();
        for (Map.Entry<Socks, Integer> entry : socksMap.entrySet()) {
            socksBatches.add(new SocksBatch(entry.getKey(), entry.getValue()));
        }
        return socksBatches;
    }

    @Override
    public void replace(List<SocksBatch> socksBatches) {
        socksMap.clear();
        for (SocksBatch batch : socksBatches) {
            save(batch);
        }
    }
}
