package guru.springframework.repositories;

import guru.springframework.domain.*;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.test.annotation.*;
import org.springframework.test.context.junit4.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    @DirtiesContext
    public void testDescription() {
        Optional<UnitOfMeasure> uomOpt = unitOfMeasureRepository.findByDescription("Tablespoon");
        assertEquals("Tablespoon", uomOpt.get().getDescription());
    }

    @Test
    public void testDescriptionPinch() {
        Optional<UnitOfMeasure> uomOpt = unitOfMeasureRepository.findByDescription("Pinch");
        assertEquals("Pinch", uomOpt.get().getDescription());
    }
}