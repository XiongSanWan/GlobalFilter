package com.xy.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

public class GlobalFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
	HttpServletRequest req = (HttpServletRequest) request;
	HttpServletResponse resp = (HttpServletResponse) response;
	
	//req.setCharacterEncoding("utf-8");
	req = new MyRequest(req);

		chain.doFilter(req, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}

class MyRequest extends HttpServletRequestWrapper{
		HttpServletRequest request;
	public MyRequest(HttpServletRequest request) {
		super(request);//���ø���Ĺ��췽������Ϊ�Ǽ̳й����ı�����ø���Ĺ��죬����Ϊ����ֻ���вι���������һ�б���Ҫ��
		this.request = request;
	}
	/*@Override
	public String getParameter(String name) {
		String n = request.getParameter(name);
		try {
			return new String(n.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}*/
	private boolean flag=true;
	@Override
	public Map<String, String[]> getParameterMap() {
		// TODO Auto-generated method stub
		Map<String, String[]> map = request.getParameterMap();
		if(flag){		//���ж�Ϊ�˷�ֹ���ε���getParameterMapʹԭ������õı����ָ���ԭ�ˡ�
			for (Map.Entry<String, String[]> m:map.entrySet()){
				String[] values = m.getValue();
				for (int i = 0; i < values.length; i++) {
					try {
						values[i] = new String(values[i].getBytes("iso-8859-1"),"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			flag=false;
		}
		return map;
	}
	@Override
	public String getParameter(String name) {
		// TODO Auto-generated method stub
		Map<String, String[]> map = getParameterMap();
		return map.get(name)[0];
	}
	@Override
	public String[] getParameterValues(String name) {
		// TODO Auto-generated method stub
		Map<String, String[]> map = getParameterMap();
		return map.get(name);
	}
}