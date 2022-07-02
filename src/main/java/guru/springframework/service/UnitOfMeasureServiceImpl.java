package guru.springframework.service;

import guru.springframework.commands.*;
import guru.springframework.converters.*;
import guru.springframework.repositories.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.stream.*;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    public final UnitOfMeasureRepository uomRepository;
    public final UnitOfMeasureToUnitOfMeasureCommand uomCommand;

    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand uomCommand) {
        this.uomRepository = uomRepository;
        this.uomCommand = uomCommand;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {
        Set<UnitOfMeasureCommand> set = StreamSupport
                .stream(uomRepository.findAll()
                .spliterator(), false)
                .map(uomCommand::convert)
                .collect(Collectors.toSet());
        return set;
    }
}
