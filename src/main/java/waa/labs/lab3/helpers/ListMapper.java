package waa.labs.lab3.helpers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListMapper<T,E> {
    ModelMapper modelMapper;

    @Autowired
    ListMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<E> mapList(List<T> sourceList, Class<E> targetClass) {
        return sourceList.stream()
                .map(entity -> modelMapper.map(entity, targetClass))
                .collect(Collectors.toList());
    }
}
