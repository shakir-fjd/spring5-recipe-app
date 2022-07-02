package guru.springframework.service;

import guru.springframework.commands.*;

import java.util.*;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureCommand> listAllUoms();
}
