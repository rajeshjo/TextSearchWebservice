package com.textSearch.service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.textSearch.model.TextSearchCount;
import com.textSearch.model.TextSearchCountRequest;

@Service
public class TextSearchService {
	
	@Autowired
	ServletContext servletContext;
	
  public LinkedHashMap<String,Integer> searchAllText(){
	  ArrayList<TextSearchCount> textSearchCount = new ArrayList<TextSearchCount>();
	  LinkedHashMap<String,Integer> sortedMap = null;
	  InputStream istream =  servletContext.getResourceAsStream("/WEB-INF/TextSource.txt");
	  BufferedReader bReader = new BufferedReader(new InputStreamReader(istream));
	  String line;
	  HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	  try {
		while((line=bReader.readLine())!=null){
			String[] arr = line.split("\\P{Alpha}+");
			for(String str:arr){
				if(hmap.get(str)!=null){
					hmap.put(str, hmap.get(str)+1);
				}else{
					hmap.put(str, 1);
				}
			}
			
		}
		sortedMap =sortInDescending(hmap);
		  				  
	} catch (IOException e) {
		e.printStackTrace();
	}
	  
	  return sortedMap;
  }
  
  public TextSearchCount[] searchText(TextSearchCountRequest textArray){
	  ArrayList<TextSearchCount> textSearchCount = new ArrayList<TextSearchCount>();
	  InputStream istream =  servletContext.getResourceAsStream("/WEB-INF/TextSource.txt");
	  BufferedReader bReader = new BufferedReader(new InputStreamReader(istream));
	  String line;
	  HashMap<String, Integer> hmap = new HashMap<String, Integer>();
	  try {
		while((line=bReader.readLine())!=null){
			String[] arr = line.split("\\P{Alpha}+");
			for(String str:arr){
				if(hmap.get(str)!=null){
					hmap.put(str, hmap.get(str)+1);
				}else{
					hmap.put(str, 1);
				}
			}
			
		}
		LinkedHashMap<String,Integer> sortedMap =sortInDescending(hmap);
		  String[] txtArray= textArray.getSearchText();
		  for(String txt:txtArray){
			  textSearchCount.add(new TextSearchCount(txt,sortedMap.get(txt)==null?0:sortedMap.get(txt)));
		  }			  
	} catch (IOException e) {
		e.printStackTrace();
	}
	  
	  return textSearchCount.toArray(new TextSearchCount[textSearchCount.size()]);
  }
  
  public LinkedHashMap<String, Integer> sortInDescending(HashMap<String, Integer> unsortMap)
  {

      List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());

      Collections.sort(list, new Comparator<Entry<String, Integer>>()
      {
          public int compare(Entry<String, Integer> o1,
                  Entry<String, Integer> o2)
          {
             return o2.getValue().compareTo(o1.getValue());
          }
      });

      // Maintaining insertion order with the help of LinkedList
      LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
      for (Entry<String, Integer> entry : list)
      {
          sortedMap.put(entry.getKey(), entry.getValue());
      }

      return sortedMap;
  }

}
