package com.hili.clientosm.Modele.Metier;

import java.util.LinkedList;

import org.json.JSONArray;
import org.json.JSONObject;


public class APIMetierImpl implements APIMetier {
	private boolean noRoute;
	
	public APIMetierImpl() {
		noRoute=false;
	}
	
	public  boolean isNoRoute() {
		return noRoute;
	}
	
	
	//Parse Response From API
	 
		@Override
		public LinkedList<String[]> jsontoLinkedListMapQuest(String jsonString,int errorAPI) {
			try {


				LinkedList <String[]> list = new LinkedList<String[]>();

				jsonString= jsonString.substring(24, jsonString.length()-2);
				//System.out.println(jsonString);

				JSONObject jsonRootObject = new JSONObject(jsonString);
				JSONObject route = jsonRootObject.optJSONObject("route");
				try{
					JSONObject shape = route.getJSONObject("shape");

					JSONArray shapePoints=shape.getJSONArray("shapePoints");

					for (int i = 0; i < shapePoints.length(); i++) {


						list.add(new String[]{shapePoints.get(i).toString(),shapePoints.get(i+1).toString()});
						i++;
					}
					noRoute=false;
					errorAPI=200;
				}catch(Exception e){
					errorAPI=404;
					System.out.println("JSONObject[shape] not found. => No Route found");
					noRoute=true;	

				}
				return list;


			}
			catch (Exception e){
				System.out.println("Help jsontoLinkedList :'( "+e.toString());
			}
			return null;
		}





		@Override
		public LinkedList<String[]> jsontoLinkedListGoogle(String jsonString,int errorAPI) {
			try {


				LinkedList <String[]> list = new LinkedList<String[]>();

				//jsonString= jsonString.substring(24, jsonString.length()-2);


				JSONObject jsonRootObject = new JSONObject(jsonString);
				//JSONObject route = jsonRootObject.getJSONObject("routes");
				JSONArray routes = jsonRootObject.getJSONArray("routes");

				for(int i=0;i<routes.length();i++){
					if(routes.get(i)!=null){
						JSONObject route = (JSONObject) routes.get(i);

						if (route.get("legs")!=null){

							JSONArray legs = route.getJSONArray("legs");

							JSONObject legsE =  (JSONObject) legs.get(0);

							JSONArray steps =legsE.getJSONArray("steps");

							if(steps!=null){
								for(int j=0;j<steps.length();j++){
									JSONObject step =  (JSONObject) steps.get(j);

									if(step!=null){
										JSONObject start_location =  (JSONObject) step.get("start_location");

										list.add(new String[] {start_location.get("lat").toString(),start_location.get("lng").toString()});
										if(j==steps.length()-1)
										{
											JSONObject end_location =  (JSONObject) step.get("end_location");

											list.add(new String[] {end_location.get("lat").toString(),end_location.get("lng").toString()});

										}
									}
								}

							}

						}
					}}
				errorAPI=200;
				return list;
			}
			catch(Exception e){
				errorAPI=404;
				System.out.println("JSONObject[shape] not found. => No Route found");
				noRoute=false;	
				return null;

			}









		}

		// Generate Link for API
		@Override
		public String makeLinkForRoutesMapQuest(String lat1, String lon1, String lat2, String lon2) {

			String s= "http://www.mapquestapi.com/directions/v2/route?key=f4SfWZKaf0pHk2QxkAF7VqqNVY4AiBPp&callback="
					+ "renderAdvancedNarrative&ambiguities=ignore&avoidTimedConditions=false&doReverseGeocode=true&o"
					+ "utFormat=json&routeType=fastest&timeType=1&enhancedNarrative=true&shapeFormat=raw&generalize=0&"
					+ "locale=fr_FR&unit=k&from="+lat1+","+lon1+"&to="+lat2+","+lon2;
			return s.replace(" ", "");

		}
		
		@Override
		public String makeLinkForRoutesGoogle(String lat1, String lon1, String lat2, String lon2) {

			String s= "https://maps.googleapis.com/maps/api/directions/json?origin="
					+ lat1+","+lon1+"&destination="+ lat2+","+lon2
					+ "&key=AIzaSyC3d0hChbCJJbsM1MHzAGp4FgcDgfslqfg";
			return s.replace(" ", "");

		}
}
