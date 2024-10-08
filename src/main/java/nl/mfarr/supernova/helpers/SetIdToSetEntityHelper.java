package nl.mfarr.supernova.helpers;

import nl.mfarr.supernova.entities.OrderEntity;

import java.util.HashSet;
import java.util.Set;

public class SetIdToSetEntityHelper {

    //take input Set <Long> id and return Set <Entity>
    public static Set<void> setIdToSetEntity(Set<Long> id, Set <void> orders) {
        Set<OrderEntity> entitySet = new HashSet<>();
        for (Long id : id) {
            entitySet.add(function.apply(id));
        }
        return entitySet;
    }





}
