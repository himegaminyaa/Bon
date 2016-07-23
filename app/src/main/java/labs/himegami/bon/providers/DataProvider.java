package labs.himegami.bon.providers;

import android.content.Context;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import labs.himegami.bon.R;
import labs.himegami.bon.enums.VoucherTypeEnum;
import labs.himegami.bon.models.BonEstablishmentModel;
import labs.himegami.bon.models.BonMenuModel;
import labs.himegami.bon.models.BonMerchantModel;
import labs.himegami.bon.models.BonUserModel;
import labs.himegami.bon.models.BonVoucherModel;
import labs.himegami.bon.utilities.components.BaseDecoder;
import labs.himegami.bon.utilities.formatting.BaseFormatUtilities;
import labs.himegami.bon.utilities.randomgenerator.RandomIntGenerator;
import labs.himegami.bon.utilities.randomgenerator.RandomStringGenerator;

/**
 * Property of Himegami Labs
 *
 * @author Roy N. Meñez
 * @version %I%, %G%
 * @since 0.0.1
 *
 * July 16, 2016
 */
public class DataProvider {

    public static BonUserModel bonUser = new BonUserModel();
    public static BonEstablishmentModel establishment = new BonEstablishmentModel();

    //region HARD CODED VALUES
    public static ArrayList<BonVoucherModel> hardCodedVouchers = new ArrayList<>();
    public static ArrayList<BonMenuModel> hardCodedMenu = new ArrayList<>();

    //region INIT
    public static void initHardCodedVouchers(Context context) {
        hardCodedVouchers = new ArrayList<>();

        BonVoucherModel entry1 = new BonVoucherModel();
        entry1.setVoucherName("Rose Shake");
        entry1.setVoucherPrice(BaseFormatUtilities.convertStringToDecimal("249.00"));
        entry1.setVoucherCurrency("PHP");
        entry1.setVoucherPromo("∞");
        entry1.setVoucherPromoCd("Unlimited");
        entry1.setVoucherImage(BaseDecoder.decodeImage(context.getResources().getDrawable(R.drawable.bon_rose_shake)));
        entry1.setVoucherType(VoucherTypeEnum.UNLIMITED);
        hardCodedVouchers.add(entry1);

        BonVoucherModel entry2 = new BonVoucherModel();
        entry2.setVoucherName("Lasagna");
        entry2.setVoucherPrice(BaseFormatUtilities.convertStringToDecimal("119.00"));
        entry2.setVoucherCurrency("PHP");
        entry2.setVoucherPromo("24%");
        entry2.setVoucherPromoCd("Discount");
        entry2.setVoucherImage(BaseDecoder.decodeImage(context.getResources().getDrawable(R.drawable.bon_lasagna)));
        entry2.setVoucherType(VoucherTypeEnum.DISCOUNT);
        hardCodedVouchers.add(entry2);

        BonVoucherModel entry3 = new BonVoucherModel();
        entry3.setVoucherName("Stuffed Mushrooms");
        entry3.setVoucherPrice(BaseFormatUtilities.convertStringToDecimal("229.00"));
        entry3.setVoucherCurrency("PHP");
        entry3.setVoucherPromo("7+2");
        entry3.setVoucherPromoCd("Buy 7 and get 2 for free");
        entry3.setVoucherImage(BaseDecoder.decodeImage(context.getResources().getDrawable(R.drawable.bon_seafood_stuffed_mushrooms)));
        entry3.setVoucherType(VoucherTypeEnum.BUNDLE);
        hardCodedVouchers.add(entry3);

        BonVoucherModel entry4 = new BonVoucherModel();
        entry4.setVoucherName("Mac and Cheese Pan");
        entry4.setVoucherPrice(BaseFormatUtilities.convertStringToDecimal("888.00"));
        entry4.setVoucherCurrency("PHP");
        entry4.setVoucherPromo("11%");
        entry4.setVoucherPromoCd("Discount");
        entry4.setVoucherImage(BaseDecoder.decodeImage(context.getResources().getDrawable(R.drawable.bon_pizza_mac_and_cheese)));
        entry4.setVoucherType(VoucherTypeEnum.DISCOUNT);
        hardCodedVouchers.add(entry4);

        BonVoucherModel entry5 = new BonVoucherModel();
        entry5.setVoucherName("Any Cocktails");
        entry5.setVoucherPrice(BaseFormatUtilities.convertStringToDecimal("199.00"));
        entry5.setVoucherCurrency("PHP");
        entry5.setVoucherPromo("1+1");
        entry5.setVoucherPromoCd("Two of any cocktail");
        entry5.setVoucherImage(BaseDecoder.decodeImage(context.getResources().getDrawable(R.drawable.bon_cocktails)));
        entry5.setVoucherType(VoucherTypeEnum.COMPANION);
        hardCodedVouchers.add(entry5);

        BonVoucherModel entry6 = new BonVoucherModel();
        entry6.setVoucherName("Dumplings");
        entry6.setVoucherPrice(BaseFormatUtilities.convertStringToDecimal("499.00"));
        entry6.setVoucherCurrency("PHP");
        entry6.setVoucherPromo("∞");
        entry6.setVoucherPromoCd("Unlimited");
        entry6.setVoucherImage(BaseDecoder.decodeImage(context.getResources().getDrawable(R.drawable.bon_dumplings)));
        entry6.setVoucherType(VoucherTypeEnum.UNLIMITED);
        hardCodedVouchers.add(entry6);

        BonVoucherModel entry7 = new BonVoucherModel();
        entry7.setVoucherName("Maccha Made in Heaven");
        entry7.setVoucherPrice(BaseFormatUtilities.convertStringToDecimal("149.00"));
        entry7.setVoucherCurrency("PHP");
        entry7.setVoucherPromo("4:1");
        entry7.setVoucherPromoCd("Buy 4 maccha cookies and get one free coffee");
        entry7.setVoucherImage(BaseDecoder.decodeImage(context.getResources().getDrawable(R.drawable.bon_maccha_made_in_heaven)));
        entry7.setVoucherType(VoucherTypeEnum.COMPANION);
        hardCodedVouchers.add(entry7);
    }

    public static void initHardCodedMenu() {
        hardCodedMenu = new ArrayList<>();



        BonMenuModel appetizerHeader = new BonMenuModel();
        appetizerHeader.setIsHeader(true);
        appetizerHeader.setTitle("Appetizers");
        hardCodedMenu.add(appetizerHeader);

        BonMenuModel appetizer1 = new BonMenuModel();
        appetizer1.setName("Bucket of Shrimps");
        appetizer1.setDesc("Experience tranquility with this pound of delectable shrimps marinated with beer and select spices.");
        appetizer1.setPrice(new BigDecimal(299));
        hardCodedMenu.add(appetizer1);

        BonMenuModel appetizer2 = new BonMenuModel();
        appetizer2.setName("Platter of Fries");
        appetizer2.setDesc("Have it special with our three-stage prep'd fries that are crispy on the outside but contrastingly fluffy in the inside.");
        appetizer2.setPrice(new BigDecimal(199));
        hardCodedMenu.add(appetizer2);

        BonMenuModel appetizer3 = new BonMenuModel();
        appetizer3.setName("Sausage Platter");
        appetizer3.setDesc("Picky with sausages? Then why not order them all? Get a taste of 12 different sausages for a very affordable price.");
        appetizer3.setPrice(new BigDecimal(349));
        hardCodedMenu.add(appetizer3);

        BonMenuModel appetizer4 = new BonMenuModel();
        appetizer4.setName("Buffalo Wings");
        appetizer4.setDesc("Hot, mild, sweet, or tangy - with a selection of 20 different sauces, we sure got you covered!");
        appetizer4.setPrice(new BigDecimal(249));
        hardCodedMenu.add(appetizer4);

//        for(int i = 0; i < 7; i++) {
//            BonMenuModel appetizer = new BonMenuModel();
//            appetizer.setName(RandomStringGenerator.alphaNumeric(RandomIntGenerator.randInt(15, 25)).toString());
//            appetizer.setDesc(RandomStringGenerator.alphaNumeric(RandomIntGenerator.randInt(100, 255)).toString());
//            appetizer.setPrice(BigDecimal.valueOf(RandomIntGenerator.randInt(79, 159)));
//            hardCodedMenu.add(appetizer);
//        }

        BonMenuModel entreeHeader = new BonMenuModel();
        entreeHeader.setIsHeader(true);
        entreeHeader.setTitle("Entrees");
        hardCodedMenu.add(entreeHeader);

        BonMenuModel entree1 = new BonMenuModel();
        entree1.setName("House Steak");
        entree1.setDesc("Certified 100% ANGUS BEEF drizzled with our trademark secret sauce and paired with seasonal vegetables.");
        entree1.setPrice(new BigDecimal(799));
        hardCodedMenu.add(entree1);

        BonMenuModel entree2 = new BonMenuModel();
        entree2.setName("Beef Caldereta Pasta");
        entree2.setDesc("Pasta prepared three ways but served as a single unforgettably delicate dish.");
        entree2.setPrice(new BigDecimal(499));
        hardCodedMenu.add(entree1);

//        for(int i = 0; i < 15; i++) {
//            BonMenuModel entree = new BonMenuModel();
//            entree.setName(RandomStringGenerator.alphaNumeric(RandomIntGenerator.randInt(20, 35)).toString());
//            entree.setDesc(RandomStringGenerator.alphaNumeric(RandomIntGenerator.randInt(150, 255)).toString());
//            entree.setPrice(BigDecimal.valueOf(RandomIntGenerator.randInt(199, 499)));
//            hardCodedMenu.add(entree);
//        }

        BonMenuModel dessertHeader = new BonMenuModel();
        dessertHeader.setIsHeader(true);
        dessertHeader.setTitle("Desserts");
        hardCodedMenu.add(dessertHeader);

        BonMenuModel dessert = new BonMenuModel();
        dessert.setName("Leche Flan");
        dessert.setDesc("Made the classic Pinoy way!");
        dessert.setPrice(new BigDecimal(99));
        hardCodedMenu.add(dessert);

//        for(int i = 0; i < 10; i++) {
//            BonMenuModel dessert = new BonMenuModel();
//            dessert.setName(RandomStringGenerator.alphaNumeric(RandomIntGenerator.randInt(17, 28)).toString());
//            dessert.setDesc(RandomStringGenerator.alphaNumeric(RandomIntGenerator.randInt(100, 255)).toString());
//            dessert.setPrice(BigDecimal.valueOf(RandomIntGenerator.randInt(49, 299)));
//            hardCodedMenu.add(dessert);
//        }


        BonMenuModel drinkHeader = new BonMenuModel();
        drinkHeader.setIsHeader(true);
        drinkHeader.setTitle("Drinks");
        hardCodedMenu.add(drinkHeader);

        BonMenuModel drink = new BonMenuModel();
        drink.setName("Ice Tea");
        drink.setDesc("Our own original house blend with a bit of a minty kick.");
        drink.setPrice(new BigDecimal(49));
        hardCodedMenu.add(drink);

//        for(int i = 0; i < 20; i++) {
//            BonMenuModel drink = new BonMenuModel();
//            drink.setName(RandomStringGenerator.alphaNumeric(RandomIntGenerator.randInt(12, 20)).toString());
//            drink.setDesc(RandomStringGenerator.alphaNumeric(RandomIntGenerator.randInt(40, 150)).toString());
//            drink.setPrice(BigDecimal.valueOf(RandomIntGenerator.randInt(29, 149)));
//            hardCodedMenu.add(drink);
//        }

    }
    //endregion

    //region HELPER FUNCTIONS
    private static ArrayList<String> getRandomizedStringList (int listSize, int stringLength) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(RandomStringGenerator.letter(stringLength).toString());
        }
        return list;
    }

    private static ArrayList<BigDecimal> getRandomizedBigDecimalList (int listSize, int min, int max) {
        ArrayList<BigDecimal> list = new ArrayList<>();
        for (int i = 0; i < listSize; i++) {
            list.add(new BigDecimal(RandomIntGenerator.randInt(min, max)));
        }
        return list;
    }
    //endregion
    //endregion

}
