package com.hili.clientosm.Services;

/**
 * Created by HILI on 07/11/2015.
 */
public class HttprequestThreadImpl extends Thread implements HttprequestThread {



        protected volatile boolean running = false;
        protected HttpServices httpServices ;
        protected int delay;
        protected String url;
        
        
        
        
      
	
		
		public HttpServices getHttpServices() {
			return httpServices;
		}

		public void setHttpServices(HttpServices httpServices) {
			this.httpServices = httpServices;
		}

		public HttprequestThreadImpl(){
            httpServices = new HttpServicesImpl();
        }
		
		@Override
        public void setAll(String url,int maxRoutes){
            this.url=url;
            httpServices.setMaxRoutes(maxRoutes);
        }
		
        
        public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		
		@Override
        public boolean isRunning ()
        {
            return running;
        }
        
        //Stop GPS Sender
        @Override
        public void arret() {
            running = false;
        }
        //Start GPS Sender
        @Override
        public void demarrer() {
        running = true;
        delay=getHttpServices().getGpsMetier().getSettingsDao().getDelay();
        start();

    }
        public void run() {
            try {
                 while (running) {
                	
                	 if(httpServices.getCounter()<=httpServices.getMaxRoutes()-1)
                   httpServices.sendPostHttpRequest(url);
                	 else
                		 this.arret();
                	
                     Thread.sleep(delay);
                }
            }catch (Exception e) {
                System.out.println("error HttprequestThread"+ e.toString()+"\n");
            }
        }
        

}
