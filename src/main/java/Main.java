import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Main {
	 public static void main(String[] args) {
	        try {
	            SchedulerFactory schedFact = new StdSchedulerFactory();
	            Scheduler sched = schedFact.getScheduler();
	            sched.start();
	     
	            JobDetail job = JobBuilder.newJob(MyJob.class)
	                .withIdentity("myjob", "grupo1")
	                .build();
	            Trigger trigger = TriggerBuilder
	                .newTrigger()
	                .withIdentity("meugatilho", "grupo1")
	                .withSchedule(CronScheduleBuilder.cronSchedule("0/10 * * * * ?"))
//	                .withSchedule(CronScheduleBuilder.cronSchedule("0 01 11 ? * MON,TUE,WED,THU,FRI,SAT"))
	                .build();
	            sched.scheduleJob(job, trigger);
	             
	            sched.deleteJob(job.getKey());
	            trigger = TriggerBuilder.newTrigger()
	            	.withIdentity("meugatilho", "grupo1")
	 	            .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
//	 	            .withSchedule(CronScheduleBuilder.cronSchedule("0 01 11 ? * MON,TUE,WED,THU,FRI,SAT"))
	 	            .build();
	            sched.scheduleJob(job,trigger);
	        } catch (SchedulerException e) {
	            System.out.println("erro");
	            e.printStackTrace();
	        }
	    }
}
