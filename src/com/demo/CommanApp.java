package com.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CommanApp {
	static HashMap<String, ArrayList<String>> installDependencyMap = new HashMap<>();
	static Set<String> listComponent = new LinkedHashSet<>();
	static HashMap<String, ArrayList<String>> removeDependencyMap = new HashMap<>();

	public static void main(String... string) {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String cmd = scanner.nextLine();
			if (cmd.equals("END"))
				break;
			if (cmd.startsWith("DEPEND")) {
				initDependencies(cmd);
			} else if (cmd.startsWith("INSTALL")) {
				initInstall(cmd);
			} else if (cmd.startsWith("REMOVE")) {
				initRemove(cmd);
			} else if (cmd.startsWith("LIST")) {
				initList(cmd);
			} else {
				throw new IllegalArgumentException(cmd + " not supported");
			}
		}
		scanner.close();
	}

	private static void initList(String cmd) {
		System.out.println(cmd);
		for(String component : listComponent){
			System.out.println("\t"+component);
		}
	}

	private static void initRemove(String cmd) {
		System.out.println(cmd);
		String parameter = extractParameterFromCmd(cmd)[0];
		remove(parameter);
		
	}
	
	private static void remove(String parameter) {
//		if()
//		if (removeDependencyMap.containsKey(parameter)) {
//			ArrayList<String> list = removeDependencyMap.get(parameter);
//			if (!list.isEmpty()) {
//				for (String component : list) {
//					remove(component);
//				}
//			}else{
//				System.out.println("\tRemoving " + parameter);		
//			}
//		}
	}
	
	private static void initInstall(String cmd) {
		System.out.println(cmd);
		String parameter = extractParameterFromCmd(cmd)[0];
		install(parameter);

	}

	private static void install(String parameter) {
		if(listComponent.contains(parameter)){
			System.out.println("\t"+parameter+" is Already Installed ");
			return;
		}
		if (installDependencyMap.containsKey(parameter)) {
			ArrayList<String> list = installDependencyMap.get(parameter);
			if (!list.isEmpty()) {
				for (String component : list) {
					install(component);
				}
			}
		}
		System.out.println("\tInstalling " + parameter);
		listComponent.add(parameter);
	}

	private static void initDependencies(String cmd) {
		System.out.println(cmd);
		String[] parameters = extractParameterFromCmd(cmd);
		for (int i = 0; i < parameters.length; i++) {
			String key = parameters[i];
			if (!installDependencyMap.containsKey(key))
				installDependencyMap.put(key, new ArrayList<String>());
			if (i < parameters.length - 1) {
				ArrayList<String> componentList = installDependencyMap.get(key);
				if (!componentList.contains(parameters[i + 1]))
					componentList.add(parameters[i + 1]);
			}
		}
		for (int i = parameters.length-1; i >= 0; i--) {
			String key = parameters[i];
			if (!removeDependencyMap.containsKey(key))
				removeDependencyMap.put(key, new ArrayList<String>());
			if (i > 0) {
				ArrayList<String> componentList = removeDependencyMap.get(key);
				if (!componentList.contains(parameters[i - 1]))
					componentList.add(parameters[i - 1]);
			}
		}
		
	}

	public static String[] extractParameterFromCmd(String cmd) {
		String[] split = cmd.split("\\s+");
		return Arrays.copyOfRange(split, 1, split.length);
	}
}
