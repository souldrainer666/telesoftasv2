package hello;

import gildedrose.GildedRose;
import gildedrose.Item;
import gildedrose.quality.QualityControlFactory;
import gildedrose.sell.SellInControl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
public class Config {
    @Bean
    public GildedRose gildedRose(QualityControlFactory qualityControlFactory, SellInControl sellInControl) {
        Item[] items = new Item[]{
                new Item("+5 Dexterity Vest", 10, 20), //
                new Item("Aged Brie", 2, 0), //
                new Item("Elixir of the Mongoose", 5, 7), //
                new Item("Sulfuras, Hand of Ragnaros", 0, 80), //
                new Item("Sulfuras, Hand of Ragnaros", -1, 80),
                new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
                new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
                new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
                new Item("Conjured Mana Cake", 3, 6)};

        GildedRose app = new GildedRose(qualityControlFactory, sellInControl);

        int days = 2;

        for (int i = 0; i < days; i++) {
            for (Item item : items) {
                System.out.println(item);
            }
            app.updateQuality(Arrays.asList(items));
        }
        return app;
    }

    @Bean
    public QualityControlFactory qualityControlFactory() {
        return new QualityControlFactory();
    }

    @Bean
    public SellInControl sellInControl() {
        return new SellInControl();
    }
}
