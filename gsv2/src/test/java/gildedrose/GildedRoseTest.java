package gildedrose;

import gildedrose.quality.DefaultQualityControl;
import gildedrose.quality.QualityControlFactory;
import gildedrose.sell.SellInControl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static gildedrose.build.ItemBuilder.anItem;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GildedRoseTest {

    @Mock
    private QualityControlFactory qualityControlFactory;
    @Mock
    private DefaultQualityControl qualityControl;
    @Mock
    private SellInControl sellInControl;
    private GildedRose gildedRose;
    private Item item2;
    private Item item1;

    @Before
    public void initialise() {
        when(qualityControlFactory.qualityControlFor(Mockito.any(Item.class))).thenReturn(qualityControl);
        gildedRose = new GildedRose(qualityControlFactory, sellInControl);

        item1 = anItem().build();
        item2 = anItem().build();
    }

    @Test
    public void shouldUpdateItemsQuality() {
        gildedRose.updateQuality(listContaining(item1, item2));
        verifyIfQualityWasUpdatedFor(item1, item2);
    }

    @Test
    public void shouldUpdateItemsSellIn() {
        gildedRose.updateQuality(listContaining(item1, item2));
        verifyIfSellInWasUpdatedFor(item1, item2);
    }

    private void verifyIfSellInWasUpdatedFor(Item... items) {
        for (Item item : items) {
            verify(sellInControl).updateSellInFor(item);
        }
    }

    private void verifyIfQualityWasUpdatedFor(Item... items) {
        for (Item item : items) {
            verify(qualityControl).updateQualityFor(item);
        }
    }

    private List<Item> listContaining(Item... items) {
        return Arrays.asList(items);
    }

}
