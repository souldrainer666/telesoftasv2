package gildedrose;

import gildedrose.quality.QualityControl;
import gildedrose.quality.QualityControlFactory;
import gildedrose.sell.SellInControl;

import java.util.List;

public class GildedRose {
    private Item[] items;
    private QualityControlFactory qualityControlFactory;
    private SellInControl sellInControl;

    public GildedRose(QualityControlFactory qualityControl, SellInControl sellInControl) {
        this.qualityControlFactory = qualityControl;
        this.sellInControl = sellInControl;
    }

    public void updateQuality(List<Item> items) {
        for (Item item : items) {
            udpateSellInFor(item);
            updateQualityFor(item);
        }
        this.items = (Item[]) items.toArray();
    }

    private void updateQualityFor(Item item) {
        QualityControl qualityControl = qualityControlFactory.qualityControlFor(item);
        qualityControl.updateQualityFor(item);
    }

    private void udpateSellInFor(Item item) {
        sellInControl.updateSellInFor(item);
    }

    public Item[] getItems() {
        return items;
    }
}