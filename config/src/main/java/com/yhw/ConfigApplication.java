package com.yhw;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * 配置中心
 */
@EnableConfigServer
@SpringCloudApplication
public class ConfigApplication {


	/**
	 * 关于配置中心客户端加载 http://localhost:8888 的问题
	 * org.springframework.cloud.config.client.ConfigClientProperties 查看客户端配置信息
	 *
	 * 需要配置spring cloud bus 实现配置文件动态刷新
	 *
	 */

	public static void main(String[] args) {
		SpringApplication.run(ConfigApplication.class, args);
	}

}
