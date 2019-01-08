package com.cloudlearn.core;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoreApplicationTests {

	@Test
	public void contextLoads() {
		ILoadBalancer balancer=new BaseLoadBalancer();

		List<Server> servers = new ArrayList<Server>();
		servers.add(new Server("127.0.0.1",8091));
		servers.add(new Server("127.0.0.1",8092));
		balancer.addServers(servers);

		for(int i=0;i<10;i++) {
			Server choosedServer = balancer.chooseServer(null);
			System.out.println(choosedServer);
		}

	}

}
