package com.CNFloWopen.niugou.web.superadmin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.CNFloWopen.niugou.entity.Area;

import com.CNFloWopen.niugou.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/superadmin")
public class AreaController {
	@Autowired
	private AreaService areaService;
	Logger logger = LoggerFactory.getLogger(AreaController.class);
	@RequestMapping(value = "/listArea",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> listArea()
	{
		logger.info("=====start=====");
		long startTime = System.currentTimeMillis();
		List<Area> list = new ArrayList<Area>();
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			list = areaService.getAreaList();
			map.put("rows",list);
			map.put("total",list.size());
		}catch (Exception e){
			map.put("success",false);
			map.put("errMsg",e.toString());
		}
		logger.error("test error");
		long endTime = System.currentTimeMillis();
		logger.debug("costTime:[${}ms]",endTime-startTime);
		logger.info("======end=====");
		return map;
	}



}
