package BusinessLayer;


import java.util.ArrayList;
import java.util.HashMap;
import DataLayer.DTOs.SiteDTO;
import DataLayer.SiteMapper;

import java.util.List;

public class SiteManager {
    HashMap<Integer, Site> sites;
    private static int siteID;
    private static SiteManager instance;
    private SiteMapper siteMapper;

    private SiteManager(HashMap<Integer,Site> sites, int currId){
        this.sites = sites;
        siteID = currId;
        instance=this;
    }

    private SiteManager(){
        this.sites = new HashMap<>();
        siteID = 1;
        instance=this;
        siteMapper=new SiteMapper();
    }

    public static SiteManager getInstance(){
        if(instance==null)
            instance=new SiteManager();
        return instance;
    }
    public void loadSites(){
        List<SiteDTO> siteDTOS=siteMapper.getAllSites();
        for(SiteDTO siteDTO:siteDTOS){
            Site site=new Site(siteDTO);
            addSiteAndUpdateStaticId(site,Integer.parseInt(siteDTO.id));
        }
    }
    public void addSite(Site site){
        if (isSiteAlreadyRegistered(site)){
            throw new IllegalArgumentException("Site already registered.");
        }
        if(siteMapper.insertSite(String.valueOf(siteID),site.getName(),site.address,site.contactName,site.contactPhone,
                site.getType().toString(),site.getLocation().getX(),site.getLocation().getY(),site.isActive)){
            sites.put(siteID++,site);
        }
    }

    /**
     * Used to add sites from db - only for sites that already have an id!
     * @param site - the site to add
     * @param id - the id of the site
     */
    private void addSite(Site site, int id){
        if(sites.containsValue(site) || sites.containsKey(id)){
            throw new IllegalArgumentException("Id or site already exists");
        }
        sites.put(id,site);
    }

    /**
     * Used to add sites from db and updates current id if higher - only for sites that already have an id!
     *
     * @param site
     * @param id
     * @return true if siteID was updated
     */
    public boolean addSiteAndUpdateStaticId(Site site, int id){
        sites.put(id,site);
       return checkAndSetId(id);
    }

    private boolean checkAndSetId(int id){
        if (siteID <= id){
            siteID = id+1;
            return true;
        }
        return false;
    }

    public Site getSite(String name){
        for (Integer i: sites.keySet()){
            if (sites.get(i).getName().equals(name)){
                return sites.get(i);
            }
        }
        throw new IllegalArgumentException("Site with name "+ name +" does not exist!");
    }

    public int getSiteIDFromName(String name){
        for (Integer i: sites.keySet()){
            if (sites.get(i).getName().equals(name)){
                return i;
            }
        }
        throw new IllegalArgumentException("Site with name "+name+" does not exist!");
    }


    public void addNewSite(String name, String address, String cName, String cPhone, int xCord, int yCord, Site.SiteType type){
        if (!checkName(name)){
            throw new IllegalArgumentException(String.format("Site with name %s already registered", name));
        }
        Site site = new Site(name,address,cName,cPhone,xCord,yCord,type);
        if (isSiteAlreadyRegistered(site)){
            throw new IllegalArgumentException("Site already registered.");
        }
        if(siteMapper.insertSite(String.valueOf(siteID),site.getName(),site.address,site.contactName,site.contactPhone,
                site.getType().toString(),site.getLocation().getX(),site.getLocation().getY(),site.isActive)){
            sites.put(siteID++,site);
        }
    }
    private boolean isSiteAlreadyRegistered(Site s){
        return sites.containsValue(s);
    }

    private HashMap <Integer,Site> getAllOfType(Site.SiteType type){
        HashMap<Integer,Site> res = new HashMap<>();
        for (Integer i : sites.keySet()){
            Site s = sites.get(i);
           if( s.type == type)
               res.put(i,s);
        }
        return res;
    }

    public HashMap <Integer,Site> getAllBranches(){
        return getAllOfType(Site.SiteType.Branch);
    }

    public HashMap <Integer,Site> getAllSuppliers(){
        return getAllOfType(Site.SiteType.Supplier);
    }
    public HashMap <Integer,Site> getAllLogisticCenters(){
        return getAllOfType(Site.SiteType.LogisticCenter);
    }
    public HashMap <Integer,Site> getAllElse(){
        return getAllOfType(Site.SiteType.LogisticCenter);
    }
    public Site getSite(int id){
        if (sites.get(id) == null){
            throw new IllegalArgumentException("Site with given ID does not exist!");
        }
        return sites.get(id);
    }

    public HashMap <Integer,Site> getBranchesByName(String name){
        HashMap<Integer,Site> res = getAllBranches();
       for (Integer i: res.keySet() ){
           Site s = sites.get(i);
           if( !s.getName().equals(name)){
               res.remove(i);
           }
       }
       return res;
    }

    public HashMap <Integer,Site> getBranchesByAddress(String address){
        HashMap<Integer,Site> res = getAllBranches();
        for (Integer i: res.keySet() ){
            Site s = sites.get(i);
            if( !s.getAddress().equals(address)){
                res.remove(i);
            }
        }
        return res;
    }

    public Site getSite(String name, String address) throws Exception {
        for(Site s: sites.values()){
            if (s.getName().equals(name) && s.getAddress().equals(address)){
                return s;
            }
        }
        throw new Exception(String.format("Site with name %s and address %s was not found!", name, address));
    }

    public Site getFirstMatchingBranchByName(String name) {
        for (Site s: sites.values()){
            if (s.getName().equals(name)){
                return s;
            }
        }
        throw new IllegalArgumentException(String.format("branch with name %s does not exist.", name));
    }

    public Site getFirstMatchingBranchByAddress(String address) throws Exception {
        for (Site s: sites.values()){
            if (s.getAddress().equals(address)){
                return s;
            }
        }
        throw new Exception(String.format("Branch with address %s does not exist.", address));
    }

    public HashMap<Integer,Site> FilterByTypeAndName(String name, Site.SiteType type) throws Exception {
        HashMap <Integer,Site> res = getAllOfType(type);
        for (Integer i: res.keySet()){
            Site s = res.get(i);
            if (!s.getName().equals(name)){
                res.remove(i);
            }
        }
        if (res.isEmpty()){
            throw new Exception(String.format("%s with name %s does not exist.", type.toString(),name));
        }
        return res;
    }

    public HashMap<Integer,Site> getActiveSites(){
        HashMap<Integer,Site> res = new HashMap<>();
        for (Integer i: sites.keySet()){
            if (sites.get(i).isActive()){
                res.put(i,sites.get(i));
            }
        }
        return res;
    }

    public HashMap<Integer,Site> getActiveSitesByType(Site.SiteType type){
        HashMap<Integer,Site> out = new HashMap<>();
        HashMap<Integer,Site> res = getAllOfType(type);
        for (Integer i: res.keySet()){
            if (res.get(i).isActive()){
                out.put(i,res.get(i));
            }
        }
        return out;
    }

    public HashMap<Integer,Site> getActiveSitesByTypeAndName(Site.SiteType type, String name) {
        HashMap<Integer,Site> res = getAllOfType(type);
        for (Integer i: sites.keySet()){
            if (sites.get(i).isActive() && sites.get(i).getName().equals(name)){
                res.put(i,sites.get(i));
            }
        }
        if (res.isEmpty()){
            throw new IllegalArgumentException(String.format("Active %s with name %s does not exist.", type.toString(),name));
        }
        return res;
    }

    public Site getActiveBranchByName(String name){
        HashMap<Integer,Site> res = getActiveSitesByTypeAndName(Site.SiteType.Branch,name);
        if (res.isEmpty()){
            throw new IllegalArgumentException(String.format("Active branch with name %s does not exist.",name));
        }
        return res.values().iterator().next();
    }

    public boolean checkName(String name){
        for(Integer i: sites.keySet()){
            if (sites.get(i).getName().equals(name)){
                return false;
            }
        }
        return true;
    }
    public List<String> getAllBranchesNames(){
        List<String> branches=new ArrayList<>();
        for(Site site: sites.values()){
            if(site.type== Site.SiteType.Branch)
                branches.add(site.name);
        }
        return branches;
    }

    public void deleteForTests(){
        instance=null;
        siteMapper.deleteAll();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:\tName:\t type:\t address:\n");
        for (Integer i: sites.keySet()){
            Site s = sites.get(i);
            sb.append(String.format("%d\t%s\t%s\t%s\n",i,s.getName(),s.getType().toString(),s.getAddress() ));
        }
        String res = sb.toString();
        res = res.equals("ID:\tName:\t type:\t address:\n") ? ("No sites listed!\n") :res + "\n";
        return res;
    }

    public String toStringByType(Site.SiteType type ) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:\tName:\t type:\t address:\n");
        for (Integer i: sites.keySet()){
            Site s = sites.get(i);
            if(s.type == type){
                sb.append(String.format("%d\t%s\t%s\t%s\n",i,s.getName(),s.getType().toString(),s.getAddress() ));
            }
        }
        String res = sb.toString();
        res = res.equals("ID:\tName:\t type:\t address:\n") ? ("No sites listed!\n") :res + "\n";
        return res;
    }

    public String toStringByActive(boolean isActive ) {
        StringBuilder sb = new StringBuilder();
        sb.append("ID:\tName:\t type:\t address:\n");
        for (Integer i: sites.keySet()){
            Site s = sites.get(i);
            if(s.isActive == isActive){
                sb.append(String.format("%d\t%s\t%s\t%s\n",i,s.getName(),s.getType().toString(),s.getAddress() ));
            }
        }
        String res = sb.toString();
        res = res.equals("ID:\tName:\t type:\t address:\n") ? ("No sites listed!\n") :res + "\n";
        return res;
    }

    public String toStringByType(String type ){
        return toStringByType(Site.getTypeFromString(type));
    }

    public String getSiteString(int sId){
        return getSite(sId).toString();
    }
    public int getSiteAmount(){
        return sites.size();
    }

    public int addNewSiteReturnId(String name, String address, String cName, String cPhone, String sCoordinate, String type) {
        String[] cords = sCoordinate.split(",");
        addSite(new Site(name,address,cName,cPhone,Integer.parseInt(cords[0]),Integer.parseInt(cords[1]),Site.getTypeFromString(type)));
        return siteID-1;
    }

    public int addNewSiteReturnIdWithExceptions(String name, String address, String cName, String cPhone, String sCoordinate, String type) {
        addSiteExceptionsCheck(name, address, cName, cPhone, sCoordinate, type);
        String[] cords = sCoordinate.split(",");
        addSite(new Site(name,address,cName,cPhone,Integer.parseInt(cords[0]),Integer.parseInt(cords[1]),Site.getTypeFromString(type)));
        return siteID-1;
    }

    private void addSiteExceptionsCheck(String name, String address, String cName, String cPhone, String sCoordinate, String type) {
        String [] arr ={name, address, cName, cPhone, sCoordinate, type};
        for(String s: arr){
            if(s == null || s.isBlank()){
                throw new IllegalArgumentException("Fields cannot be left empty or blank!");
            }
        }
        if(!isValidName(name)){
            throw new IllegalArgumentException("Name can only contain numbers and letters!");
        }
        if(!isSiteNameNotTaken(name)){
            throw new IllegalArgumentException("Name "+ name + "is already taken, please select different name!");
        }
        if(!isValidPhone(cPhone)){
            throw new IllegalArgumentException("Phone must be of length > 2 and can contain only numbers!");
        }
        if(!isValidCoordinate(sCoordinate)){
            throw new IllegalArgumentException("X adn Y coordinates can be only numbers!");
        }
    }

    public boolean isSiteNameNotTaken(String name){
        for (Integer i: sites.keySet()){
            if(sites.get(i).getName().equals(name)){
                return false;
            }
        }
        return true;
    }
    public boolean isValidName(String name){
        if (name.isBlank()){
            return false;
        }
        return name.matches("[a-zA-Z0-9 ]+");
    }

    public boolean isValidPhone(String number){
        if (number.isBlank() || number.length()<3){
            return false;
        }
        for (int i = 0; i<number.length();i++){
            if((number.charAt(i) <48 || number.charAt(i)>57)){
                return false;
            }
        }
        return true;
    }

    public boolean isValidCoordinate(String cords){
        String[] arr= cords.split(",");
        Integer.parseInt(arr[0]);
        Integer.parseInt(arr[1]);
        return true;
    }
    public void setSiteName(int id, String newName){
        if (!isValidName(newName)){
            throw new IllegalArgumentException("Please enter valid site name!");
        }
        if (isSiteNameNotTaken(newName)){
            throw new IllegalArgumentException("Site with same name already exists!");
        }
        if(!getSite(id).isActive){
            throw new RuntimeException("Cannot edit inactive sites!");
        }
        if(siteMapper.updateSite(String.valueOf(id),"name",newName)){
            getSite(id).setName(newName);
        }
    }
    public void setSiteAddress(int id, String userIn) {
        if(!getSite(id).isActive){
            throw new RuntimeException("Cannot edit inactive sites!");
        }
        if(siteMapper.updateSite(String.valueOf(id),"address",userIn)){
            getSite(id).setAddress(userIn);
        }
    }

    public void setSiteContactName(int id, String userIn) {
        if(!isValidName(userIn)){
            throw new IllegalArgumentException("Invalid site contact name!");
        }
        if(!getSite(id).isActive){
            throw new RuntimeException("Cannot edit inactive sites!");
        }
        if(siteMapper.updateSite(String.valueOf(id),"contactName",userIn)) {
            getSite(id).setContactName(userIn);
        }
        throw new RuntimeException("Error while changing site data");
    }

    public void setSiteContactPhone(int id, String userIn) {
        if (!isValidPhone(userIn)){
            throw new IllegalArgumentException("Please enter valid Phone number!");
        }
        if(!getSite(id).isActive){
            throw new RuntimeException("Cannot edit inactive sites!");
        }
        if(siteMapper.updateSite(String.valueOf(id),"contactPhone",userIn)) {
            getSite(id).setContactPhone(userIn);

        }
    }

    public void setSiteCoordinate(int id, String userIn) {
        if (!isValidCoordinate(userIn)){
            throw new IllegalArgumentException("Please enter valid values!");
        }
        if(!getSite(id).isActive){
            throw new RuntimeException("Cannot edit inactive sites!");
        }
        String[] cords = userIn.split(",");
        if(siteMapper.updateSite(String.valueOf(id),Integer.parseInt(cords[0]),Integer.parseInt(cords[1]))) {
            getSite(id).setLocation(Integer.parseInt(cords[0]),Integer.parseInt(cords[1]));
        }
    }

    public void setSiteType(int id, String userIn) {
        if(!getSite(id).isActive){
            throw new RuntimeException("Cannot edit inactive sites!");
        }
        if(siteMapper.updateSite(String.valueOf(id),"type",userIn)){
            getSite(id).setTypeFromString(userIn);
        }
    }

    public void deactivateSite(int siteId) {
        if (!getSite(siteId).isActive){
            throw new ArithmeticException("Site is already inactive!");
        }
        if(siteMapper.updateSite(String.valueOf(siteId),"active",false)){
            getSite(siteId).deactivateSite();
        }
    }


    public String[][] getSiteTable(String filter,String viewInactive) {
        boolean filterInactive = !Boolean.valueOf(viewInactive);
        HashMap<Integer,Site> filteredList = filterInactive? getActiveSites() : sites;
        switch(filter){
            case "Branch":
                filteredList = filterInactive? getActiveSitesByType(Site.SiteType.Branch) : getAllBranches();
                break;
            case "Supplier":
                filteredList = filterInactive? getActiveSitesByType(Site.SiteType.Supplier) : getAllSuppliers();
                break;
            case "Logistic Center":
                filteredList = filterInactive? getActiveSitesByType(Site.SiteType.LogisticCenter) : getAllLogisticCenters();
                break;
        }
        String[][] arr = new String[filteredList.size()][4];
        int curr = 0;
        for(Integer i: filteredList.keySet()){
            arr[curr] = getSiteInfo(i);
            curr++;
        }
        return arr;
    }

    public String[] getSiteInfo(int siteId){
        Site s = getSite(siteId);
        String[] arr =  {""+siteId,s.getName(),s.getType().toString(),s.getAddress()};
        return arr;
    }


    public boolean isNameValidForUse(String text){
        return isValidName(text) && isSiteNameNotTaken(text);
    }

    public String[] getSiteModelData(String siteId) {
        Site s = getSite(Integer.valueOf(siteId));
        String[] arr =  {""+siteId,s.getName(),s.getAddress(),s.getContactName(),s.getContactPhone(),
                String.valueOf(s.getLocation().x),String.valueOf(s.getLocation().y),s.getType().toString(),""+s.isActive};
        return arr;

    }
}

