package guru.springframework.service;

import guru.springframework.commands.*;
import guru.springframework.converters.*;
import guru.springframework.domain.*;
import guru.springframework.repositories.*;
import org.junit.*;
import org.mockito.*;

import java.util.*;

import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureService unitOfMeasureService;
    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms() {

        Set<UnitOfMeasure> set = new HashSet<>();
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(1L);
        set.add(uom1);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(2L);
        set.add(uom2);
        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setId(3L);
        set.add(uom3);

        when(unitOfMeasureRepository.findAll()).thenReturn(set);
        Set<UnitOfMeasureCommand> uomSet = unitOfMeasureService.listAllUoms();
        Assert.assertEquals(3, uomSet.size());
        verify(unitOfMeasureRepository, times(1)).findAll();
    }
}