package org.revo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.revo.Domain.Index;
import org.revo.Service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TubeApplicationTests {
    @Autowired
    private IndexService indexService;

    @Test
    public void contextLoads() {
        List<Index> byMaster = indexService.findByMaster("5c0bd621e65475001023bf5f");
        for (Index index : byMaster) {
            System.out.println();
        }

    }

}
