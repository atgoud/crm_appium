package Action;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import Page.InformationView;
import Page.Menu;
import Page.WorkOrder.WorkOrderDetailsView;
import Page.WorkOrder.WorkOrderListView;
import Page.WorkOrder.*;
import UI.GetBy;
import UI.UI;

public class Navigate extends UI {

	private static By WorkOrderListItem= GetBy.ClassAndText(Class.TextView,"Work order list");
	private static By  ProccedButton= GetBy.ClassAndText(Class.Button,"Proceed");
	
	public static WorkOrderListView ToWorkOrderList()
	{	
		if (UI.ElementExist(GetBy.ClassAndText(Class.TextView, "Activities"),0))
		{
			Back();Back();
			return new WorkOrderListView();
		}
	
		Menu mainMenu=ToMainMenu();
		return  mainMenu.ChooseWorkOrderList();
	}
	
	public static WorkOrderDetailsView ToFirstWorkOrder()
	{		
		// now select first work order 
		return ToWorkOrderList().ChooseFirstWorkOrder();
	}
	
	public static Menu ToMainMenu(){
		TapIfExist(GetBy.Text("Friendly Solutions"),1);
		if (UI.ElementExist(ProccedButton,2))
		{
			return (new InformationView()).Procced();
		}
		return new Menu();
	}
	
    public static  InformationView ToInformationPage(){ 
    	TapIfExist(GetBy.Text("Friendly Solutions"),1);
    	String textInPage=UI.GetTextFromAllChildren(null);
    	if (textInPage.contains("#"))
    	{
    		Back();Back();
    	}	
    	if (textInPage.contains("Work order list"))	Back();
		return new InformationView();
	}
    
    public static WorkOrderDetailsView ToWorkOrderByDescription(String Description,String ...WoCustomer)
    {
    	 String Customer= WoCustomer.length>0?WoCustomer[0]:TestData.TestCustomer;
    	 WorkOrderListView WorkOrderList;
		 WorkOrderList=Navigate.ToWorkOrderList();
		 if(!UI.ElementExist(GetBy.ClassAndText(Class.TextView, Description), 0))
		 WorkOrderList.Submenu().Customers(Customer);
		 
		 return WorkOrderList.SelectWorkOrderByDescription(Description);
    }

    public static NewWorkOrderView ToNewWorkOrderView()
    {
    	// TODO : refaktoring przeniesienie do innej klasy
    	String CustomerName="2500 Holding Group LLC*";
    	String newWorkOrderItem="Create new work order";
    	Back();Back(); ToMainMenu().ChooseClientList();
    	WaitAndTap(GetBy.ClassAndText(Class.Button, "0-9"));
    	WaitAndTap(GetBy.Text(CustomerName));
    	WaitAndTap(GetBy.Text(newWorkOrderItem));
    	return new NewWorkOrderView();
    }
}
