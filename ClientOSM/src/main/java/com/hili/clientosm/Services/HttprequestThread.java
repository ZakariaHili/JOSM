package com.hili.clientosm.Services;


import com.hili.clientosm.Entities.JSONEntitie;

/**
 * Created by HILI on 07/11/2015.
 */
public class HttprequestThread extends Thread {



        protected volatile boolean running = false;
        protected HttpServicesImpl httpServices ;

        
        
        protected  JSONEntitie jsonEntitie;
        protected String url;
        
        
        
        
        public HttpServicesImpl getHttpServices() {
			return httpServices;
		}
		public void setHttpServices(HttpServicesImpl httpServices) {
			this.httpServices = httpServices;
		}
		public HttprequestThread(){
            httpServices = new HttpServicesImpl();
        }
        public void setAll(JSONEntitie jsonEntitie,String url,int maxRoutes){
            this.jsonEntitie =jsonEntitie;
            this.url=url;
            httpServices.setMaxRoutes(maxRoutes);
        }
        public void setJsonEntitie(JSONEntitie jsonEntitie){
            this.jsonEntitie=jsonEntitie;
        }
        public HttprequestThread(JSONEntitie jsonEntitie, String url){
            this.jsonEntitie = jsonEntitie;
            this.url=url;
        }
        public boolean isRunning ()
        {
            return running;
        }
        
        //Stop GPS Sender
        public void arret() {
            running = false;
        }
        //Start GPS Sender
        public void demarrer() {
        running = true;
    }
        public void run() {
            try {
                 while (running) {
                	 if(httpServices.getCounter()!=httpServices.getMaxRoutes()-1)
                   httpServices.sendPostHttpRequest(jsonEntitie,url);
                	 else
                		 this.arret();
                     Thread.sleep(10000);
                }
            }catch (Exception e) {
                System.out.println("error HttprequestThread"+ e.toString()+"\n");
            }
        }


}
