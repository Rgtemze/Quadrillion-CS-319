package pos;

import data.PurchaseInfo;
import database.DatabaseConnection;

public class Entity {
    private PurchaseInfo purchaseInfo;

    public Entity(PurchaseInfo purchaseInfo){
        this.purchaseInfo = purchaseInfo;
    }

    private void updateDatabase(){
        DatabaseConnection db = DatabaseConnection.getInstance();
    }

    public boolean doPayment(){
        boolean result = PosService.getInstance().buy(purchaseInfo);

        if(result) {
            updateDatabase();
        }
        return result;
    }
}
