package ru.otus.homework.diyjson.forTest;

import java.util.ArrayList;
import java.util.List;


public class Child extends Parent {
    private String chaildField = "childFieldVal";
    public List<BagOfPrimitives[]> bagOfPrimitivesList = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child that = (Child) o;
        if (!this.chaildField.equals(that.chaildField)) return false;
        if (this.bagOfPrimitivesList.size()!=that.bagOfPrimitivesList.size()) return false;
        for (int i = 0; i < that.bagOfPrimitivesList.size() ; i++) {
            BagOfPrimitives[] thisArray = this.bagOfPrimitivesList.get(i);
            BagOfPrimitives[] thatArray = that.bagOfPrimitivesList.get(i);
            if (thisArray.length!=thatArray.length) return false;
            for (int j = 0; j < thisArray.length ; j++) {
                if ((thatArray[j] != null || thisArray[j] != null) &&
                        !thisArray[j].equals(thatArray[j])) return false;
            }
        }
        return true;
    }
    @Override
    public String toString() {
        return chaildField + " " + bagOfPrimitivesList.toString();
    }
}
