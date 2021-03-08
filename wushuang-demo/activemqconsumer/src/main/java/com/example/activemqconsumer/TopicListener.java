package com.example.activemqconsumer;

import com.example.activemqconsumer.mapper.UnameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;


@Component
public class TopicListener {

	@Autowired
	private UnameMapper unameMapper;

	@JmsListener(destination = "active_ws", containerFactory="jmsTopicListenerContainerFactory")
	public void handler(String msg) {

		System.out.println("revice msg = " + msg);
		unameModel uname=new unameModel();
		uname.setBookname(msg);
		uname.setAuthor("activemq");
		uname.setBookdes("activemqDes");
		unameMapper.insertUnameBook(uname);
	}
}
