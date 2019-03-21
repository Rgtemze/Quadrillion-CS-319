package pos;

import data.PurchaseInfo;

public class Entity {
    private PurchaseInfo purchaseInfo;

    public Entity(PurchaseInfo purchaseInfo){
        this.purchaseInfo = purchaseInfo;
    }

    private void updateDatabase(){

    }

    public boolean doPayment(){
        boolean result = PosService.getInstance().buy(purchaseInfo);

        if(result) {
            updateDatabase();
        }
        return result;
    }
}
