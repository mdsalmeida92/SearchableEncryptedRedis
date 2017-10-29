import API.clientAPI;
import client.clientMasterSlave;
import utils.MyEntry;

import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class test {

	public static void main(String[] args) {

		clientAPI client= new clientMasterSlave("http://localhost:8080/");
		Map<String,String> map = new HashMap<String,String>();
		map.put("field1", "Hello");
		map.put("field2", "World");
		map.put("field4", "1");

		Map<String,String> map2 = new HashMap<String,String>();
		map2.put("field1", "Hell");
		map2.put("field2", "Heaven");
		map2.put("field4", "5");
		;

		Map<String,String> map3 = new HashMap<String,String>();
		map3.put("field1", "bye");
		map3.put("field2", "water");
		map3.put("field4", "17");
		;


		addGetTest(client, map);
		addElementTest(client, map);
		removeTest(client, map);
		incrTest(client, map);
		sumTest(client, map, map2);
		sumConstTest(client, map);
		multTest(client, map, map2);
		searchElemTest(client, map, map2);
		searchEntriesTest(client, map, map2);
		orderEntrysTest(client, map, map2, map3);
		searchGreaterThanTest(client, map, map2, map3);
		searchLesserThanTest(client, map, map2, map3);
		isGreaterThanTest(client, map, map2);

	}

	static void addGetTest(clientAPI client, Map<String,String> map){

		client.addSet("ola1005", map);
		Map<String,String> mapGET =  client.getSet("ola1005");
		assert map.equals(mapGET);
		client.removeSet("ola1005");


	}

	static void addElementTest(clientAPI client, Map<String,String> map){

		client.addSet("ola1005", map);
		client.addElement("ola1005", "newfield", "something");
		Map<String,String> newmap = map;
		newmap.put("newfield", "something");
		Map<String,String> mapGET =  client.getSet("ola1005");
		client.removeSet("ola1005");
		assert newmap.equals(mapGET);


	}

	static void removeTest(clientAPI client, Map<String,String> map){

		client.addSet("ola1005", map);
		client.removeSet("ola1005");
		Map<String,String> mapGET =  client.getSet("ola1005");
		assert mapGET.isEmpty();


	}

	static void incrTest(clientAPI client, Map<String,String> map){

		client.addSet("ola1005", map);
		client.incr("ola1005", "field4");
		Map<String,String> mapGET =  client.getSet("ola1005");
		client.removeSet("ola1005");
		assert Integer.valueOf(map.get("field4")) +1 ==   Integer.valueOf(mapGET.get("field4"));



	}

	static void sumTest(clientAPI client, Map<String,String> map, Map<String,String> map2){

		client.addSet("ola1005", map);
		client.addSet("ola1006", map2);
		int value = client.sum("ola1005", "field4", "ola1006");
		client.removeSet("ola1005");
		client.removeSet("ola1006");
		assert value == 6;



	}

	static void sumConstTest(clientAPI client, Map<String,String> map){

		client.addSet("ola1005", map);
		int value = client.multConst("ola1005", "field4", 6);
		client.removeSet("ola1005");
		assert value == 6;



	}

	static void multTest(clientAPI client, Map<String,String> map, Map<String,String> map2){

		client.addSet("ola1005", map);
		client.addSet("ola1006", map2);
		int value = client.mult("ola1005", "field4", "ola1006");
		client.removeSet("ola1005");
		client.removeSet("ola1006");
		assert value == 5;


	}

	static void searchElemTest(clientAPI client, Map<String,String> map, Map<String,String> map2){

		client.addSet("ola1005", map);
		client.addSet("ola1006", map2);
		List<String> list = client.searchElement("field2", "World");
		client.removeSet("ola1005");
		client.removeSet("ola1006");

		assert list.contains("ola1005");




	}

	static void searchEntriesTest(clientAPI client, Map<String,String> map, Map<String,String> map2){

		client.addSet("ola1005", map);
		client.addSet("ola1006", map2);
		Map<String,String> mapTemp = new HashMap<String,String>();
		mapTemp.put("field1", "Hello");
		mapTemp.put("field2", "World");
		//mapTemp.put("field4", "5");
		List<String> list = client.searchEntry(mapTemp);
		client.removeSet("ola1005");
		client.removeSet("ola1006");


		assert list.contains("ola1005");


	}

	static void orderEntrysTest(clientAPI client, Map<String,String> map, Map<String,String> map2, Map<String,String> map3){

		client.addSet("ola1005", map);
		client.addSet("ola1006", map2);
		client.addSet("ola1007", map3);


		List<MyEntry> list = client.orderEntrys("field4");
		List<String> keys = new LinkedList<>();
		keys.add("ola1005");
		keys.add("ola1006");
		keys.add("ola1007");
		client.removeSet("ola1005");
		client.removeSet("ola1006");
		client.removeSet("ola1007");

		Iterator<String> it = keys.iterator();
		for (Iterator<MyEntry> iterator = list.iterator()  ; iterator.hasNext() && it.hasNext();) {
			MyEntry myEntry = iterator.next();
			String string = it.next();
			assert string.equals(myEntry.getKey());
		}
	}
	
	static void searchGreaterThanTest(clientAPI client, Map<String,String> map, Map<String,String> map2, Map<String,String> map3){

		client.addSet("ola1005", map);
		client.addSet("ola1006", map2);
		client.addSet("ola1007", map3);


		List<MyEntry> list = client.searchGreaterThan("field4", 5);

		client.removeSet("ola1005");
		client.removeSet("ola1006");
		client.removeSet("ola1007");

		for (Iterator<MyEntry> iterator = list.iterator()  ; iterator.hasNext();) {
			MyEntry myEntry = iterator.next();
			assert "ola1007".equals(myEntry.getKey());
		}
	}
	
	static void searchLesserThanTest(clientAPI client, Map<String,String> map, Map<String,String> map2, Map<String,String> map3){

		client.addSet("ola1005", map);
		client.addSet("ola1006", map2);
		client.addSet("ola1007", map3);


		List<MyEntry> list = client.searchLesserThan("field4", 5);

		client.removeSet("ola1005");
		client.removeSet("ola1006");
		client.removeSet("ola1007");

		for (Iterator<MyEntry> iterator = list.iterator()  ; iterator.hasNext();) {
			MyEntry myEntry = iterator.next();
			assert "ola1005".equals(myEntry.getKey());
		}
	}
	
	static void isGreaterThanTest(clientAPI client, Map<String,String> map, Map<String,String> map2){

		client.addSet("ola1005", map);
		client.addSet("ola1006", map2);
		boolean isGreaterThan = client.valuegreaterThan("ola1005", "field4", "ola1006");
		client.removeSet("ola1005");
		client.removeSet("ola1006");
		assert !isGreaterThan;


	}

}
