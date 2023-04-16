package com.github.krextouch.rtds.node;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.w3c.dom.Node;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.lang.reflect.Array;
import java.util.*;

@SpringBootApplication
public class NodeApplication {

	static Map<String, Short> args = new HashMap<>();

	public static void main(String[] args) {
		for(String s: args) {
			String[] splitArgs = s.split("=");
			NodeApplication.args.put(splitArgs[0], Short.valueOf(splitArgs[1]));
		}
		SpringApplication.run(NodeApplication.class, args);
	}

	public static Map<String, Short> getArgs() {
		return NodeApplication.args;
	}
}
