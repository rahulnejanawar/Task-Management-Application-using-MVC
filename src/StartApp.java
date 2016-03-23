import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class StartApp {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws IOException, ParseException {

		String catName,taskName,desc,endDt,tags,priority,status;
		Scanner sc1=new Scanner(System.in);
		Scanner sc2=new Scanner(System.in);
		TaskModel model = new TaskModel();
		boolean result;
		int ch=0;
		while(ch!=5){
			System.out.println("----------");
			System.out.println("Press 1 to Create Category");
			System.out.println("Press 2 to Load Category");
			System.out.println("Press 3 to Search");
			System.out.println("Press 4 to List");
			System.out.println("Press 5 to Exit");
			System.out.println("Enter Your Choice:");
			System.out.println("");
			
			while(!sc1.hasNextInt()){
				System.out.println("Please Enter Integer Input Only");
				sc1.next();
			}
			ch=sc1.nextInt();
			System.out.println("Choice is:"+ch);
			switch (ch) {
			case 1:
				System.out.println("Create Category");
				System.out.println("Enter Name of the Category:");
				catName=sc2.nextLine();
				while(!TaskUtil.validateName(catName))
				{
					System.out.println("Enter valid name, starting with letter, no space, no spl chars!!");
					catName = sc2.nextLine();
				}
				System.out.println("Category name given = "+catName);
				
				result = model.checkIfCategoryExists(catName);
				if(result==true)
					System.out.println("Category name already exists, Please enter new name!!!");
				else
				{
				ch=0;
				while(ch!=6){
					System.out.println("----------");
					System.out.println("Press 1 to Add Task");
					System.out.println("Press 2 to Edit Task");
					System.out.println("Press 3 to Remove Task");
					System.out.println("Press 4 to List the Task");
					System.out.println("Press 5 to Search");
					System.out.println("Press 6 to Go back");
					System.out.println("");
					while(!sc1.hasNextInt()){
						System.out.println("Please Enter Integer Input Only");
						sc1.next();
					}
					ch=sc1.nextInt();
					System.out.println("Choice is:"+ch);
					switch (ch) {
					case 1:
						System.out.println("Adding Tasks...");
						System.out.println("Enter Name of the Task");
						taskName=sc2.nextLine();
						while(!TaskUtil.validateName(taskName))
						{
							System.out.println("Enter valid task name...");
							taskName = sc2.nextLine();
						}
						System.out.println("Enter Task Description");
						desc = sc2.nextLine();
						System.out.println("Enter Task End of Date in this format dd/MM/yyyy");
						endDt = sc2.nextLine();
						String dt =new SimpleDateFormat("dd/MM/yyyy").format(new Date());
						while(!TaskUtil.isValidDate(endDt))
						{
							System.out.println("Enter valid date in format dd/MM/yyyy  !");
							endDt = sc2.nextLine();
						}
						System.out.println("Enter Task Priority");
						priority=sc2.nextLine();
						System.out.println("Enter Task Tags(comma seperated)");
						tags = sc2.nextLine();
						System.out.println("Enter Task Status");
						status = sc2.nextLine();
						TaskBean bean = new TaskBean(taskName,desc,endDt,tags,priority,status,dt);
						
						String msg = model.addTask(bean,catName);
						if(msg.equals(Constants.SUCCESS))
							System.out.println("Task "+taskName+" added successfuly to "+catName+" category");
						else
							System.out.println("Addition failed! Msg = "+msg);
						break;
					case 2:
						System.out.println("Edit tasks");
						System.out.println("Enter task name to edit");
						String edittask=sc2.nextLine();
						File [] files4=model.listfiles();
						List<String> listtasks3=new ArrayList<String>();
						for (File catfile : files4) {
							String catname=catfile.toString();
							catName=catname.substring(2,catname.length()-6);
							List<TaskBean> tasks = model.fetchTasks(catName);
							for(TaskBean tb : tasks)
							{
								if(tb.getTaskName().equals(edittask)){
									int ch1=0;
									listtasks3.add(tb.toString());
									while(ch1!=4){
										System.out.println("----------");
										System.out.println("Press 1 to edit task description");
										System.out.println("Press 2 to edit task end date");
										System.out.println("Press 3 to edit task status");
										System.out.println("Press 4 to goback");
										System.out.println("");
										while(!sc1.hasNextInt()){
											System.out.println("Please Enter Integer Input Only");
											sc1.next();
										}
										ch1=sc1.nextInt();
										System.out.println("Choice is:"+ch1);
										String updateTask;
										switch(ch1){
										case 1:
											System.out.println("present description of taskname"+tb.getTaskName()+"is :"+tb.getDesc());
											System.out.println("Enter new description");
											updateTask=sc2.nextLine();
											String editDesc=model.upadateTask(catName, updateTask,tb.getDesc());
											if(editDesc.equals(Constants.SUCCESS)){
												System.out.println("task description is changed successfully");
											}
											else{
												System.out.println("task description is not changed");
											}
									        
									        break;
										case 2:
											System.out.println("present end date of taskname"+tb.getTaskName()+"is :"+tb.getEndDt());
											System.out.println("Enter new End Date in dd/MM/yyyy fromat");
											updateTask=sc2.nextLine();
											String editEndDt=model.upadateTask(catName, updateTask,tb.getEndDt());
											if(editEndDt.equals(Constants.SUCCESS)){
												System.out.println("task end date is changed successfully");
											}
											else{
												System.out.println("task end date is not changed");
											}
									        break;
										case 3:
											System.out.println("present status of taskname"+tb.getTaskName()+"is :"+tb.getStatus());
											System.out.println("Enter enter task status");
											updateTask=sc2.nextLine();
											String editStatus=model.upadateTask(catName, updateTask,tb.getStatus());
											if(editStatus.equals(Constants.SUCCESS)){
												System.out.println("task status is changed successfully");
											}
											else{
												System.out.println("task status is not changed");
											}
											break;
										case 4:
											System.out.println("Going Back...");
											break;
										default:
											System.out.println("Enter valid input");
											break;
										}
									}
								}
							}
																
						}
						if(listtasks3.size()<1){
							System.out.println("The Task You entered "+edittask+" does not exist");
						}
						break;
					case 3:
						System.out.println("Enter Task Name to remove");
						String removeTask=sc2.nextLine();
						String confirmation=model.removeTask(removeTask);
						if(confirmation.equals(Constants.SUCCESS)){
							System.out.println("Task Removed Successfully");
						}
						else{
							System.out.println("Task is not Removed Successfully");
						}
						break;
					case 4:
						ch=0;
						while(ch!=5){
							System.out.println("Listing Tasks...");
							System.out.println("Press 1 to list tasks by alphabetical listing by name");
							System.out.println("Press 2 to list tasks by due date");
							System.out.println("Press 3 to list tasks by created date");
							System.out.println("Press 4 to list tasks by longest time");
							System.out.println("Press 5 to GoBack");
							while(!sc1.hasNextInt()){
								System.out.println("Please Enter Integer Input Only");
								sc1.next();
							}
							System.out.println("Choice is:"+ch);
							ch=sc1.nextInt();
							switch(ch){
							case 1:
								System.out.println("Listing Tasks by alphabetical listing by name");
								System.out.println("listing...");
								List<String> listtasks=model.listTaskbyAlpha();
								
								Collections.sort(listtasks);
								for(String s:listtasks){
									System.out.println(s);
								}						
								break;
							case 2:
								System.out.println("Listing Task by due date");
								List<String> listtasks1=model.listTaskbyDueDate();
								System.out.println("listtasks"+listtasks1);
								for(String s:listtasks1){
									System.out.println(s.toString());
								}
								break;
							case 3:
								System.out.println("Listing Tasks by created date");	
								List<TaskBean> listtasks2=model.listTaskbyCreatedDate();
								System.out.println("listtasks"+listtasks2);
								for(TaskBean s:listtasks2){
									System.out.println(s.toString());
								}
								break;
							case 4:
								System.out.println("Listing Tasks by longest time...");
								List<TaskBean> listtasks21=model.listTaskbylongesttime();
								System.out.println("listtasks"+listtasks21);
								Collections.sort(listtasks21, new Comparator<TaskBean>(){
									   public int compare(TaskBean o1, TaskBean o2){
										   return o1.getDate().compareTo(o2.getDate());
									   }
									});
								for(TaskBean s:listtasks21){
									System.out.println(s.toString());
								}
								break;
							case 5:
								System.out.println("Going Back");
								break;
							default:
								System.out.println("Please Enter Valid Input");
								break;
							
							}
						}
						break;
					case 5:
						System.out.println("Search Task");
						String searchtask=sc2.nextLine();
						List<TaskBean> listtasks31=model.searchTask(searchtask);
						if(listtasks31.size()<1){
							System.out.println("The Task You entered "+searchtask+" does not exist");
						}
					case 6:
						System.out.println("Going Back");
						break;

					default:
						System.out.println("Please Enter valid inputs");
						break;
					}
					
				  }
				}
				break;
			case 2:
				System.out.println("Load Category");
				System.out.println("Here is the list of category");
				
				File [] files4 = model.listfiles();
				List<String> cat=new ArrayList<String>();
				for (File catfile : files4) {
					String catname=catfile.toString();
					cat.add(catname.substring(2,catname.length()-6));
				}
				System.out.println("category:"+cat);
				System.out.println("Enter category to load");
				String loadcat=sc2.nextLine();
				if(cat.contains(loadcat)){
					System.out.println("category exist");
					catName=loadcat;
					ch=0;
					while(ch!=6){
						System.out.println("----------");
						System.out.println("Press 1 to Add Task");
						System.out.println("Press 2 to Edit Task");
						System.out.println("Press 3 to Remove Task");
						System.out.println("Press 4 to List the Task");
						System.out.println("Press 5 to Search");
						System.out.println("Press 6 to Go back");
						System.out.println("");
						while(!sc1.hasNextInt()){
							System.out.println("Please Enter Integer Input Only");
							sc1.next();
						}
						ch=sc1.nextInt();
						System.out.println("Choice is:"+ch);
						switch (ch) {
						case 1:
							System.out.println("Adding Tasks...");
							System.out.println("Enter Name of the Task");
							taskName=sc2.nextLine();
							while(!TaskUtil.validateName(taskName))
							{
								System.out.println("Enter valid task name...");
								taskName = sc2.nextLine();
							}
							System.out.println("Enter Task Description");
							desc = sc2.nextLine();
							System.out.println("Enter Task End of Date");
							endDt = sc2.nextLine();
							Date dt=new Date();
							while(!TaskUtil.isValidDate(endDt))
							{
								System.out.println("Enter valid date in format dd/mm/yyyy  !");
								endDt = sc2.nextLine();
							}
							System.out.println("Enter Task Priority");
							priority=sc2.nextLine();
							System.out.println("Enter Task Tags(comma seperated)");
							tags = sc2.nextLine();
							System.out.println("Enter Task Status");
							status = sc2.nextLine();
							TaskBean bean = new TaskBean(taskName,desc,endDt,tags,priority,status,dt.toString());
							
							String msg = model.addTask(bean,catName);
							if(msg.equals(Constants.SUCCESS))
								System.out.println("Task "+taskName+" added successfuly to "+catName+" category");
							else
								System.out.println("Addition failed! Msg = "+msg);
							break;
						case 2:
							System.out.println("Edit tasks");
							System.out.println("Enter task name to edit");
							String edittask=sc2.nextLine();
							List<String> listtasks3=new ArrayList<String>();
								List<TaskBean> tasks = model.fetchTasks(catName);
								for(TaskBean tb : tasks)
								{
									if(tb.getTaskName().equals(edittask)){
										int ch1=0;
										listtasks3.add(tb.toString());
										while(ch1!=4){
											System.out.println("----------");
											System.out.println("Press 1 to edit task description");
											System.out.println("Press 2 to edit task end date");
											System.out.println("Press 3 to edit task status");
											System.out.println("Press 4 to goback");
											System.out.println("");
											while(!sc1.hasNextInt()){
												System.out.println("Please Enter Integer Input Only");
												sc1.next();
											}
											ch1=sc1.nextInt();
											System.out.println("Choice is:"+ch1);
											String updateTask;
											switch(ch1){
											case 1:
												System.out.println("present description of taskname "+tb.getTaskName()+" is :"+tb.getDesc());
												System.out.println("Enter new description");
												updateTask=sc2.nextLine();
												String editDesc=model.upadateTask(catName, updateTask,tb.getDesc());
												if(editDesc.equals(Constants.SUCCESS)){
													System.out.println("task description is changed successfully");
												}
												else{
													System.out.println("task description is not changed");
												}
										        
										        break;
											case 2:
												System.out.println("present end date of taskname "+tb.getTaskName()+" is :"+tb.getEndDt());
												System.out.println("Enter new End Date in dd/MM/yyyy fromat");
												updateTask=sc2.nextLine();
												String editEndDt=model.upadateTask(catName, updateTask,tb.getEndDt());
												if(editEndDt.equals(Constants.SUCCESS)){
													System.out.println("task end date is changed successfully");
												}
												else{
													System.out.println("task end date is not changed");
												}
										        break;
											case 3:
												System.out.println("present status of taskname "+tb.getTaskName()+" is :"+tb.getStatus());
												System.out.println("Enter task new status");
												updateTask=sc2.nextLine();
												String editStatus=model.upadateTask(catName, updateTask,tb.getStatus());
												if(editStatus.equals(Constants.SUCCESS)){
													System.out.println("task status is changed successfully");
												}
												else{
													System.out.println("task status is not changed");
												}
												break;
											case 4:
												System.out.println("Going Back...");
												break;
											default:
												System.out.println("Enter valid input");
												break;
											}
										}
									}
								}
		
							if(listtasks3.size()<1){
								System.out.println("The Task You entered "+edittask+" does not exist");
							}
							break;
						case 3:
							System.out.println("Enter Task Name to remove");
							String removeTask=sc2.nextLine();
							String confirmation=model.removeTask(removeTask,catName);
							if(confirmation.equals(Constants.SUCCESS)){
								System.out.println("Task Removed Successfully");
							}
							else{
								System.out.println("Task is not Removed");
							}
							break;
						case 4:
							ch=0;
							while(ch!=5){
								System.out.println("Listing Tasks...");
								System.out.println("Press 1 to list tasks by alphabetical listing by name");
								System.out.println("Press 2 to list tasks by due date");
								System.out.println("Press 3 to list tasks by created date");
								System.out.println("Press 4 to list tasks by longest time");
								System.out.println("Press 5 to GoBack");
								while(!sc1.hasNextInt()){
									System.out.println("Please Enter Integer Input Only");
									sc1.next();
								}
								System.out.println("Choice is:"+ch);
								ch=sc1.nextInt();
								switch(ch){
								case 1:
									System.out.println("Listing Tasks by alphabetical listing by name");
									System.out.println("listing...");
									List<String> listtasks=model.listTaskbyAlpha();
									
									Collections.sort(listtasks);
									for(String s:listtasks){
										System.out.println(s);
									}						
									break;
								case 2:
									System.out.println("Listing Task by due date");
									List<String> listtasks1=model.listTaskbyDueDate();
									System.out.println("listtasks"+listtasks1);
									for(String s:listtasks1){
										System.out.println(s.toString());
									}
									break;
								case 3:
									System.out.println("Listing Tasks by created date");	
									List<TaskBean> listtasks2=model.listTaskbyCreatedDate();
									System.out.println("listtasks"+listtasks2);
									for(TaskBean s:listtasks2){
										System.out.println(s.toString());
									}
									break;
								case 4:
									System.out.println("Listing Tasks by longest time...");
									List<TaskBean> listtasks21=model.listTaskbylongesttime();
									System.out.println("listtasks"+listtasks21);
									Collections.sort(listtasks21, new Comparator<TaskBean>(){
										   public int compare(TaskBean o1, TaskBean o2){
											   return o1.getDate().compareTo(o2.getDate());
										   }
										});
									for(TaskBean s:listtasks21){
										System.out.println(s.toString());
									}
									break;
								case 5:
									System.out.println("Going Back");
									break;
								default:
									System.out.println("Please Enter Valid Input");
									break;
								
								}
							}
							break;
						case 5:
							System.out.println("Search Task");
							String searchtask=sc2.nextLine();
							List<TaskBean> listtasks31=model.searchTask(searchtask);
							if(listtasks31.size()<1){
								System.out.println("The Task You entered "+searchtask+" does not exist");
							}
							break;
						case 6:
							System.out.println("Going Back");
							break;

						default:
							break;
						}
		
					}
				}
				else{
					System.out.println("category you entered doesnot exist");
				}
				break;
			case 3:
				System.out.println("Search category");
				
				String searchcat=sc2.nextLine();
				File [] files2=model.listfiles();
				System.out.println("File length-"+files2.length);
				List<String> listtasks1=new ArrayList<String>();
				for (File catfile : files2) {
					String catname=catfile.toString();
					catName=catname.substring(2,catname.length()-6);
					if(catName.equals(searchcat)){
						System.out.println("The entered category "+searchcat+" exist");
						listtasks1.add(catName);
					}
														
				}
				if(listtasks1.size()<1){
					System.out.println("The category you entered "+searchcat+" Does not exist");
				}
				
				break;
			case 4:
				System.out.println("Listing category");
				
				File [] files = model.listfiles();

				for (File catfile : files) {
					String catname=catfile.toString();
					
				    System.out.println(catname.substring(2,catname.length()-6));
				}
				
				break;
			case 5:
				sc2.close();
				sc1.close();
				System.out.println("exit,,,bye bye");
				break;
			default:
				break;
			}
		}
	}

}
