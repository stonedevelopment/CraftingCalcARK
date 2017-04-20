package arc.resource.calculator.util;

class PurchaseUtil {
//    private final static String TAG = PurchaseUtil.class.getSimpleName();
//
//    public static final String SKU_FEATURE_DISABLE_ADS = "feature.disable.ads";
//    public static final String SKU_FEATURE_ENABLE_FILTER_BY_STATION = "feature.enable.filter.byStation";
//    public static final String SKU_FEATURE_ENABLE_FILTER_BY_REQUIRED_LEVEL = "feature.enable.filter.byRequiredLevel";
//
//    private ActivityCheckout mCheckout;
//
//    public static List<String> mSkuList = new ArrayList<String>() {{
//        add( SKU_FEATURE_DISABLE_ADS );
//        add( SKU_FEATURE_ENABLE_FILTER_BY_STATION );
//        add( SKU_FEATURE_ENABLE_FILTER_BY_REQUIRED_LEVEL );
//    }};
//
//    public PurchaseUtil( Activity activity ) {
//        mCheckout = Checkout.forActivity( activity, MyApplication.get().getBilling() );
//    }
//
//    public void setup() {
//        mCheckout.setup();
//    }
//
//    public void pause() {
//        mCheckout.pause();
//    }
//
//    public void loadInventory( Inventory.Callback callback ) {
//        Inventory inventory = mCheckout.makeInventory();
//
//        inventory.load( Inventory.Request.create()
//                .loadAllPurchases()
//                .loadSkus( ProductTypes.IN_APP, mSkuList ), callback );
//    }
//
//    // initiate purchase flow with google's billing services
//    public void purchaseSku( String sku, EmptyRequestListener<Purchase> listener ) {
//        mCheckout.startPurchaseFlow( IN_APP, sku, null, listener );
//    }
//
//    private static class MyApplication extends Application {
//
//        private static MyApplication sInstance;
//
//        private final Billing mBilling;
//
//        public MyApplication() {
//            mBilling = new Billing( this, new Billing.DefaultConfiguration() {
//                @NonNull
//                @Override
//                public String getPublicKey() {
//                    return arc.resource.calculator.BuildConfig.PUBLIC_KEY;
//                }
//            } );
//        }
//
//        public static MyApplication get() {
//            if ( sInstance == null )
//                sInstance = new MyApplication();
//
//            return sInstance;
//        }
//
//        public Billing getBilling() {
//            return mBilling;
//        }
//    }
}
