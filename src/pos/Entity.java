package pos;

import data.PurchaseInfo;

public class Entity {
    private PurchaseInfo purchaseInfo;

    public Entity(PurchaseInfo purchaseInfo){
        this.purchaseInfo = purchaseInfo;
    }

    private void updateDatabase(){

    }

    private boolean doPayment(){
        PosService.getInstance().buy(purchaseInfo);
        return true;
    }
}
