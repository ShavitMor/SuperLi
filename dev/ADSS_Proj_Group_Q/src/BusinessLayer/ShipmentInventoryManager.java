
package BusinessLayer;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.NoSuchElementException;

public class ShipmentInventoryManager {
    ArrayList<Site> shippingOrder;
    Hashtable <Site,Hashtable<String, Integer>> siteToItemsTable;
    Hashtable<String,Double> itemToWeightTable;
    public ShipmentInventoryManager(){
        shippingOrder = new ArrayList<>();
        siteToItemsTable = new Hashtable<>();
        itemToWeightTable = new Hashtable<>();
    }

    public ShipmentInventoryManager(Hashtable<Site,Hashtable<String,Integer>> siteToItems, Hashtable<String, Double> itemWeight, ArrayList<Site> shippingOrder){
        this.shippingOrder = shippingOrder;
        this.siteToItemsTable = siteToItems;
        this.itemToWeightTable = itemWeight;
    }

    public void addSite(Site site){
        if (site.type == Site.SiteType.LogisticCenter){
            throw new IllegalArgumentException("Deliveries can not be made to Logistic Centers");
        }
        shippingOrder.add(site);
        siteToItemsTable.put(site,new Hashtable<>());
    }
    public void addSite(Site site, int order){
        removeSite(site);
        shippingOrder.add(order,site);
        siteToItemsTable.put(site,new Hashtable<>());
    }
    public void removeSite(Site site){
        clearDestinationDelivery(site);
        siteToItemsTable.remove(site);
        shippingOrder.remove(site);
    }

    public ArrayList<Site> getShippingOrder() {
        return shippingOrder;
    }

    public Hashtable<String, Double> getItemToWeightTable() {
        return itemToWeightTable;
    }

    public Hashtable<Site, Hashtable<String, Integer>> getSiteToItemsTable() {
        return siteToItemsTable;
    }

    public void setSiteToPlace(Site s, int newPlace){
        if (!shippingOrder.contains(s)){
            throw new IllegalArgumentException(" Site " + s.getName() + "is not part of current shipment!");
        }
        if(isSiteSource(s)){
            throw new IllegalArgumentException("Cannot edit source's order in shipment");
        }
        Site prev = shippingOrder.set(newPlace,s);
        Hashtable<String, Integer> items = siteToItemsTable.get(prev);
        siteToItemsTable.remove(prev);
        siteToItemsTable.put(prev,items);
    }

    public void switchSitesOrder(int first, int second){
        if (first == 0 || second == 0){
            throw new IllegalArgumentException("Cannot edit source's order in shipment");
        }
        Site a = shippingOrder.get(first);
        Site b = shippingOrder.get(second);
        shippingOrder.set(first,b);
        shippingOrder.set(second,a);
    }
    public void addItemPickUp(Site dest, String item, int amount, double weight) {
        if (isShipmentContainsItem(item) ){
            throw new IllegalArgumentException("Shipment already contains info on given item - please edit existing item!");
        }
        if (amount < 1){
            throw new IllegalArgumentException(" Amount of item must be < 0");
        }
        if (weight <= 0 ){
            throw new IllegalArgumentException(" Weight of item must be < 0");
        }
        itemToWeightTable.put(item,weight);
        Hashtable<String, Integer> destItems = siteToItemsTable.get(dest);
        destItems.put(item, amount);
        siteToItemsTable.put(dest,destItems);
    }
    public void addExistingItemPickUp(Site dest, String item, int amount) throws Exception {
        if (!isShipmentContainsItem(item) ){
            throw new Exception("Shipment does not contain info on given item - please enter new item!");
        }
        if (amount < 1){
            throw new IllegalArgumentException(" Amount of item must be < 0");
        }
        Hashtable<String, Integer> destItems = siteToItemsTable.get(dest);
        destItems.put(item, amount);
        siteToItemsTable.put(dest,destItems);
    }
    public void addItemToShip(Site dest, String item, int amount) throws Exception {
        if (!isShipmentContainsItem(item)){
            throw new Exception("Shipment does not contain info on given item - please enter new item!");
        }
        if (amount < 1){
            throw new IllegalArgumentException(" Amount of item must be < 0");
        }
        if (isSiteSource(dest)){
            throw new IllegalArgumentException("Cannot ship items to source- only to destinations");
        }
        Hashtable<String, Integer> destItems = siteToItemsTable.get(dest);
        destItems.put(item, -amount);
        siteToItemsTable.put(dest,destItems);
    }


    public boolean removeItem(Site dest, String item){
        if (!isShipmentContainsItem(item)){
            throw new IllegalArgumentException("Shipment does not contain info on given item");
        }
        if (!siteToItemsTable.containsKey(dest)){
            throw new IllegalArgumentException("Site " + dest.getName() + " is not a part of this shipment!");
        }
        if (!siteToItemsTable.get(dest).containsKey(item)){
            throw new IllegalArgumentException("Item - "+ item + " is not picked nor shipped to this destination");
        }
        Hashtable<String, Integer> destItems = siteToItemsTable.get(dest);
        destItems.remove(item);
        siteToItemsTable.put(dest,destItems);
//        if (!isItemShippedOrPicked(item)){
//            itemToWeightTable.remove(item);
//            return true;
//        }
        return false;
    }
    public boolean removeItemWithoutCheckSite(Site dest, String item){
        if (!isShipmentContainsItem(item)){
            throw new IllegalArgumentException("Shipment does not contain info on given item");
        }
        if (!siteToItemsTable.containsKey(dest)){
            throw new IllegalArgumentException("Site " + dest.getName() + " is not a part of this shipment!");
        }
        Hashtable<String, Integer> destItems = siteToItemsTable.get(dest);
        destItems.remove(item);
        siteToItemsTable.put(dest,destItems);
//        if (!isItemShippedOrPicked(item)){
//            itemToWeightTable.remove(item);
//            return true;
//        }
        return false;
    }
    public void setItemWeight(String item, double weight){
        if (!itemToWeightTable.containsKey(item)){
            throw new IllegalArgumentException("Item " + item + "does not exist in this shipment.");
        }
        if (weight <= 0 ){
            throw new IllegalArgumentException(" Weight of item must be < 0");
        }
        itemToWeightTable.put(item,weight);
    }

    public boolean isShipmentContainsItem(String item){
        return itemToWeightTable.containsKey(item);
    }

    private boolean isItemShippedOrPicked(String item){
        boolean isItem = false;
        for (Site s: shippingOrder){
            while(!isItem){
                isItem = siteToItemsTable.get(s).contains(item);
            }
        }
        return isItem;
    }
    public void isSiteGetsItems() throws Exception {
        for (Site s: shippingOrder){
            if (siteToItemsTable.get(s).isEmpty() && !isSiteSource(s)){
                throw new Exception(String.format( "WARNING - at site %d -%s, no items are picked up or shipped to!",
                        shippingOrder.indexOf(s), s.getName()));
            }
        }
    }

    public boolean isSiteGetsItems(Site s){
        return ! (siteToItemsTable.get(s)==null||siteToItemsTable.get(s).isEmpty());
    }
    public boolean isSiteSource(Site site){
        return shippingOrder.indexOf(site) == 0 && site.type == Site.SiteType.LogisticCenter;
    }


    public void checkWeightForShipment(double maxWeight) throws Exception {
        int i = isWeightOKThroughAll(maxWeight);
        if ( i > 0){
            throw new Exception(String.format("WARNING - Truck overweight at site number: %d - %s .",
                    i, shippingOrder.get(i).name));
        }
    }

    private double getWeightAtSite(int siteNum){
        double totalWeight = 0;
        for (int i = 0; i <= siteNum; i++) {
            for(Site site: shippingOrder){
                Hashtable<String, Integer> items =  siteToItemsTable.get(site);
                if(items == null){
                    items = new Hashtable<>();
                }
                for (String item: items.keySet()){
                    totalWeight += itemToWeightTable.get(item) * items.get(item);
                }
            }
        }
        return totalWeight;
    }
    private double getWeightAtSiteWithTruck(int siteNum,double truckWeight){
        double totalWeight = 0;
        for (int i = 0; i <= siteNum; i++) {
            for(Site site: shippingOrder){
                Hashtable<String, Integer> items =  siteToItemsTable.get(site);
                if(items ==null){
                    items = new Hashtable<>();
                }
                for (String item: items.keySet()){
                    totalWeight += itemToWeightTable.get(item) * items.get(item);
                }
            }
        }
        return totalWeight + truckWeight;
    }

    public int isWeightOKThroughAll(double maxWeight){
        double totalWeight = 0;
        for (int i = 0; i < shippingOrder.size(); i++) {
            Site site = shippingOrder.get(i);
            Hashtable<String, Integer> items =  siteToItemsTable.get(site);
            if (items != null){
                for (String item: items.keySet()){
                    totalWeight += itemToWeightTable.get(item) * items.get(item);
                }
                if (totalWeight > maxWeight){
                    return i;
                }
            }
        }
        return -1;
    }

    public boolean areAllItemsShipped(){
        for ( String item: itemToWeightTable.keySet()){
            if (getFinalAmountOfItem(item) > 0){
                return false;
            }
        }
        return true;
    }
    private int getFinalAmountOfItem(String item){
        int counter = 0;
        for(Site site: shippingOrder){
            Hashtable<String, Integer> items =  siteToItemsTable.get(site);
            counter += items.getOrDefault(item,0);
        }
        return counter;
    }


    public boolean areAllItemsShippedInOrder() {
        for (String item: itemToWeightTable.keySet()){
            if (!isItemShippedInOrder(item)){
                return false;
            }
        }
        return true;
    }

    private boolean isItemShippedInOrder(String item){
        int counter = 0;
        for(Site site: shippingOrder){
            Hashtable<String, Integer> items =  siteToItemsTable.get(site);
            counter += items.getOrDefault(item,0);
            if (counter < 0){
                return false;
            }
        }
        return true;
    }

    private int getItemAmountErrorSite(String item){
        int counter = 0;
        for(Site site: shippingOrder){
            Hashtable<String, Integer> items =  siteToItemsTable.get(site);
            counter += items.getOrDefault(item,0);
            if (counter < 0){
                return shippingOrder.indexOf(site);
            }
        }
        return -1;
    }

    public void clearDestinationDelivery(Site site){
        siteToItemsTable.remove(site);
    }

    public String ToStringShort(double maxWeight){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < shippingOrder.size(); i++) {
            Site s = getSite(i);
            sb.append(String.format("%d.\tname: %s\taddress: %s\n",i,s.getName(),s.getAddress()));
            sb.append(sumItemsOfSiteString(s,maxWeight));
        }
        return sb.toString();
    }

    private String sumItemsOfSiteString(Site s,double maxWeight){
        if (!shippingOrder.contains(s)){
            throw new IllegalArgumentException("given site is not part of this shipment");
        }
        StringBuilder sb =new StringBuilder();
        double weightAtSite =  getWeightAtSite(shippingOrder.indexOf(s));
        int itemsPicked = 0;
        int itemsDropped = 0;
        if(siteToItemsTable.get(s) != null){
            for (String item: siteToItemsTable.get(s).keySet()){
                if (siteToItemsTable.get(s).get(item) > 0){
                    itemsPicked += siteToItemsTable.get(s).get(item);
                }
                if (siteToItemsTable.get(s).get(item) < 0){
                    itemsDropped += siteToItemsTable.get(s).get(item);
                }
            }
        }
        sb.append(String.format("Items delivered: %d \t Items picked up: %d\n",itemsDropped,itemsPicked));
        sb.append(String.format("Load weight at Site: %1.3f\n",weightAtSite));
        if (weightAtSite > maxWeight){
            sb.append(String.format("***WARNING - weight at site %d. - %s is more than truck allowed to carry!***\n",getShippingOrder().indexOf(s),s.getName()));
            sb.append("Change to an adequate truck or remove items!\n");
        }
        return  sb.toString();

    }

    public String siteShippingToString(Site site){
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append( shippingOrder.indexOf(site) +". \t"+site.toString());
        if(siteToItemsTable.contains(site)) {
            for (String item : siteToItemsTable.get(site).keySet()) {
                sb.append(itemInfoToString(site, item));
            }
        }
        if (site == null | !site.isValidSite()){
            sb.append("***WARNING: site data is incomplete!***\n");
        }
        if (!isSiteSource(site) && !isSiteGetsItems(site)){
            sb.append("***WARNING: destination invalid - no items delivered to it!***\n");
        }
        return sb.toString();
    }
    public String siteFullShippingToString(Site site,double maxWeight,double maxDriver){
        StringBuilder sb = new StringBuilder();
        sb.append(site.toString()+"\n");
        sb.append( shippingOrder.indexOf(site) +". \t"+ site.toString());
        for (String item : siteToItemsTable.get(site).keySet()){
            sb.append(itemInfoToString(site,item));
            if (isItemHasEnoughAmountAtSite(site,item)){
                sb.append("***WARNING - not enough of this item is picked up before reaching this destination!***\n");
            }
        }
        if (site == null | !site.isValidSite()){
            sb.append("***WARNING: site data is incomplete!***\n");
        }
        if (!isSiteSource(site) && !isSiteGetsItems(site)){
            sb.append("***WARNING: destination invalid - no items delivered to it!***\n");
        }
        if(maxWeight < getWeightAtSite(shippingOrder.indexOf(site))){
            sb.append("***WARNING - weight at site is more than truck allowed to carry!***\n");
        }

        return sb.toString();
    }

    private boolean areItemsHasEnoughAmountAtSite(){
        boolean ans = true;
        for (Site s: shippingOrder){
           ans = ans & isItemHasEnoughAmountAtSite(s);
        }
        return ans;
    }
    private boolean isItemHasEnoughAmountAtSite(Site s) {
        boolean ans = true;
        if (siteToItemsTable.get(s) == null){
            return true;
        }
        for (String item: siteToItemsTable.get(s).keySet()){
            ans = ans & isItemHasEnoughAmountAtSite(s, item);
        }
        return ans;
    }

    private boolean isItemHasEnoughAmountAtSite(Site s,String item){
        int amount = 0;
        for (int i = 0; i <= shippingOrder.indexOf(s); i++) {
            Site curr = shippingOrder.get(i);
           amount += siteToItemsTable.get(curr) != null ?  siteToItemsTable.get(curr).containsKey(item) ?  siteToItemsTable.get(curr).get(item) : 0:0;
        }
        return amount > 0;
    }

    public String siteShippingToStringWithInfo(Site site, double maxTruck, double maxDriver){
        StringBuilder sb = new StringBuilder();
        sb.append( shippingOrder.indexOf(site) +". \t"+ site.toString());
        if(siteToItemsTable.contains(site)) {
            for (String item : siteToItemsTable.get(site).keySet()) {
                sb.append(itemInfoToString(site, item));
                if (isItemHasEnoughAmountAtSite(site, item)) {
                    sb.append("***WARNING - not enough of this item is picked up before reaching this destination!***\n");
                }
            }
        }
        if (site == null || !site.isValidSite()){
            sb.append("***WARNING: site data is incomplete!***\n");
        }
        if (!isSiteSource(site) && !isSiteGetsItems(site)){
            sb.append("***WARNING: destination invalid - no items delivered to it!***\n");
        }
        if(getWeightAtSite(shippingOrder.indexOf(site)) > maxTruck){
            sb.append("***WARNING: Truck overweight at destination " +shippingOrder.indexOf(site)+". "+site.getName() + " -remove/drop items or change truck***\n");
        }
        if(getWeightAtSite(shippingOrder.indexOf(site)) > maxDriver){
            sb.append("***WARNING: driver not allowed to drive with current weight - remove/drop items or change driver/truck***\n");
        }
        return sb.toString();
    }

    public String itemInfoToString(Site site,String itemName) {
        return String.format("%s:\t%d\t%1.3f\t%1.3f \n",
                itemName, getSiteToItemsTable().get(site).getOrDefault(itemName,0),
                itemToWeightTable.get(itemName),getTotalDeliveryWeight(site,itemName));
    }

    public double getTotalDeliveryWeight(Site site, String item){
        final Integer none = 0;
        Integer amount = getSiteToItemsTable().get(site).getOrDefault(item,none);
        return amount*itemToWeightTable.get(item);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Site site : shippingOrder){
            sb.append(siteShippingToString(site));
            sb.append("Weight after leaving site " + site.getName() +": " + getWeightAtSite(shippingOrder.indexOf(site)) +"\n\n");
        }
        return sb.toString();
    }

    public String toStringWithInfo(double maxTruckWeight, double maxDriverWeight){
        StringBuilder sb = new StringBuilder();
        for (Site site : shippingOrder){
            sb.append(siteShippingToString(site));
            sb.append("Weight after leaving site " + site.getName() +": " + getWeightAtSite(shippingOrder.indexOf(site)) +"\n\n");
            if (! (isWeightOKThroughAll(maxTruckWeight) <0 ) ){
                sb.append("WARNING - TRUCK overweight at current site!\n \t- remove items from shipment or drop them at site before, or switch to valid truck!");
            }
            if(!(isWeightOKThroughAll(maxDriverWeight) < 0 )){
                sb.append("WARNING - Driver is not allowed do drive with weight at this site!\n \t- remove items from shipment or drop them at site before, or switch to valid driver!");
            }
        }
        return sb.toString();
    }
    public String toShortString(){
        StringBuilder sb = new StringBuilder();
        for (Site s:
             shippingOrder) {
            sb.append( shippingOrder.indexOf(s)+".\t" + s.toString() + "total delivery weight at site:"+ getWeightAtSite(shippingOrder.indexOf(s)) + "\n");
        }
        return sb.toString();
    }


    public void setSource(Site s) {
        if(s.getType() != Site.SiteType.LogisticCenter){
            throw new IllegalArgumentException("Source must be a Logistic Center!");
        }
        addSite(s,0);
    }

    public Site getSite(int order){
        return shippingOrder.get(order);
    }

    public boolean removeUnViableDestination(){
        for (Site d: shippingOrder){
            if (!d.isValidSite()){
                removeSite(d);
                return true;
            }
        }
        return false;
    }

    public boolean isValidShipmentData(){
        if (shippingOrder.isEmpty()){
            return false;
        }
        for (Site s: shippingOrder){
            if (s==null | !s.isValidSite() | !isSiteGetsItems(s)){
                return false;
            }
        }
        return true;
    }

    public String shipmentItemString(String itemName){
        if (!isShipmentContainsItem(itemName)){
            return "Shipment does not contain" + itemName;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Item: %s\t Weight of 1: %d\n", itemName, itemToWeightTable.get(itemName)));
        for (Site s: shippingOrder){
            if (isSiteGetsItems(s)){
                int amount = siteToItemsTable.get(s).get(itemName);
                sb.append(String.format("At site %s : \t %d\n", s.getName(),amount));
            }
        }
        return sb.toString();
    }

    public String siteItemsToString(Site s){
        StringBuilder sb = new StringBuilder();
        sb.append("\tItem Name: \t Amount:\n");
        String toCheck = sb.toString();
        if(siteToItemsTable.get(s) != null){
            for (String item: siteToItemsTable.get(s).keySet()){
                sb.append(String.format("\t%s \t %d\n",item,siteToItemsTable.get(s).get(item)));
            }
        }
        if(sb.toString().equals(toCheck)){
            return "No items delivered to/from " +s.getName();
        }
        sb.append("\n");
        return sb.toString();
    }

    public double getShippingDriveTime(){
        double time = 0;
        final int speedKMH = 60;
        Site.Coordinate prev = null;
        for (Site s: shippingOrder){
            if (prev != null){
                time += prev.getDistance(s.getLocation()) / speedKMH;
            }
            prev = s.getLocation();
        }
        return time;
    }

    public LocalTime getShippingDriveTimeInHours(){
        double time  = getShippingDriveTime();
        int hours = 0;
        while ( time >= 1){
            time -=1;
            hours +=1;
        }
        double minutes = hours * 60;
        return LocalTime.of(hours,(int)minutes);
    }
    public double getMaxShipmentWeight(double truckWeight){
        double max = 0;
        for (Site s :getShippingOrder()){
            double curr = getWeightAtSiteWithTruck(shippingOrder.indexOf(s),truckWeight);
            if (curr>max){
                max = curr;
            }
        }
        return max;
    }
    public double getMaxShipmentWeight() {
        double max = 0;
        for (Site s : getShippingOrder()) {
            double curr = getWeightAtSite(shippingOrder.indexOf(s));
            if (curr > max) {
                max = curr;
            }
        }
        return max;
    }

    public void updateSite(Site newSite, String oldName) {
        for (Site s: shippingOrder){
            if (s.name == oldName){
                shippingOrder.set(shippingOrder.indexOf(s),newSite);
            }
        }
        for (Site s: siteToItemsTable.keySet()){
            if (s.name == oldName){
                Hashtable<String,Integer> itemsOfSite= siteToItemsTable.get(s);
                siteToItemsTable.remove(s);
                siteToItemsTable.put(newSite,itemsOfSite);
            }
        }
    }

    public void addDeliverableItem(String itemName, double weight){
        if (isShipmentContainsItem(itemName) ){
            throw new IllegalArgumentException("Shipment already contains info on given item!");
        }
        if (weight <= 0 ){
            throw new IllegalArgumentException(" Weight of item must be > 0");
        }
        itemToWeightTable .put(itemName,weight);
    }

    public void addItemPickUp(int siteOrder,String itemName,int amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Amount of item must be > 0");
        }
        if(!itemToWeightTable.containsKey(itemName)){
            throw new NoSuchElementException("Item must be added to shipment before adding pick up/drop off!");
        }
        Site s = shippingOrder.get(siteOrder);
        Hashtable<String,Integer> prev = siteToItemsTable.get(s);
        if(prev == null){
            prev = new Hashtable<>();
        }
        prev.put(itemName,amount);
        siteToItemsTable.put(s,prev);
    }
    public void addItemDrop(int siteOrder,String itemName,int amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Amount of item must be > 0");
        }
        if(!itemToWeightTable.containsKey(itemName)){
            throw new NoSuchElementException("Item must be added to shipment before adding pick up/drop off!");
        }
        Site s = shippingOrder.get(siteOrder);
        Hashtable<String,Integer> prev = siteToItemsTable.get(s);
        if(prev == null){
            prev = new Hashtable<>();
        }
        prev.put(itemName,-amount);
        siteToItemsTable.put(s,prev);
    }
    public boolean isSourceViable(){
        return shippingOrder.size()>0 ? shippingOrder.get(0).getType()== Site.SiteType.LogisticCenter : false;
    }

    public boolean removeIrrelevantItems(){
        boolean areItemRemoved = false;
        for (String item :itemToWeightTable.keySet()){
            if (!isItemShippedOrPicked(item)){
                itemToWeightTable.remove(item);
                areItemRemoved = true;
            }
        }
        return areItemRemoved;
    }

    public double getShippingDriveTimeToSite(Site dest){
        double time = 0;
        final int speedKMH = 60;
        Site.Coordinate prev = null;
        for (Site s: shippingOrder){
            if (prev != null){
                time += prev.getDistance(s.getLocation()) / speedKMH;
            }
            prev = s.getLocation();
            if(s == dest){
                return time;
            }
        }
        throw new IllegalArgumentException("Site is not in this shipment!");
    }

    public void removeItem(String itemName){
        for (Site s: shippingOrder) {
            removeItem(s, itemName);
        }
        if(itemToWeightTable.containsKey(itemName)){
            itemToWeightTable.remove(itemName);
        }
    }
    public void removeItemWithoutChecks(String itemName){
        for (Site s: shippingOrder) {
            removeItemWithoutCheckSite(s, itemName);
        }
        if(itemToWeightTable.containsKey(itemName)){
            itemToWeightTable.remove(itemName);
        }
    }

    public String getFullSiteString(int destOrder,double maxWeight) {
        StringBuilder sb = new StringBuilder();
        sb.append(getSite(destOrder).toString()+"\n");
        sb.append("delivered items:\n\n");
        sb.append(siteItemsToStringWithError(getSite(destOrder),maxWeight));
        return sb.toString();
    }

    private String siteItemsToStringWithError(Site site, double maxWeight) {
        String res = siteItemsToString(site);
        if (getWeightAtSite(shippingOrder.indexOf(site))>maxWeight){
            res += "***WARNING - weight at site is more than truck allowed to carry!***\nChange to an adequate truck or remove items!\n";
        }
        return res;
    }

    public String ItemsToString() {
        int index = 1;
        StringBuilder sb = new StringBuilder();
        sb.append("id: item: \t weight (kg):\n");
        for (String item: itemToWeightTable.keySet()){
            sb.append(String.format("%d. %s \t %3.3f \n",index,item,itemToWeightTable.get(item)));
        }
        sb.append("\n");
        return sb.toString();
    }

    public boolean isSourceNull() {
        return shippingOrder.size() < 1 || shippingOrder.get(0) == null || !shippingOrder.get(0).isValidSite();
    }

    public String getWeightReport(double truckMax, double driverMax) {
        StringBuilder sb = new StringBuilder();
        sb.append("Max weight of shipment : "+ getMaxShipmentWeight() +"kg\n");
        if(getMaxShipmentWeight() > truckMax){
            sb.append("***WARNING - weight at site is more than truck allowed to carry!***\n");
        }
        if(getMaxShipmentWeight(truckMax) > driverMax){
            sb.append("***WARNING - weight at site is more than driver allowed to drive!***\n");
        }
        return sb.toString();
    }

    public void setOrder(ArrayList<Site> shippingOrder) {
        this.shippingOrder = shippingOrder;
    }

    public void addItem(int order, String item, Integer integer) {
        Site s = getSite(order);
        if(integer != 0){
            if(siteToItemsTable.get(s) == null){
                Hashtable<String,Integer> itemHash = new Hashtable<>();
                itemHash.put(item,integer);
                siteToItemsTable.put(s,itemHash);
                return;
            }
            siteToItemsTable.get(s).put(item,integer);
        }
    }

    public String getItemsStringWithAmount() {
        StringBuilder sb = new StringBuilder();
        int index = 1;
        System.out.println("Item\tWeight for unit\tAmount Delivered:\n");
        for (String item: itemToWeightTable.keySet()){
            int amount  = 0;
            for (Site s: shippingOrder){
                if(siteToItemsTable.contains(s)&&siteToItemsTable.get(s).containsKey(item) && siteToItemsTable.get(s).get(item)>0){
                    amount += siteToItemsTable.get(s).get(item);
                }
            }
            sb.append(String.format("%d. %s \t %3.3f\t %d\n",index++,item,itemToWeightTable.get(item),amount));
        }
        return sb.append("\n").toString();
    }

    public boolean areOtherSitesThanSource(){
        return shippingOrder.size() > 1;
    }

    public String[][] getRouteInfo() {
        String[][] out = new String[shippingOrder.size()][5];
        for (int i = 0; i < shippingOrder.size(); i++) {
            Site s = shippingOrder.get(i);
            String[] arr = {""+i,s.getName(),s.getType().toString(),getChangedItemsAmount(i),getWeightAtSite(i)+"" };
            out[i] = arr;
        }
        return out;
    }
    
    private String getChangedItemsAmount(int order){
        Site s = getSite(order);
        int up = 0;
        int down = 0;
        for (String item: itemToWeightTable.keySet()){
            if(siteToItemsTable.get(s) != null && siteToItemsTable.get(s).containsKey(item)){
                if(siteToItemsTable.get(s).get(item) >0){
                    up+=siteToItemsTable.get(s).get(item);
                }
                else {
                    down+= (-siteToItemsTable.get(s).get(item));
                }
            }
        }
        return up+" / "+down;
    }

    public String[][] getItemsInfo() {
        String[][] out = new String[itemToWeightTable.keySet().size()][4];
        int counter = 0;
        for (String item: itemToWeightTable.keySet()) {
            String[] arr = {item,String.valueOf(itemToWeightTable.get(item)),""+getTotalShippedAmount(item),
                    ""+getTotalPickedAmount(item)};
            out[counter] = arr;
            counter++;
        }
        return out;

    }

    private int getTotalPickedAmount(String item) {
        int amount = 0;
        for(Site s: shippingOrder){
            if (siteToItemsTable.get(s) != null && siteToItemsTable.get(s).containsKey(item)){
                int i =  siteToItemsTable.get(s).get(item);
                amount += i>0 ?  i : 0;
            }
        }
        return amount;
    }

    private int getTotalShippedAmount(String item) {
        int amount = 0;
        for(Site s: shippingOrder){
            if (siteToItemsTable.get(s) != null && siteToItemsTable.get(s).containsKey(item)){
               int i =  siteToItemsTable.get(s).get(item);
               amount += i<0 ?  -i : 0;
            }
        }
        return amount;
    }

    public void removeFromAll(String item) {
        if(!itemToWeightTable.containsKey(item)){
            throw new IllegalArgumentException("item: " + item+" is not in this shipment!");
        }
        if(siteToItemsTable== null){
            return;
        }
        for (Site s: siteToItemsTable.keySet()){
            if (siteToItemsTable.get(s) != null ){
                siteToItemsTable.get(s).remove(item);
            }
        }
        itemToWeightTable.remove(item);
    }

    public String getErrorsAtSite(int order,double truckMax,double truckWeight, double driverMax) {
        String errors = "";
        Site s = getSite(order);
        if(!isItemHasEnoughAmountAtSite(s)){
            errors = "***WARNING -- not enough arriving items!***";
        }
        if(getMaxShipmentWeight() > truckMax){
            errors = ("***WARNING - weight at site is more than truck allowed to carry!***\n");
        }
        if(getMaxShipmentWeight(truckWeight) > driverMax){
            errors = ("***WARNING - weight at site is more than driver allowed to drive!***\n");
        }
        return errors;
    }

    public String[][] getSiteItemsInfo(int order) {
        Site s = getSite(order);
        String[][] arr;
        if(siteToItemsTable.get(s)==null){
            arr = new String[0][4] ;
            return arr;
        }
        int counter = 0;
        arr = new String[siteToItemsTable.get(s).size()][4];
        for (String item : siteToItemsTable.get(s).keySet()){
            String [] itemIn = {item,""+siteToItemsTable.get(s).get(item),""+getAmountOfItemAtSite(item,order-1),
                    ""+getAmountOfItemAtSite(item,order) };
            arr[counter] = itemIn;
            counter++;
        }
        return arr;

    }

    private int getAmountOfItemAtSite(String item,int order){
        int amount = 0;
        for (int i = 0; i < order; i++) {
            Site s = shippingOrder.get(i);
            amount += siteToItemsTable.get(s) == null || siteToItemsTable.get(s).get(item) == null ?
                    0 : siteToItemsTable.get(s).get(item);
        }
        return amount;


    }

    public boolean isSiteGetItem(int order,String item) {
        boolean b = siteToItemsTable.get(getSite(order))!=null && siteToItemsTable.get(getSite(order)).get(item)!=null;
        return b;
    }
}

