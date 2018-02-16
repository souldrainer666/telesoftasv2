package gildedrose;

import gildedrose.quality.*;
import org.junit.Before;
import org.junit.Test;

import static gildedrose.build.ItemBuilder.anItem;
import static gildedrose.quality.QualityControlFactory.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalculationTest {
    private static final int FIFTY_DAYS = 50;
    private static final int TWENTY_DAYS = 20;
    private static final int TEN_DAYS = 10;
    private static final int FIVE_DAYS = 5;
    private static final int ZERO_DAYS = 0;

    private AgedBrieQualityControl agedBrieQualityControl;
    private BackstagePassQualityControl qualityControl;
    private ConjuredQualityControl conjuredQualityControl;
    private QualityControlFactory factory;
    private SulfurasQualityControl sulfurasQualityControl;
    private Item conjured;
    private Item agedBrie;
    private Item backstagePass;


    @Before
    public void initialise() {
        agedBrieQualityControl = new AgedBrieQualityControl();
        qualityControl = new BackstagePassQualityControl();
        conjuredQualityControl = new ConjuredQualityControl();
        factory = new QualityControlFactory();
        sulfurasQualityControl = new SulfurasQualityControl();
        conjured = anItem().withName(CONJURED_ITEM_NAME).build();
        agedBrie = anItem().withName(AGED_BRIE_ITEM_NAME).build();
        backstagePass = anItem().withName(BACKSTAGE_PASS_ITEM_NAME).build();

    }

    @Test
    public void testOrdinaryConjuredItemDecrease() {
        conjured.setQuality(TWENTY_DAYS);
        conjured.setSellIn(10);
        conjuredQualityControl.updateQualityFor(conjured);
        assertThat(conjured.getQuality(), is(18));
    }

    @Test
    public void testMinConjuredQuality() {
        conjured.setQuality(ZERO_DAYS);
        conjured.setSellIn(10);
        conjuredQualityControl.updateQualityFor(conjured);
        assertThat(conjured.getQuality(), is(0));
    }

    @Test
    public void testOrdinaryAgedBrieQualityIncrease() {
        agedBrie.setQuality(TEN_DAYS);
        agedBrieQualityControl.updateQualityFor(agedBrie);
        assertThat(agedBrie.getQuality(), is(11));
    }

    @Test
    public void testAgedBrieMaxQuality() {
        agedBrie.setQuality(FIFTY_DAYS);
        agedBrieQualityControl.updateQualityFor(agedBrie);
        assertThat(agedBrie.getQuality(), is(50));
    }

    @Test
    public void testOrdinaryConcertQualityIncrease() {
        backstagePass.setSellIn(TWENTY_DAYS);
        backstagePass.setQuality(10);
        qualityControl.updateQualityFor(backstagePass);
        assertThat(backstagePass.getQuality(), is(11));
    }

    @Test
    public void testTenDaysBeforeConcertIncrease() {
        backstagePass.setSellIn(TEN_DAYS);
        backstagePass.setQuality(10);
        qualityControl.updateQualityFor(backstagePass);
        assertThat(backstagePass.getQuality(), is(12));
    }

    @Test
    public void testFiveDaysBeforeConcertIncrease() {
        backstagePass.setSellIn(FIVE_DAYS);
        backstagePass.setQuality(10);
        qualityControl.updateQualityFor(backstagePass);
        assertThat(backstagePass.getQuality(), is(13));
    }

    @Test
    public void testAfterConcertQuality() {
        backstagePass.setSellIn(ZERO_DAYS);
        backstagePass.setQuality(10);
        qualityControl.updateQualityFor(backstagePass);
        assertThat(backstagePass.getQuality(), is(0));
    }

    @Test
    public void testMaxConcertQuality() {
        backstagePass.setSellIn(FIVE_DAYS);
        backstagePass.setQuality(50);
        qualityControl.updateQualityFor(backstagePass);
        assertThat(backstagePass.getQuality(), is(50));
    }

    @Test
    public void legendaryItemTest() {
        Item sulfuras = anItem().withName(SULFURAS_ITEM_NAME).withQuality(20).withSellIn(10).build();
        sulfurasQualityControl.updateQualityFor(sulfuras);
        assertThat(sulfuras.getQuality(), is(20));
    }

    @Test
    public void testQualityControlFactory() {
        QualityControl qualityControl = factory.qualityControlFor(anItem().withName(AGED_BRIE_ITEM_NAME).build());
        assertThat(qualityControl, is(instanceOf(AgedBrieQualityControl.class)));
        qualityControl = factory.qualityControlFor(anItem().withName(BACKSTAGE_PASS_ITEM_NAME).build());
        assertThat(qualityControl, is(instanceOf(BackstagePassQualityControl.class)));
        qualityControl = factory.qualityControlFor(anItem().withName(CONJURED_ITEM_NAME).build());
        assertThat(qualityControl, is(instanceOf(ConjuredQualityControl.class)));
        qualityControl = factory.qualityControlFor(anItem().build());
        assertThat(qualityControl, is(instanceOf(DefaultQualityControl.class)));
        qualityControl = factory.qualityControlFor(anItem().withName(SULFURAS_ITEM_NAME).build());
        assertThat(qualityControl, is(instanceOf(SulfurasQualityControl.class)));
    }
}
