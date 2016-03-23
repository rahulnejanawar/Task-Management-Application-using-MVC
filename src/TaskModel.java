

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class TaskModel {

	public boolean checkIfCategoryExists(String catName)
	{
		/*
		 * check if a file with catName as name already exists!
		 */
		
		return new File(catName+".tasks").exists();
	}

	public String addTask(TaskBean bean,String catName) {
		
		/*
		 * 1.apply business validations!
		 * if it fails, return error msg
		 * 2.apply business logic
		 * if it fails, return error msg
		 * else return "SUCCESS"
		 */
		
		BufferedWriter bw = null;
		try
		{
			bw = new BufferedWriter(new FileWriter(catName+".tasks",true));
			bw.write(bean.toString());
			bw.newLine();
			return Constants.SUCCESS;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return "Oops something bad happened! "+e.getMessage();
		}
		finally
		{
			if(bw!=null)
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}
	
	public String upadateTask(String catName,String updateTask, String oldcontent) throws IOException{
		System.out.println("catname is-"+catName);
		File file = new File(catName+".tasks");
		String delete;
		String totalStr="";
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		while((delete = reader.readLine()) != null) {
			  
			 totalStr += delete+"\n";
		}
		totalStr = totalStr.replace(oldcontent, updateTask);
        FileWriter fw = new FileWriter(file);
        fw.write(totalStr);
        fw.close();
        reader.close();
		return Constants.SUCCESS;
	}
	
	public List<String> listTaskbyAlpha(){
		File [] files = listfiles();
		List<String> listtasks=new ArrayList<String>();
		for (File catfile : files) {
			String catname=catfile.toString();
			String catName=catname.substring(2,catname.length()-6);
			List<TaskBean> tasks1 = fetchTasks(catName);
			for(TaskBean tb : tasks1)
			{
				listtasks.add("Name : "+tb.getTaskName()+" Desc : "+tb.getDesc()+" End Date : "+tb.getEndDt()+" Tags : "+tb.getTags()+" Priority :"+tb.getPriority()+" Task Created Date :"+tb.getDate());
			}
		}
		return listtasks;
	}
	
	public List<TaskBean> searchTask(String searchtask){
		List<TaskBean> listtasks=new ArrayList<TaskBean>();
		File [] files2=listfiles();
		for (File catfile : files2) {
			String catname=catfile.toString();
			String catName=catname.substring(2,catname.length()-6);
			List<TaskBean> tasks1 = fetchTasks(catName);
			for(TaskBean tb : tasks1)
			{
				if(tb.getTaskName() != null && tb.getTaskName().contains(searchtask)){
					listtasks.add(tb);
					System.out.println("Name : "+tb.getTaskName()+" Desc : "+tb.getDesc()+" End Date : "+tb.getEndDt()+" Tags : "+tb.getTags()+" Priority :"+tb.getPriority()+" Task Created Date :"+tb.getDate());
				}
			
			}
												
		}
		return listtasks;
	}
	
	public List<TaskBean> listTaskbyCreatedDate(){
		List<TaskBean> listtasks=new ArrayList<TaskBean>();
		File [] files2=listfiles();
		for (File catfile : files2) {
			String catname=catfile.toString();
			String catName=catname.substring(2,catname.length()-6);
			List<TaskBean> tasks1 = fetchTasks(catName);
			for(TaskBean tb : tasks1)
			{	
				listtasks.add(tb);		
			}										
		}
		Collections.sort(listtasks, new Comparator<TaskBean>(){
			   public int compare(TaskBean o1, TaskBean o2){
				   return o1.getDate().compareTo(o2.getDate());
			   }
			});
		return listtasks;
	}
	
	public List<String> listTaskbyDueDate() throws ParseException{
		File [] files=listfiles();
		List<String> listtasks=new ArrayList<String>();
		for (File catfile : files) {
			String catname=catfile.toString();
			String catName=catname.substring(2,catname.length()-6);
			List<TaskBean> tasks = fetchTasks(catName);
			for(TaskBean tb : tasks)
			{
				String format = "dd/MM/yyyy";
				 
	            SimpleDateFormat sdf = new SimpleDateFormat(format);
	            String date1=tb.getEndDt();
	            String date2=new SimpleDateFormat(format).format(new Date());
	            Date dateObj1 = sdf.parse(date1);
	            Date dateObj2 = sdf.parse(date2);
	            System.out.println(dateObj1);
	            System.out.println(dateObj2 + "\n");
	 
	            long diff = dateObj2.getTime() - dateObj1.getTime();
	            System.out.println("diff="+diff);
	            int diffDays = (int) (diff / (24 * 60 * 60 * 1000));
	            System.out.println("diff="+diffDays);
	            if(diffDays<=0){
	            	listtasks.add("Task Name:"+tb.getTaskName()+" End Date:"+tb.getEndDt()+" get due in "+Math.abs(diffDays)+"days");
	            }
	            else{
	            	listtasks.add("Task Name:"+tb.getTaskName()+" End Date:"+tb.getEndDt()+" is due by "+diffDays+"days");
	            }
			}
												
		}
		return listtasks;
	}
	
	public List<TaskBean> listTaskbylongesttime(){
		File [] files = listfiles();
		List<TaskBean> listtasks=new ArrayList<TaskBean>();
		for (File catfile : files) {
			String catname=catfile.toString();
			String catName=catname.substring(2,catname.length()-6);
			List<TaskBean> tasks1 = fetchTasks(catName);
			for(TaskBean tb : tasks1)
			{   
				if(!(tb.getStatus().equals(Constants.COMPLETED))){
					listtasks.add(tb);
			
				}	
			}
				
		}
		return listtasks;
	}
	
	@SuppressWarnings("unused")
	public String removeTask(String removeTask) throws IOException{
		File [] files = listfiles();
		Scanner sc2=new Scanner(System.in);
		List<String> li=new ArrayList<String>();
		for (File catfile : files) {
		String catname=catfile.toString();
		String catName=catname.substring(2,catname.length()-6);
		List<TaskBean> tasks1 = fetchTasks(catName);
		for(TaskBean t:tasks1) {
			li.add(t.getTaskName());
		}
		}
		while(!li.contains(removeTask)){
			System.out.println("The Task you entered does not exists...Please enter proper Task Name");
			System.out.println("Task List="+li);
			System.out.println("Here is the list of tasks choose which one you want to delete");
			removeTask=sc2.nextLine();
		}	
		for (File catfile : files) {
			String catname=catfile.toString();
			String catName=catname.substring(2,catname.length()-6);
			List<TaskBean> tasks1 = fetchTasks(catName);
			for(TaskBean t:tasks1) {
				File file = new File(catName+".tasks");
				File temp = File.createTempFile("file", ".txt", file.getParentFile());
				String charset = "UTF-8";
				String delete;
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
				while((delete = reader.readLine()) != null) {
					  // trim newline when comparing with lineToRemove
					  String trimmedLine = delete.trim();
					 if(trimmedLine.contains(removeTask)) continue;
					 writer.write(delete + System.getProperty("line.separator"));
				}
				reader.close();
				writer.close();
				file.delete();
				temp.renameTo(file);		
			}
		}
		sc2.close();
		return Constants.SUCCESS;
	}
	
	@SuppressWarnings("unused")
	public String removeTask(String removeTask, String catName) throws IOException{
		Scanner sc2=new Scanner(System.in);
		List<String> li=new ArrayList<String>();
		List<TaskBean> tasks1 = fetchTasks(catName);
		for(TaskBean t:tasks1) {
			li.add(t.getTaskName());
		}
		while(!li.contains(removeTask)){
			System.out.println("The Task you entered does not exists...Please enter proper Task Name");
			System.out.println("Task List="+li);
			System.out.println("Here is the list of tasks choose which one you want to delete");
			removeTask=sc2.nextLine();
		}	
			for(TaskBean t:tasks1) {
				
				File file = new File(catName+".tasks");
				File temp = File.createTempFile("file", ".txt", file.getParentFile());
				String charset = "UTF-8";
				String delete;
				BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
				PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
				while((delete = reader.readLine()) != null) {
					  // trim newline when comparing with lineToRemove
					  String trimmedLine = delete.trim();
					 if(trimmedLine.contains(removeTask)) continue;
					 writer.write(delete + System.getProperty("line.separator"));
				}
				reader.close();
				writer.close();
				file.delete();
				temp.renameTo(file);		
			}
		
        sc2.close();
		return Constants.SUCCESS;
	}
	
	public File[] listfiles(){
		File dir = new File(".");
		File [] files = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.endsWith(".tasks");
		    }
		});
		return files;
	}

	public List<TaskBean> fetchTasks(String catName) {
		
		List<TaskBean> tasks = new ArrayList<TaskBean>();
		
		BufferedReader br = null;
		String line;
		TaskBean bean;
		try
		{
			br = new BufferedReader(new FileReader(catName+".tasks"));
			while((line = br.readLine())!=null)
			{
				String[] sa = line.split(":");
				
				bean = new TaskBean(sa[0],sa[1],sa[2],sa[3],sa[4],sa[5],sa[6]);
				tasks.add(bean);
			}
			return tasks;
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return null;
		}
		finally
		{
			if(br!=null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
	}
}










