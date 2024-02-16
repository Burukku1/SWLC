package by.dmitryskachkov.service;

import by.dmitryskachkov.repo.api.IFlatRepository;
import by.dmitryskachkov.repo.entity.Flat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class SaveService {

    private final IFlatRepository flatRepo;
    private final BlockingQueue<Flat> allFlats = new LinkedBlockingQueue<>();

    public SaveService(IFlatRepository flatRepo) {
        this.flatRepo = flatRepo;
    }

    public void putIntoSaveQueue(Flat flatEntity) {
        try {
            allFlats.put(flatEntity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Async
    @Transactional
    public void save() {
        while (true) {
            try {
                Flat flatEntity = allFlats.poll(10, TimeUnit.SECONDS);
                if (flatEntity == null) {
                    System.err.println("end of saving thread");
                    break;
                }
                Flat existingEntity = flatRepo.findById(flatEntity.getOriginalUrl()).orElse(null);

                if (existingEntity == null) {
                    flatRepo.save(flatEntity);
                    log.info("save");
                    continue;
                }

                if (!existingEntity.getPrice().equals(flatEntity.getPrice())) {
                    flatRepo.save(flatEntity);
                    log.info("update");
                }

            } catch (Throwable e) {
                System.err.println("2" + e.getMessage());
            }
        }
    }
}
