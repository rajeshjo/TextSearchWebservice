package com.textSearch.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.textSearch.model.TextSearchCount;
import com.textSearch.model.TextSearchCountRequest;
import com.textSearch.service.TextSearchService;



@RestController
public class TextSearchController {
	@Autowired
	TextSearchService textSearchservice;

	@RequestMapping(value="/search",method=RequestMethod.POST)
	public @ResponseBody TextSearchCount[] getcount(@RequestBody String inputString){
		ObjectMapper mapper = new ObjectMapper();
		TextSearchCountRequest requestStrings = new TextSearchCountRequest();

		String inputStringDecoded = null;
		TextSearchCount[] textSearchCountArray = null;
		try {
			inputStringDecoded = URLDecoder.decode(inputString,"UTF-8");
			requestStrings = mapper.readValue(inputStringDecoded, TextSearchCountRequest.class);
			textSearchCountArray = textSearchservice.searchText(requestStrings);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return textSearchCountArray;
	}


	@RequestMapping(value="/top/{player}")
	public  String getcountcsv(@PathVariable String player){
		int count = player==null?0:Integer.valueOf(player);
		LinkedHashMap<String, Integer> textSearchSortedMap = textSearchservice.searchAllText();
		ArrayList<String> list = new ArrayList<String>();
		Iterator iter = textSearchSortedMap.entrySet().iterator();
		int i =0;
		while(iter.hasNext()){
			Map.Entry entry = (Map.Entry)iter.next();
			list.add(entry.getKey()+"|"+entry.getValue()+"\n");
			i++;
			if(i==count)
				break;
		}
		return list.toString();
	}

}
