package ru.otus.homework.diyjson.forTest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Child extends Parent {
    public List<BagOfPrimitives[]> bagOfPrimitivesList = new ArrayList<>();
    private String chaildField = "childFieldVal";

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Child that = (Child) o;
        if (!this.chaildField.equals(that.chaildField)) return false;
        if (this.bagOfPrimitivesList.size()!=that.bagOfPrimitivesList.size()) return false;

        Iterator<BagOfPrimitives[]> iter1 = this.bagOfPrimitivesList.iterator();
        Iterator<BagOfPrimitives[]> iter2 = that.bagOfPrimitivesList.iterator();
        while (iter1.hasNext()) {
            BagOfPrimitives[] thisArray = iter1.next();
            BagOfPrimitives[] thatArray = iter2.next();
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
