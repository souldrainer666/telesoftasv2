package gildedrose.sell;

import gildedrose.Item;

import static gildedrose.quality.QualityControlFactory.SULFURAS_ITEM_NAME;

public class SellInControl {
    private static final int DEFAULT_DECREASE = 1;
    private static final int NO_DECREASE = 0;

    public void updateSellInFor(Item item) {
        item.setSellIn(item.getSellIn() - sellInDecreaseFor(item));
    }

    private int sellInDecreaseFor(Item item) {
        return SULFURAS_ITEM_NAME.equals(item.getName()) ? NO_DECREASE : DEFAULT_DECREASE;
    }
}
